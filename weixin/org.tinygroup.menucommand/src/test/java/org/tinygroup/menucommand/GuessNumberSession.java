package org.tinygroup.menucommand;

import java.util.Random;

public class GuessNumberSession {
    public String userId;
    public int num;
    public int count;
	
	public GuessNumberSession(){
		Random r = new Random();
		num = Math.abs(r.nextInt())%100+1;
	}
}
