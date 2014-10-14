package org.apache.jsp.user;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class default2_jsp extends org.tinygroup.jspengine.runtime.HttpJspBase
    implements org.tinygroup.jspengine.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = new org.tinygroup.jspengine.runtime.JspFactoryImpl();

  private static java.util.Vector _jspx_dependants;

  private org.tinygroup.jspengine.runtime.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.tinygroup.jspengine.runtime.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("  \r\n");
   
  request.setAttribute("logName","admin");  
  session.setAttribute("logName","xuanxuan");

      out.write("  \r\n");
      out.write("default2.jsp:");
      out.print(request.getParameter("logName1"));
      out.write("</br>\r\n");
      out.write("request:");
      out.write((java.lang.String) org.tinygroup.jspengine.runtime.PageContextImpl.evaluateExpression("${requestScope.logName}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</br>\r\n");
      out.write("session:");
      out.write((java.lang.String) org.tinygroup.jspengine.runtime.PageContextImpl.evaluateExpression("${sessionScope.logName}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</br>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
