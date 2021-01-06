/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
/**
 *
 * @author Lidia Fuentes
 */
public class ProcessLoginServlet extends HttpServlet {
  public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

    String loginName = request.getParameter("loginName");
    if ( (loginName == null) || (loginName.trim().length() == 0) ) {
    Map errors = new HashMap();
    errors.put("loginName", "Mandatory field");
    request.setAttribute("errors", errors);
    forwardToShowLogin(request, response);
    } else {
    LoginManager.login(request, loginName.trim());
    response.sendRedirect("MainPageServlet");
    }
  }

  public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
    doPost(request, response);
  }

  private void forwardToShowLogin(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

    RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/ShowLoginServlet");

    requestDispatcher.forward(request, response);
  }
}
