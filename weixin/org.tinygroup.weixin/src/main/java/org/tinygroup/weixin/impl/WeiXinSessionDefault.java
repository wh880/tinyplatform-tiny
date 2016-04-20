package org.tinygroup.weixin.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.tinygroup.weixin.WeiXinSession;

public class WeiXinSessionDefault implements WeiXinSession{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7387128460312069070L;

	private String sessionId;
	
	private long creationTime;
	
	private long lastAccessedTime;
	
	private int maxInactiveInterval;
	
	/**
	 * 参数缓存对象
	 */
	private Map<String,Serializable> parameterMaps = new HashMap<String,Serializable>();
	
	/**
	 * 默认方案采用微信用户Id作为会话Id
	 * @param sessionId
	 */
	public WeiXinSessionDefault(String sessionId){
		this(sessionId,0);
	}
	
	/**
	 * 默认方案采用微信用户Id作为会话Id
	 * @param sessionId
	 * @param maxInactiveInterval
	 */
	public WeiXinSessionDefault(String sessionId,int maxInactiveInterval){
		this.creationTime = System.currentTimeMillis();
		this.lastAccessedTime = creationTime;
		this.sessionId = sessionId;
		this.maxInactiveInterval = maxInactiveInterval;
	}
	

	public String getSessionId() {
		return this.sessionId;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public boolean isExpired() {
		if(maxInactiveInterval==0){
		   return false;
		}else if(lastAccessedTime+maxInactiveInterval*1000<System.currentTimeMillis()){
		   return true;
		}
		return false;
	}

	public void update() {
		this.lastAccessedTime = System.currentTimeMillis();
	}

	public boolean contains(String name) {
		return parameterMaps.containsKey(name);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getParameter(String name) {
		return (T) parameterMaps.get(name);
	}

	public <T extends Serializable> void setParameter(String name, T value) {
		if(value==null){
			parameterMaps.remove(name);
		}else{
			parameterMaps.put(name, value);
		}
	}

}
