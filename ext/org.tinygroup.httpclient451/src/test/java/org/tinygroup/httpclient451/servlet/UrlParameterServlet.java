package org.tinygroup.httpclient451.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试URL的参数
 * @author yancheng11334
 *
 */
public class UrlParameterServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3552613861138861135L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(request.getParameter("p0"));
		out.write(request.getParameter("p1"));
		out.write(request.getParameter("p2"));
		out.write(request.getParameter("p3"));
	}

}
