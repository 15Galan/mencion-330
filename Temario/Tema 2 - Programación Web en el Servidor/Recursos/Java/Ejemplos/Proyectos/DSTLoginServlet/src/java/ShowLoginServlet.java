/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lidia Fuentes
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ShowLoginServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

    String loginName = "";

    // Gestion de login=null
    String loginNameErrorMessage = "";
    Map errors = (Map) request.getAttribute("errors");
    if (errors != null) {
      String errorHeader = "<font color=\"red\"><b>";
      String errorFooter = "</b></font>";
      if (errors.containsKey("loginName")) {
        loginName = request.getParameter("loginName");
        loginNameErrorMessage = errorHeader +
        errors.get("loginName") + errorFooter;
      }
    }


   /* Generar respuesta. */
   response.setContentType("text/html; charset=ISO-8859-1");
   PrintWriter out = response.getWriter();
   /* Inicio. */
   out.println("<html><head><title>");
   out.println("Portal-1 login form");
   out.println("</title></head>");
   out.println("<body text=\"#000000\" bgcolor=\"#ffffff\">");
   /* Inicio del form. */
   out.println("<form method=\"POST\" action=\"ProcessLoginServlet\">");
   /* Inicio de la table. */
   out.println("<table width=\"100%\" border=\"0\" align=\"center\" " +"cellspacing=\"12\">");
   /* Login. */
   out.println("<tr>");
   out.println("<th align=\"right\" width=\"50%\"> Login name </th>");

   out.println("<td align=\"left\">" +"<input type=\"text\" name=\"loginName\" " +
   " value=\"" + loginName + "\" size=\"16\" maxlength=\"16\">" +
   loginNameErrorMessage + "</td>");
   out.println("</tr>");

   /* Boton de login. */
   out.println("<tr>");
   out.println("<td width=\"50%\"></td>");
   out.println("<td align=\"left\" width=\"50%\"> " +"<input type=\"submit\" value=\"Login\"></td>");
   out.println("</tr>");

   /* Fin de tabla. */
   out.println("</table>");
   /* Fin. */
   out.println("</body></html>");
   /* Cerrar el descriptor de salida". */
   out.close();
   }

  public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
    doGet(request, response);
  }
}
