<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%   
  request.setAttribute("logName","xuanxuan");  
%>  
 <li>（1）<c:out value="${requestScope.logName}"></c:out></li>
<c:if test="${requestScope.logName=='xuanxuan'}">
user.wealthy is true.
</c:if>
default.jsp:<%=request.getParameter("logName")%></br>
<jsp:include page="default2.jsp">
    <jsp:param name="logName1" value="xiaohuihui"/>
</jsp:include>
i can show