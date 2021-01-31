package Servlets.Proceso;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.LoginManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;


@WebServlet (name ="Servlets.Proceso.Login", urlPatterns = {"/Servlets.Proceso.Login"})
public class Login extends HttpServlet {

    private final String RUTA_BASE = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\resources\\";

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        String usuario = peticion.getParameter("loginName");
        String contra = peticion.getParameter("password");

        if (usuario == null || usuario.trim().length() == 0) {  // Usuario vacío
            Map<String, String> errors = new HashMap<>();

            errors.put("loginName", "Campo obligatorio");
            peticion.setAttribute("errors", errors);
            forwardToShowLogin(peticion, respuesta);

        } else if (!registrado(usuario, contra)) {              // Contraseña incorrecta ó vacía
            Map<String, String> errors = new HashMap<>();

            errors.put("password", "Contraseña incorrecta");
            peticion.setAttribute("errors", errors);
            forwardToShowLogin(peticion, respuesta);

        } else {                                                // Registro correcto
            LoginManager.login(peticion, usuario.trim());
            respuesta.sendRedirect("Servlets.Paginas.Usuario");
        }
    }


    // Métodos propios

    /**
     * Comprueba si un usuario introducido está registrado.
     *
     * @param usuario   Usuario introducido en la aplicación
     * @param contra    Contraseña del usuario introducido
     *
     * @return  TRUE si los datos son correctos, FALSE en caso contrario
     */
    public boolean registrado(String usuario, String contra) {
        boolean res = false;

        try (Scanner sc = new Scanner(new File(RUTA_BASE + "usuarios.txt"))) {
            do {
                String nombre = sc.next();
                String clave = sc.next();

                if (usuario.equals(nombre) && contra.equals(clave)) {
                    res = true;
                }

            } while (sc.hasNext() && !res);

        } catch (IOException e) {
            System.err.println("Error al leer el fichero.");
        }

        return res;
    }

    private void forwardToShowLogin(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher("/Servlets.Paginas.Login").forward(peticion, respuesta);
    }
}
