
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class ProcessLogoutServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

    LoginManager.logout(request);
    response.sendRedirect("/servlet/MainPageServlet");
  }

  public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

    doGet(request, response);
  }
}
