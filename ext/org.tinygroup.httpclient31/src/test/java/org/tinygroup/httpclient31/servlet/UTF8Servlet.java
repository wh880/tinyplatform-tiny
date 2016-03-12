package org.tinygroup.httpclient31.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试utf-8编码
 * @author yancheng11334
 *
 */
public class UTF8Servlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2143449860664274498L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write("中文,utf-8");
	}

}
