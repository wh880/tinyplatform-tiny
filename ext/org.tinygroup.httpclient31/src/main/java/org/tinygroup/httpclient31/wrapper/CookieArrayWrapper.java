package org.tinygroup.httpclient31.wrapper;

import org.tinygroup.httpvisitor.Cookie;

/**
 * Cookie数组包装类
 * @author yancheng11334
 *
 */
public class CookieArrayWrapper {

	private org.apache.commons.httpclient.Cookie[] cookies;
	
    public CookieArrayWrapper(org.apache.commons.httpclient.Cookie[] cookies) {
		super();
		this.cookies = cookies;
	}

	public Cookie[] getCookies(){
		if(cookies!=null){
			Cookie[] newCookies = new Cookie[cookies.length];
			for(int i=0;i<cookies.length;i++){
				newCookies[i] = new CookieWrapper(cookies[i]);
			}
			return newCookies;
		}else{
			return null;
		}
    }
}
