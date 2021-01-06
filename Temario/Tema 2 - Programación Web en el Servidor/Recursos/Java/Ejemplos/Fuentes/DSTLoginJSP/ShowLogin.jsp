<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Indice de una pagina JSP</TITLE>
<META NAME="description"
CONTENT="Ejemplo con tres enlaces diferentes">
</HEAD>
<BODY BGCOLOR="#FDF5E6" TEXT="#000000" LINK="#0000EE"
VLINK="#551A8B" ALINK="#FF0000">
<CENTER>
<table BORDER=5 BGCOLOR="#EF8429">
<TR><TH CLASS="TITLE">
Introducir el nombre de usuario</table>
</CENTER>
<P>

<%@ page import="java.util.Map, java.util.HashMap, LoginForm" %>



<jsp:useBean id="loginForm" scope="request" class="LoginForm"> 
   <jsp:setProperty name="loginForm" property="*" />
</jsp:useBean>

<%
String loginNameErrorMessage = "";

Map errors = (Map) request.getAttribute("errors");
if (errors != null) {
String errorHeader = "<font color=\"red\"><b>";
String errorFooter = "</b></font>";
if (errors.containsKey("loginName")) {
loginNameErrorMessage = errorHeader +
errors.get("loginName") + errorFooter;
}
}
%>


 

<form method="POST" action="<%= response.encodeURL("ProcessLogin.jsp")
%>">
<table width="100%" border="0" align="center" cellspacing="12">
<%-- Login name --%>
<tr>
<th align="right" width="50%">
Nombre de usuario
</th>
<td align="left">
<input type="text" name="loginName"
value="<jsp:getProperty name="loginForm"
property="loginName"/>"
size="16" maxlength="16">
<%= loginNameErrorMessage %>
</td>
</tr>




<tr>
<td width="50%"></td>
<td align="left" width="50%">
<input type="submit" value="Login">
</td>
</tr>
</table>
</form>
<UL>
</UL>
</BODY>
</HTML>