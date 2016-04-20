package org.tinygroup.weixinhttpclient3;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.tinygroup.httpvisit.HttpVisitor;
import org.tinygroup.httpvisit.impl.HttpVisitorImpl;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;
import org.tinygroup.weixinhttp.WeiXinHttpConnector;
import org.tinygroup.weixinhttp.WeiXinHttpUpload;
import org.tinygroup.weixinhttp.cert.WeiXinCert;
import org.tinygroup.weixinhttpclient3.cert.AuthSSLProtocolSocketFactory;

/**
 * 默认采用httpvisit实现
 * @author yancheng11334
 *
 */
public class HttpConnectorDefault implements WeiXinHttpConnector{

	private HttpVisitor visitor;
	
	private static final String HTTPS_PROTOCOL = "https";
	
	public HttpVisitor getVisitor() {
		if(visitor==null){
		   visitor = new HttpVisitorImpl();
		   visitor.init();
		}
		return visitor;
	}

	public void setVisitor(HttpVisitor visitor) {
		this.visitor = visitor;
	}

	public String getUrl(String url) {
		return getVisitor().getUrl(url, null);
	}

	public String postUrl(String url, String content,WeiXinCert cert) {
		if(cert!=null){
			addProtocol(cert);
		}
		return getVisitor().postXml(url, content);
	}
	
	private void addProtocol(WeiXinCert cert){
		try{
			KeyStore keyStore  = KeyStore.getInstance(cert.getCertType());
	        FileObject certObjcet = VFS.resolveFile(cert.getCertPath());
	        char[] password = cert.getPassword().toCharArray();
	        try {
	            keyStore.load(certObjcet.getInputStream(), password);
	        } finally {
	        	certObjcet.clean();
	        }
	        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory. getDefaultAlgorithm());
	        factory.init(keyStore, password);
	        
	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContext.getInstance("TLS");
	        sslcontext.init(factory.getKeyManagers(), null, null);
	        
	        ProtocolSocketFactory protocolSocketFactory =  new AuthSSLProtocolSocketFactory( VFS.resolveFile(cert.getCertPath()), cert.getPassword(),  null,null);
	        Protocol authhttps = new Protocol(HTTPS_PROTOCOL, protocolSocketFactory, 443);  
	        Protocol.registerProtocol(HTTPS_PROTOCOL, authhttps);  
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	public String upload(String url, WeiXinHttpUpload upload) {
		PostMethod postMethod = new PostMethod(url);
		try{
			Part[]  parts = createParts(upload);
			HttpMethodParams params = postMethod.getParams();
			postMethod.setRequestEntity(new MultipartRequestEntity(parts,params));
		    HttpClient client = getVisitor().getHttpClient();
		    client.executeMethod(postMethod);
		    return new String(postMethod.getResponseBody(),"utf-8");
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			postMethod.releaseConnection();
		}
	}
	
	private Part[] createParts(WeiXinHttpUpload upload){
		if(upload.getFormName()==null){
			Part[] parts  = { new FileObjectPart(upload.getFileName(),upload.getFileObject(),null,null)};
			return parts;
		}else{
			Part[] parts  = { new FileObjectPart(upload.getFileName(),upload.getFileObject(),null,null),new StringPart(upload.getFormName(),upload.getContent()) };
			return parts;
		}
	}

}
