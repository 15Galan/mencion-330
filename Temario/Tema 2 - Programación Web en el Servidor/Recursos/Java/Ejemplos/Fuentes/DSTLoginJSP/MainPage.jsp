
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Indice de una pagina JSP</TITLE>
<META NAME="description"
CONTENT="Sesion con login">
</HEAD>
<BODY BGCOLOR="#FDF5E6" TEXT="#000000" LINK="#0000EE"
VLINK="#551A8B" ALINK="#FF0000">
<CENTER>
<table BORDER=5 BGCOLOR="#EF8429">
<TR><TH CLASS="TITLE">
Sesion con Login JSP</table>
</CENTER>
<P>

<%@ page import="LoginManager" %>
<html>
<head>
<title>Sesion con login en jsp</title>
</head>
<body text="#000000" bgcolor="#ffffff" link="#000ee0" vlink="#551a8b"
alink="#000ee0">

<%
String loginName = LoginManager.getLoginName(request);
if (loginName == null) { // User has not logged in yet.
  response.sendRedirect(response.encodeRedirectURL("ShowLogin.jsp"));
} else {
%>

<%-- User has logged in. --%>
<h1>Hola <%=loginName%> !!</h1>
<br>
<br>
<br>
<a href="<%= response.encodeURL("http://www.lcc.uma.es/") %>">
Pagina principal</a>
<br>
<a href="<%= response.encodeURL("ProcessLogout.jsp") %>">Logout</a>
<% } %>
</body>
</html>
