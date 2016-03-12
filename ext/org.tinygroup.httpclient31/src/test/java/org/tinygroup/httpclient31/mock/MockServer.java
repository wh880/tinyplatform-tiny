package org.tinygroup.httpclient31.mock;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.tinygroup.httpclient31.servlet.CookieServlet;
import org.tinygroup.httpclient31.servlet.FileServlet;
import org.tinygroup.httpclient31.servlet.GBKServlet;
import org.tinygroup.httpclient31.servlet.HeaderServlet;
import org.tinygroup.httpclient31.servlet.HelloWorldServlet;
import org.tinygroup.httpclient31.servlet.RedirectServlet;
import org.tinygroup.httpclient31.servlet.TextServlet;
import org.tinygroup.httpclient31.servlet.UTF8Servlet;
import org.tinygroup.httpclient31.servlet.UrlParameterServlet;
import org.tinygroup.httpclient31.servlet.UserAgentServlet;
import org.tinygroup.vfs.VFS;


/**
 * Mock模拟HTTP服务
 * @author yancheng11334
 *
 */
public class MockServer {

    private Server server;

    public void start() {
    	init();
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
    protected void init(){
    	server = new Server();
    	
    	//参考jetty.xml配置
    	SelectChannelConnector http = new SelectChannelConnector();
        http.setPort(8080);
        http.setMaxIdleTime(300000);
        http.setAcceptors(2);
        http.setStatsOn(false);
        http.setConfidentialPort(8443);
        http.setLowResourcesConnections(20000);
        http.setLowResourcesMaxIdleTime(5000);
        
        //设置SSL上下文
        SslContextFactory sslContextFactory = new SslContextFactory();
        String path = VFS.resolveURL(this.getClass().getResource("/keystore")).getAbsolutePath();
        //System.out.println("path1="+path);
        sslContextFactory.setKeyStorePath(path);
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("123456");
        
        System.setProperty("javax.net.ssl.trustStore",path);
        //System.setProperty("javax.net.ssl.tructStorePassword", "123456");
        //System.setProperty("javax.net.ssl.keyStore",path);
        //System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        SslSelectChannelConnector https = new SslSelectChannelConnector(sslContextFactory);
        https.setPort(8443);
        https.setMaxIdleTime(400000);
        
    	server.setConnectors(new Connector[]{http,https});
    	
    	
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(GBKServlet.class, "/charset1.do");
        handler.addServletWithMapping(UTF8Servlet.class, "/charset2.do");
        handler.addServletWithMapping(UrlParameterServlet.class, "/param.do");
        handler.addServletWithMapping(HeaderServlet.class, "/header.do");
        handler.addServletWithMapping(CookieServlet.class, "/cookie.do");
        handler.addServletWithMapping(RedirectServlet.class, "/redirect.do");
        handler.addServletWithMapping(UserAgentServlet.class, "/agent.do");
        handler.addServletWithMapping(TextServlet.class, "/text.do");
        handler.addServletWithMapping(FileServlet.class, "/file.do");
        handler.addServletWithMapping(HelloWorldServlet.class, "/");
       
        //handler.addServletWithMapping(MockBasicAuthenticationServlet.class, "/basicAuth");
        //handler.addServletWithMapping(MockPartServlet.class, "/upload");

        // Start things up!
    }

    public void join() {
        try {
            server.join();
        } catch (InterruptedException e) {
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
        }
    }
}
