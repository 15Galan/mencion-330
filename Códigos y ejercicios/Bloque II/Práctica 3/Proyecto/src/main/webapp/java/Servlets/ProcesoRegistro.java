package Servlets;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.LoginManager;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;


@WebServlet(name = "Servlets.ProcesoRegistro", urlPatterns = {"/Servlets.ProcesoRegistro"})
public class ProcesoRegistro extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        doPost(peticion, respuesta);
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        File fichero = new File("usuarios");
        Scanner sc = new Scanner(fichero);
        boolean esta = false;

        String usuario = peticion.getParameter("loginName");
        String contra = peticion.getParameter("password");
        String contra2 = peticion.getParameter("password2");

        do {

            if (usuario.equals(sc.next())) {
                esta = true;
            }

        } while (sc.hasNext() && !esta);

        Map<String, String> errors = new HashMap<>();

        // TODO - Limpiar errores
        if (usuario.trim().length() == 0) {

            errors.put("loginName", "Campo obligatorio");       // Login vacío
            peticion.setAttribute("errors", errors);
            forwardToShowRegister(peticion, respuesta);

        } else if (esta) {

            errors.put("loginName", "Usuario ya registrado");   // Usuario ya registrado
            peticion.setAttribute("errors", errors);
            forwardToShowRegister(peticion, respuesta);

        } else if (!contra.equals(contra2)) {

            errors.put("password", "Contraseñas diferentes");   // Contraseñas distintas
            peticion.setAttribute("errors", errors);
            forwardToShowRegister(peticion, respuesta);

        } else if (contra == null || contra2 == null || (contra.trim().length() == 0) || (contra2.trim().length() == 0)) {

            errors.put("password", "Las contraseñas no pueden estar vacías");
            peticion.setAttribute("errors", errors);
            forwardToShowRegister(peticion, respuesta);

        } else {
            fichero = new File ("usuarios");
            PrintWriter escritor = new PrintWriter(new FileWriter(fichero, true));

            LoginManager.login(peticion, usuario.trim());

            escritor.println(usuario + " " + contra);
            escritor.close();

            respuesta.sendRedirect("Servlets.PerfilUsuario");
        }
    }


    // Métodos propios
    private void forwardToShowRegister(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher("/Servlets.Registro").forward(peticion, respuesta);
    }

    private void forwardToShowMenu(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher("/Servlets.PerfilUsuario").forward(request, response);
    }
}
