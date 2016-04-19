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
package org.tinygroup.weixinhttpclient451;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.vfs.VFS;

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
        
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        
        try {

            HttpGet httpget = new HttpGet("https://api.mch.weixin.qq.com/secapi/pay/refund");

            System.out.println("executing request" + httpget.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        System.out.println(text);
                    }
                   
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
