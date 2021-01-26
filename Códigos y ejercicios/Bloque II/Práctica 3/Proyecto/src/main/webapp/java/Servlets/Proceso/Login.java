package Servlets.Proceso;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.LoginManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;


@WebServlet (name ="Servlets.Proceso.Login", urlPatterns = {"/Servlets.Proceso.Login"})
public class Login extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        String loginName = peticion.getParameter("loginName");

        if (loginName == null || loginName.trim().length() == 0) {
            Map<String, String> errors = new HashMap<>();

            errors.put("loginName", "Campo obligatorio");
            peticion.setAttribute("errors", errors);
            forwardToShowLogin(peticion, respuesta);

        } else {
            LoginManager.login(peticion, loginName.trim());
            respuesta.sendRedirect("Servlets.Paginas.Usuario");
        }
    }


    // Métodos propios
    private void forwardToShowLogin(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher("/Servlets.Paginas.Login").forward(peticion, respuesta);
    }
}
