package Servlets;/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.LoginManager;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;


@WebServlet (name ="Servlets.ProcesoLogin", urlPatterns = {"/Servlets.ProcesoLogin"})
public class ProcesoLogin extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        String loginName = peticion.getParameter("loginName");

        if (loginName == null || loginName.trim().length() == 0) {
            Map errors = new HashMap();

            errors.put("loginName", "Mandatory field");
            peticion.setAttribute("errors", errors);
            forwardToShowLogin(peticion, respuesta);

        } else {
            LoginManager.login(peticion, loginName.trim());
            respuesta.sendRedirect("Servlets.PerfilUsuario");
        }
    }


    // Métodos propios
    private void forwardToShowLogin(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher("/Servlets.MostrarLogin").forward(peticion, respuesta);
    }
}
