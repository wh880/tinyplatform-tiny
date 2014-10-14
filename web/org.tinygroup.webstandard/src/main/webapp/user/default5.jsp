<%@ page language="java" import="java.util.*,user.User" pageEncoding="utf-8" isELIgnored="false"%>
  
<%
User user=(User)session.getAttribute("logName2");
if(user!=null){
System.out.println(user.getName());
}else{
session.setAttribute("logName2",new User("xiaoxuanxuan"));
}

%>

