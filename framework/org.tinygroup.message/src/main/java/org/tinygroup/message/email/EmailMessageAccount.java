package org.tinygroup.message.email;

import org.tinygroup.message.MessageAccount;

/**
 * Created by luoguo on 2014/4/18.
 */
public class EmailMessageAccount implements MessageAccount {
    boolean auth = true;
    boolean startTlsEnable = true;
    String host;
    String port;
    String username;
    String password;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isStartTlsEnable() {
        return startTlsEnable;
    }

    public void setStartTlsEnable(boolean startTlsEnable) {
        this.startTlsEnable = startTlsEnable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
