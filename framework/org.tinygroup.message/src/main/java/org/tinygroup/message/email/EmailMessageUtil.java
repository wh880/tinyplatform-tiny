package org.tinygroup.message.email;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by luoguo on 2014/4/18.
 */
public final class EmailMessageUtil {
    private EmailMessageUtil() {

    }

    public static Session getSession(final EmailMessageAccount messageAccount) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", messageAccount.isAuth());
        props.put("mail.smtp.starttls.enable", messageAccount.isStartTlsEnable());
        props.put("mail.smtp.host", messageAccount.getHost());
        if (messageAccount.getPort() != null) {
            props.put("mail.smtp.port", messageAccount.getPort());
        }
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(messageAccount.getUsername(), messageAccount.getPassword());
                    }
                }
        );
        return session;
    }

    public static byte[] decode(byte[] b) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        InputStream b64is = MimeUtility.decode(bais, "base64");
        byte[] tmp = new byte[b.length];
        int n = b64is.read(tmp);
        byte[] res = new byte[n];
        System.arraycopy(tmp, 0, res, 0, n);
        return res;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new String(decode("=?utf-8?B?572X5p6c?=".getBytes())));
    }
}
