<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%   
  request.setAttribute("logName","xuanxuan");  
%>  

<c:if test="${requestScope.logName}">
user.wealthy is true.
</c:if>
default.jsp:<%=request.getParameter("logName")%></br>
<jsp:forward  page="default2.jsp">
    <jsp:param name="logName1" value="xiaohuihui"/>
</jsp:forward >
i can not show!!