/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lidia Fuentes
 */
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public final class LoginManager {
   private final static String LOGIN_NAME_ATTRIBUTE ="loginName";

   private LoginManager() {}
   public final static void login(HttpServletRequest request,String loginName) {
      HttpSession session = request.getSession(true);
      // deprecated, sustituida por setAttribute
      session.putValue(LOGIN_NAME_ATTRIBUTE, loginName);
    }
   public final static void logout(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session != null) {
         session.invalidate();
      }
   }
   public final static String getLoginName(HttpServletRequest request) {
     HttpSession session = request.getSession(false);
     if (session == null) {
        return null;
     } else {
     // deprecated, sustituida por getAttribute
     return (String) session.getValue(LOGIN_NAME_ATTRIBUTE);
     }
   }
}
