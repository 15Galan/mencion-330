package login;

import javax.servlet.http.*;

public final class LoginManager {
   private final static String LOGIN_NAME_ATTRIBUTE ="loginName";

   private LoginManager() {}
   public static void login(HttpServletRequest request,String loginName) {
      HttpSession session = request.getSession(true);
      
      session.setAttribute(LOGIN_NAME_ATTRIBUTE, loginName);
    }
   public static void logout(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session != null) {
         session.invalidate();
      }
   }
   public static String getLoginName(HttpServletRequest request) {
     HttpSession session = request.getSession(false);
     if (session == null) {
        return null;
     } else {
     
     return (String) session.getAttribute(LOGIN_NAME_ATTRIBUTE);
     }
   }
}