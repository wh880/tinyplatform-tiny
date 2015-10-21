package org.tinygroup.weblayer.webcontext.session;


/**
 * session信息管理接口
 * @author renhui
 *
 */
public interface SessionManager {
	
	public void addSession(Session session);
	
	public void expireSession(Session session);
	
	public Session[] queryAllSessions();
	
	public Session querySessionById(String sessionId);

}
