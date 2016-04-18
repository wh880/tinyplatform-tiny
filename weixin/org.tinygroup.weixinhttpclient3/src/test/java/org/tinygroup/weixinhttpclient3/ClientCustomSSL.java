/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.tinygroup.weixinhttpclient3;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.tinygroup.commons.file.IOUtils;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;
import org.tinygroup.weixinhttpclient3.cert.AuthSSLProtocolSocketFactory;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {

    public final static void main(String[] args) throws Exception {
    	testPayRefund("D:/apiclient_cert.p12","1244859502");
    }
    
    public  static void testPayRefund(String certPath,String password) throws Exception {
    	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileObject certObjcet = VFS.resolveFile(certPath);
        try {
            keyStore.load(certObjcet.getInputStream(), password.toCharArray());
        } finally {
        	certObjcet.clean();
        }
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory. getDefaultAlgorithm());
        factory.init(keyStore, password.toCharArray());
        
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(factory.getKeyManagers(), null, null);
        
        ProtocolSocketFactory protocolSocketFactory =  new AuthSSLProtocolSocketFactory( VFS.resolveFile(certPath), password,  null,null);
        Protocol authhttps = new Protocol("https", protocolSocketFactory,  443);  
        Protocol.registerProtocol("https", authhttps);  
        HttpClient client = new HttpClient(); 
        //client.getHostConfiguration().setHost("api.mch.weixin.qq.com", 443, authhttps);
        GetMethod get = new GetMethod("https://api.mch.weixin.qq.com/secapi/pay/refund");
        
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Accept", "text/html, application/xhtml+xml, */*");
        headerMap.put("Accept-Language", "zh-CN,en-US;q=0.5");
        headerMap.put("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        headerMap.put("Accept-Encoding", "gzip, deflate");
        headerMap.put("Host", "localhost:9999");
        headerMap.put("Connection", "Keep-Alive");
        
        try {
        	addHeader(get,headerMap);
            String result = execute(client,get);
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            get.releaseConnection();
        }
    }
    
    private static void addHeader(HttpMethodBase method, Map<String, String> header) {
        if (header != null) {
            for (String key : header.keySet()) {
                Header h = new Header(key, header.get(key));
                method.addRequestHeader(h);
            }
        }
    }
    
    private static String execute(HttpClient client,HttpMethodBase method) {
    	String requestCharset = "ISO-8859-1";
    	boolean authEnabled =true;
    	String responseCharset = "UTF-8";
        try {
            if (!("ISO-8859-1").equals(requestCharset)) {
                method.addRequestHeader("Content-Type", "text/html; charset=" + requestCharset);
            }
            method.setDoAuthentication(authEnabled);
            int iGetResultCode = client.executeMethod(method);
            if (iGetResultCode == HttpStatus.SC_OK) {
               // LOGGER.logMessage(LogLevel.DEBUG, "结果成功返回。");
                Header responseHeader = method.getResponseHeader("Content-Encoding");
                if (responseHeader != null) {
                    String acceptEncoding = responseHeader.getValue();
                    if (acceptEncoding != null && ("gzip").equals(acceptEncoding)) {
                        //如果是gzip压缩方式
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(method.getResponseBody());
                        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
                        return IOUtils.readFromInputStream(gzipInputStream, responseCharset);
                    }
                }
                return new String(method.getResponseBody(), responseCharset);
            }
            //LOGGER.logMessage(LogLevel.ERROR, "结果返回失败，原因：{}", method.getStatusLine().toString());
            throw new RuntimeException(method.getStatusLine().toString());
        } catch (Exception e) {
            //LOGGER.logMessage(LogLevel.DEBUG, "结果返回失败，原因：{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            method.releaseConnection();
        }
    }
    

}
