package org.tinygroup.weblayer.webcontext.session.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tinygroup.weblayer.webcontext.session.Session;
import org.tinygroup.weblayer.webcontext.session.SessionManager;

/**
 * 默认实现，存放在内存中
 * @author renhui
 *
 */
public class DefaultSessionManager implements SessionManager{
	
	protected Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();	
	
	private final Object lock = new Object();
	
    private static SessionManager manager;
	
	private DefaultSessionManager(){
		
	}
	public static SessionManager getSingleton(){
		if(manager==null){
			synchronized (SessionManager.class) {
				if(manager==null){
					manager=new DefaultSessionManager();
				}
			}
		}
		return manager;
	}
	
	public void addSession(Session session) {
		synchronized (lock) {
			sessions.put(session.getSessionID(), session);
		}
	}

	public void expireSession(Session session) {
		synchronized (lock){
			sessions.remove(session.getSessionID());
		}
	}

	public Session[] queryAllSessions() {
		synchronized (lock) {
			 Session[] httpSessions=new  Session[sessions.size()];
			 int index=0;
			 for (Session session : sessions.values()) {
				 httpSessions[index++]=session;	
			 }
			 return httpSessions;
		}
	}

	public Session querySessionById(String sessionId) {
		synchronized (lock) {
			return sessions.get(sessionId);
		}
	}

}
