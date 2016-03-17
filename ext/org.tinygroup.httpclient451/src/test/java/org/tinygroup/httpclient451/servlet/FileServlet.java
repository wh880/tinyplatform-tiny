package org.tinygroup.httpclient451.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tinygroup.commons.file.IOUtils;

public class FileServlet extends AbstractMockServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591474351338440258L;

	protected void dealService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String text = IOUtils.readFromInputStream(request.getInputStream(), "ISO-8859-1");
			PrintWriter out = null;
			// 默认处理
			out = response.getWriter();
		    out.write(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
