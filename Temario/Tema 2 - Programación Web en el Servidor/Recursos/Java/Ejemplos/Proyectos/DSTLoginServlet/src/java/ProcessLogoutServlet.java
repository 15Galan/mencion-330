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
import java.util.*;


public class ProcessLogoutServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

    LoginManager.logout(request);
    response.sendRedirect("MainPageServlet");
  }

  public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

    doGet(request, response);
  }
}
