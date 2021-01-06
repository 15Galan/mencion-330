<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Process Login de entrada</TITLE>
<META NAME="description"
CONTENT="Procesar el nombre de usuario">
</HEAD>
<BODY BGCOLOR="#FDF5E6" TEXT="#000000" LINK="#0000EE"
VLINK="#551A8B" ALINK="#FF0000">


<%@ page import="login.LoginManager" %>

<%
LoginManager.logout(request);
response.sendRedirect(response.encodeRedirectURL("MainPage.jsp"));
%>

<UL>
</UL>
</BODY>
</HTML>