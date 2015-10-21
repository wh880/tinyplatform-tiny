package org.tinygroup.weblayer.webcontext.session.model;

import java.util.Set;

import org.tinygroup.cache.Cache;
import org.tinygroup.commons.tools.CollectionUtil;
import org.tinygroup.weblayer.webcontext.session.Session;
import org.tinygroup.weblayer.webcontext.session.SessionManager;

/**
 * 默认实现，存放在缓存中
 * @author renhui
 *
 */
public class CacheSessionManager implements SessionManager{
	
    private Cache sessionCaches;
	private final Object lock = new Object();
	public static final String SESSION_GROUP="sessionGroup";
	
	public Cache getSessionCaches() {
		return sessionCaches;
	}
	public void setSessionCaches(Cache sessionCaches) {
		this.sessionCaches = sessionCaches;
	}
	public void addSession(Session session) {
		synchronized (lock) {
			sessionCaches.put(SESSION_GROUP, session.getSessionID(), session);
		}
	}

	public void expireSession(Session session) {
		synchronized (lock) {
			sessionCaches.remove(SESSION_GROUP, session.getSessionID());
		}
	}

	public Session[] queryAllSessions() {
		synchronized (lock) {
			Set<String> keys=sessionCaches.getGroupKeys(SESSION_GROUP);
			if(CollectionUtil.isEmpty(keys)){
				return new Session[0];
			}
			Session[] Sessions=new Session[keys.size()];
			int index=0;
			for (String key : keys) {
				Sessions[index++]=querySessionById(key);
			}
			return Sessions;
		}
	}

	public Session querySessionById(String sessionId) {
		synchronized (lock) {
			return (Session) sessionCaches.get(SESSION_GROUP, sessionId);
		}
	}

	
}