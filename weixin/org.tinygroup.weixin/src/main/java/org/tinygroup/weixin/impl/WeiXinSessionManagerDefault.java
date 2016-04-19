package org.tinygroup.weixin.impl;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.tinygroup.cache.Cache;
import org.tinygroup.cache.CacheManager;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.weixin.WeiXinSession;
import org.tinygroup.weixin.WeiXinSessionManager;

/**
 * 基于Cache的微信会话实现方案
 * @author yancheng11334
 *
 */
public class WeiXinSessionManagerDefault implements WeiXinSessionManager{

	protected static final Logger LOGGER = LoggerFactory.getLogger(WeiXinSessionManagerDefault.class);
	private static final String SESSION_GROUP = "weixin_sessions";
	private Cache cache;
	private CacheManager cacheManager;
	private String cacheRegion;
	
	//Session默认的生存时间，单位s,默认值0
	private int maxInactiveInterval;
	//Session清理线程首次延迟时间，单位s,默认值60
	private int expireTimerDelay=60;
	//Session清理线程运行周期，单位s，默认值300
	private int expireTimePeriod=300;
	
	private Timer expireTimer = new Timer();
	private TimerTask expireTask = new SessionClearTask();
	
	public WeiXinSessionManagerDefault(){
		this(0,60,300);
	}
	
    public WeiXinSessionManagerDefault(int maxInactiveInterval,int expireTimerDelay,int expireTimePeriod){
		this.maxInactiveInterval = maxInactiveInterval;
		this.expireTimerDelay = expireTimerDelay;
		this.expireTimePeriod = expireTimePeriod;
		
		//启动清理线程
		expireTimer.schedule(expireTask, expireTimerDelay*1000, expireTimePeriod*1000);
	}
	
	public synchronized Cache getCache() {
		if(cache==null){
		   cache = cacheManager.createCache(cacheRegion);
		}
		return cache;
	}
	
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	public String getCacheRegion() {
		return cacheRegion;
	}
	public void setCacheRegion(String cacheRegion) {
		this.cacheRegion = cacheRegion;
	}
	
	public WeiXinSession createWeiXinSession(String sessionId) {
		WeiXinSession session = new WeiXinSessionDefault(sessionId,maxInactiveInterval);
		return session;
	}
	public WeiXinSession getWeiXinSession(String sessionId) {
		return (WeiXinSession) getCache().get(SESSION_GROUP,sessionId);
	}
	public void addWeiXinSession(WeiXinSession session) {
		getCache().put(SESSION_GROUP, session.getSessionId(), session);
	}
	public void removeWeiXinSession(String sessionId) {
		getCache().remove(SESSION_GROUP, sessionId);
	}
	
	public void expireWeiXinSessions() {
		WeiXinSession[] sessions = getWeiXinSessions();
		if(sessions!=null){
		   for(WeiXinSession session:sessions){
			   if(session.isExpired()){
				  getCache().remove(SESSION_GROUP, session.getSessionId());
			   }
		   }
		}
	}
	
	public WeiXinSession[] getWeiXinSessions() {
		WeiXinSession[] array;
		Set<String> keys=getCache().getGroupKeys(SESSION_GROUP);
		if(keys==null || keys.isEmpty()){
		   return new WeiXinSession[0];
		}
		array = new WeiXinSession[keys.size()];
		int i=0;
		for(String key:keys){
			array[i] = (WeiXinSession) getCache().get(SESSION_GROUP, key);
			i++;
		}
		return array;
	}
	
	public void clear() {
		getCache().cleanGroup(SESSION_GROUP);
	}
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
	public int getExpireTimerDelay() {
		return expireTimerDelay;
	}
	public void setExpireTimerDelay(int expireTimerDelay) {
		this.expireTimerDelay = expireTimerDelay;
	}
	public int getExpireTimePeriod() {
		return expireTimePeriod;
	}
	public void setExpireTimePeriod(int expireTimePeriod) {
		this.expireTimePeriod = expireTimePeriod;
	}
	
	class SessionClearTask extends TimerTask{

		@Override
		public void run() {
			LOGGER.logMessage(LogLevel.DEBUG, "清理过期微信会话开始...");
			expireWeiXinSessions();
			LOGGER.logMessage(LogLevel.DEBUG, "清理过期微信会话结束!");
		}
		
	}
	
}
