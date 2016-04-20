package org.tinygroup.weixinkf.kfaccount;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 消息服务中提供的客服信息
 * @author yancheng11334
 *
 */
public class CustomerServiceAccount {

	/**
	 * 完整客服账号，格式为：账号前缀@公众号微信号 
	 */
	@JSONField(name="kf_account")
	private String account;
	
	/**
	 * 客服工号
	 */
	@JSONField(name="kf_id")
	private String id;
	
	/**
	 * 客服昵称
	 */
	@JSONField(name="kf_nick")
	private String nick;
	
	/**
	 * 客服头像URL
	 */
	@JSONField(name="kf_headimgurl")
	private String headimgurl;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	
}
