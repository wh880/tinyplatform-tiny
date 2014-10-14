<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%> 
<%   
  request.setAttribute("logName","admin");  
  session.setAttribute("logName","xuanxuan");
  System.out.println(pageContext);
%>  
default2.jsp:<%=request.getParameter("logName1")%></br>
request:${requestScope.logName}</br>
session:${sessionScope.logName}</br>