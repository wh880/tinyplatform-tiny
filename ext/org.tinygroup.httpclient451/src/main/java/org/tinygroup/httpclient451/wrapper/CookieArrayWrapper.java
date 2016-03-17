package org.tinygroup.httpclient451.wrapper;

import java.util.List;

import org.tinygroup.httpvisitor.Cookie;

/**
 * Cookie数组包装类
 * @author yancheng11334
 *
 */
public class CookieArrayWrapper {

	private List<org.apache.http.cookie.Cookie> cookies;
	
    public CookieArrayWrapper(List<org.apache.http.cookie.Cookie> cookies) {
		super();
		this.cookies = cookies;
	}

	public Cookie[] getCookies(){
		if(cookies!=null){
			Cookie[] newCookies = new Cookie[cookies.size()];
			for(int i=0;i<cookies.size();i++){
				newCookies[i] = new CookieWrapper(cookies.get(i));
			}
			return newCookies;
		}else{
			return null;
		}
    }
}
