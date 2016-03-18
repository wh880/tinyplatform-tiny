package org.tinygroup.httpclient451.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 测试GBK编码
 * @author yancheng11334
 *
 */
public class GBKServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5415009275609653520L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		out.write("中文,GBK");
	}

}
