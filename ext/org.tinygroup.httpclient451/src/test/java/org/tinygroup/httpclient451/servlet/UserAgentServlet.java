package org.tinygroup.httpclient451.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试User-Agent
 * @author yancheng11334
 *
 */
public class UserAgentServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -19853599641596946L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.write(request.getHeader("User-Agent").toString());		
	}

}
