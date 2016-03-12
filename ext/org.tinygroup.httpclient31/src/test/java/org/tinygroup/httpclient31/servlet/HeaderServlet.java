package org.tinygroup.httpclient31.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试Header
 * @author yancheng11334
 *
 */
public class HeaderServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1832000843225181225L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(request.getHeader("User-Agent"));
	}

}
