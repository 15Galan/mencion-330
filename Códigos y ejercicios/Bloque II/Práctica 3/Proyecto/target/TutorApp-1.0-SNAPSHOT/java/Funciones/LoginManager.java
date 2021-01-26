package Funciones;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import javax.servlet.http.*;


public final class LoginManager {

    private final static String ATRIBUTO_USUARIO = "loginName";

    private LoginManager() {

    }

    public static void login(HttpServletRequest peticion, String usuario) {
        HttpSession session = peticion.getSession(true);

        session.setAttribute(ATRIBUTO_USUARIO, usuario);
    }

    public static void logout(HttpServletRequest peticion) {
        HttpSession session = peticion.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    public static String getLoginName(HttpServletRequest peticion) {
        HttpSession session = peticion.getSession(false);

        if (session == null) {
            return null;

        } else {
            return session.getAttribute(ATRIBUTO_USUARIO).toString();
        }
    }
}
