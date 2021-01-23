package Servlets.Proceso;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.LoginManager;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;


@WebServlet(name = "Servlets.Proceso.Registro", urlPatterns = {"/Servlets.Proceso.Registro"})
public class Registro extends HttpServlet {

    private final String RUTA_BASE = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\resources\\";

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        doPost(peticion, respuesta);
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        comprobarErroresSesion(peticion, respuesta);
    }


    // Métodos propios
    /**
     * Analiza la introducción de datos del registro y trata
     * los errores para evitar una modificación incorrecta.
     *
     * @param peticion      Petición de la página
     * @param respuesta     Respuesta de la página
     *
     * @throws IOException          Error en el fichero
     * @throws ServletException     Error en el servlet
     */
    public void comprobarErroresSesion(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        File fichero = new File(RUTA_BASE + "usuarios.txt");
        Scanner sc = new Scanner(fichero);
        boolean encontrado = false;

        String usuario = peticion.getParameter("loginName").trim();
        String contra = peticion.getParameter("password").trim();
        String contra2 = peticion.getParameter("password2").trim();

        Map<String, String> errores = new HashMap<>();

        // Buscar un usuario registrado
        do {
            if (usuario.equals(sc.next())) {
                encontrado = true;
            }
        } while (sc.hasNext() && !encontrado);

        // TODO - Limpiar errores
        // Detectar errores
        if (usuario.length() == 0) {

            errores.put("loginName", "\tCampo obligatorio");       // Login vacío
            peticion.setAttribute("errors", errores);
            forwardToShowRegister(peticion, respuesta);

        } else if (encontrado) {

            errores.put("loginName", "\tUsuario ya registrado");   // Usuario ya registrado
            peticion.setAttribute("errors", errores);
            forwardToShowRegister(peticion, respuesta);

        } else if (!contra.equals(contra2)) {

            errores.put("password", "\tContraseñas diferentes");   // Contraseñas distintas
            peticion.setAttribute("errors", errores);
            forwardToShowRegister(peticion, respuesta);

        } else if (contra == null || contra2 == null || contra.length() == 0 || contra2.length() == 0) {

            errores.put("password", "\tLas contraseñas no pueden estar vacías");
            peticion.setAttribute("errors", errores);
            forwardToShowRegister(peticion, respuesta);

        } else {
            PrintWriter escritor = new PrintWriter(new FileWriter(fichero, true));

            LoginManager.login(peticion, usuario.trim());

            // Escribir el nuevo usuario y su contraseña en el fichero
            escritor.println(usuario + " " + contra);
            escritor.close();

            respuesta.sendRedirect("Servlets.PaginaUsuario");
        }
    }

    private void forwardToShowRegister(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher("/Servlets.Registro").forward(peticion, respuesta);
    }

    private void forwardToShowMenu(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.getServletContext().getRequestDispatcher("/Servlets.PaginaUsuario").forward(request, response);
    }
}
