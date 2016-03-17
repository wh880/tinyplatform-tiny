package org.tinygroup.httpclient451.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 基本的HelloWorld
 * @author yancheng11334
 *
 */
public class HelloWorldServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3898906063455512009L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = null;
		// 默认处理
		out = response.getWriter();
	    out.write("hello world");
	}

}
