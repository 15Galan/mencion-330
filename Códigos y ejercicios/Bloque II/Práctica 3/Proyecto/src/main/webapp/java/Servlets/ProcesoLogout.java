package Servlets;/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.LoginManager;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name ="Servlets.ProcesoLogout", urlPatterns = {"/Servlets.ProcesoLogout"})
public class ProcesoLogout extends HttpServlet {

    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        LoginManager.logout(peticion);
        respuesta.sendRedirect("Servlets.PerfilUsuario");
    }

    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        doGet(peticion, respuesta);
    }
}
