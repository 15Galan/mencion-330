/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MainPageServlet extends HttpServlet {

  public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
    String loginName = LoginManager.getLoginName(request);
    if (loginName != null) { // El usuario ya se ha autentificado.
      generateMainPage(response, loginName);
    } else { // El usuario no se ha autentificado.
         response.sendRedirect("ShowLoginServlet");

    }
  }

  public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
    doGet(request, response);
  }

  private void generateMainPage(HttpServletResponse response,String loginName) throws IOException {
    response.setContentType("text/html; charset=ISO-8859-1");
    PrintWriter out = response.getWriter();out.println("<html><head><title>");
    out.println("Portal-1 main page");
    out.println("</title></head>");
    out.println("<body text=\"#000000\" bgcolor=\"#ffffff\" " +
            "link=\"#000ee0\" vlink=\"#551a8b\" alink=\"#000ee0\">");
    out.println("<h1>Hello " + loginName + "! </h1>");
    out.println("<br><br><br>");
    out.println("<a href=\"http://www.lcc.uma.es\">Departamento LCC " +"Inicio</a><br>");
    out.println("<a href=\"ProcessLogoutServlet\">Logout</a><br>");
    out.println("</body></html>");
    out.close();
  }
}