package Servlets.Proceso;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.LoginManager;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name ="Servlets.Proceso.Logout", urlPatterns = {"/Servlets.Proceso.Logout"})
public class Logout extends HttpServlet {

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        LoginManager.logout(peticion);
        respuesta.sendRedirect("Servlets.PaginaUsuario");
    }

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        doGet(peticion, respuesta);
    }
}
