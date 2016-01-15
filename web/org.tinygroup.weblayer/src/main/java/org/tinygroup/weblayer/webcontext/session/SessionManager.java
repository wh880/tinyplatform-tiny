package org.tinygroup.weblayer.webcontext.session;


/**
 * session信息管理接口
 * @author renhui
 *
 */
public interface SessionManager {
	
	void addSession(Session session);
	
	void expireSession(Session session);
	
	Session[] queryAllSessions();
	
	Session querySessionById(String sessionId);

}
