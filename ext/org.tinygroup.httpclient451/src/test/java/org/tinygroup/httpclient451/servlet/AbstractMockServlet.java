package org.tinygroup.httpclient451.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试的基本类
 * @author yancheng11334
 *
 */
public abstract class AbstractMockServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dealService(request, response);
	}

	protected void doHead(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dealService(request, response);
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dealService(request, response);
	}

	protected void doOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dealService(request, response);
	}

	protected void doTrace(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dealService(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dealService(request, response);
	}
	
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dealService(request, response);
	}
	
	/**
	 * 抽象的服务逻辑
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void dealService (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
}
