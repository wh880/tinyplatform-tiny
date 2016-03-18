package org.tinygroup.httpclient451.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 重定向测试
 * @author yancheng11334
 *
 */
public class RedirectServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6574157765423676785L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/");
	}

}
