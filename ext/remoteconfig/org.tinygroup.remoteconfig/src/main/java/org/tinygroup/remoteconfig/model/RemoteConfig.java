/**
 * 
 */
package org.tinygroup.remoteconfig.model;

import java.util.Arrays;

/**
 * @author yanwj
 * 
 */
public class RemoteConfig {

	// ZK服务器地址列表，格式：127.0.0.0:80,127.0.0.0:81,127.0.0.0:82
	String urls;
	// app名称
	String app;
	// 环境
	String env;
	// 版本
	String version;
	// 账户
	String[] usernames;
	// 密码
	String[] passwords;

	public RemoteConfig(String urls, String app, String env, String version) {
		super();
		this.urls = urls;
		this.app = app;
		this.env = env;
		this.version = version;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String[] getUsernames() {
		return usernames;
	}

	public void setUsernames(String[] usernames) {
		this.usernames = usernames;
	}

	public String[] getPasswords() {
		return passwords;
	}

	public void setPasswords(String[] passwords) {
		this.passwords = passwords;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("urls=%s \r\n", urls));
		sb.append(String.format("app=%s \r\n", app));
		sb.append(String.format("env=%s \r\n", env));
		sb.append(String.format("version=%s \r\n", version));
		sb.append(String.format("usernames=%s \r\n", Arrays.toString(usernames)));
		sb.append(String.format("passwords=%s \r\n", Arrays.toString(passwords)));
		return sb.toString();
	}
	
}
