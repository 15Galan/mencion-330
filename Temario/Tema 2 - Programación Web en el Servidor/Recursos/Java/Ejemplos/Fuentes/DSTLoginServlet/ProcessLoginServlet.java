
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

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
    response.sendRedirect("/servlet/MainPageServlet");
    }
  }

  public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
    doPost(request, response);
  }

  private void forwardToShowLogin(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

    RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/servlet/ShowLoginServlet");

    requestDispatcher.forward(request, response);
  }
}
