package org.tinygroup.httpclient31.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试cookie
 * @author yancheng11334
 *
 */
public class CookieServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1708954091176866242L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String value = "";
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("tinyage")) {
				value = cookie.getValue();
			}
			out.write(value);
		}
	}

}
