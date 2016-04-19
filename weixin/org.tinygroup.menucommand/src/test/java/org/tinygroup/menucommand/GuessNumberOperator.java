package org.tinygroup.menucommand;

import java.util.HashMap;
import java.util.Map;

public class GuessNumberOperator {

	private static Map<String,GuessNumberSession> sessionMaps = new HashMap<String,GuessNumberSession>();
	
	public GuessNumberSession getGuessNumberSession(String userId){
		return sessionMaps.get(userId);
	}
	
	public void addGuessNumberSession(GuessNumberSession session){
		sessionMaps.put(session.userId, session);
	}
	
	public void removeGuessNumberSession(String userId){
		sessionMaps.remove(userId);
	}
}
