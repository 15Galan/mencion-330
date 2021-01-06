<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Process Login de entrada</TITLE>
<META NAME="description"
CONTENT="Procesar el nombre de usuario">
</HEAD>
<BODY BGCOLOR="#FDF5E6" TEXT="#000000" LINK="#0000EE"
VLINK="#551A8B" ALINK="#FF0000">


<%@ page import="java.util.Map, java.util.HashMap, LoginForm, LoginManager"
%>

<jsp:useBean id="loginForm" scope="request" class="LoginForm"/>
  <jsp:setProperty name="loginForm" property="*"/>
</jsp:useBean>

<%
String loginName = loginForm.getLoginName();
Map errors = new HashMap();
if ( (loginName == null) || (loginName.length() == 0) ) {
  errors.put("loginName", "Mandatory field");
}
if (!errors.isEmpty()) {
  request.setAttribute("errors", errors);
%>

<jsp:forward page="ShowLogin.jsp" />

<% } 
else {
  LoginManager.login(request, loginName);

  response.sendRedirect(response.encodeRedirectURL("MainPage.jsp"));
}
%>

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