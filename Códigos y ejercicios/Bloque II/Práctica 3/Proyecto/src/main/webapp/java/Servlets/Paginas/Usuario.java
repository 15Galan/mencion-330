package Servlets.Paginas;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.Examen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;


@WebServlet (name = "Servlets.Paginas.Usuario", urlPatterns = {"/Servlets.Paginas.Usuario"})
public class Usuario extends HttpServlet {

    private final String RUTA_BASE = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\resources\\";

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        inicializarExamenes();

        generarPagina(respuesta, peticion.getParameter("loginName"));

        // respuesta.sendRedirect("Servlets.Paginas.Usuario");
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        doGet(peticion, respuesta);
    }


    // Métodos propios
    /**
     * Define las sentencias HTML que forman la página.
     *
     * @param respuesta     Conexión a la que se envía la página
     *
     * @throws IOException  Error al escribir datos en la respuesta
     */
    private void generarPagina(HttpServletResponse respuesta, String usuario) throws IOException {
        Examen incial = new Examen("Inicial", "Test generado en el arranque");

        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        // TODO - Estilizar la página principal
        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
            "  <head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <title>Nuevo Examenes</title>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <a href=Servlets.Examenes.Creacion> Generar TEST </a><br>" +   // REDIRECCIÓN
            "    <a href=Servlets.Examenes.Seleccion> Elegir TEST </a><br>" +   // REDIRECCIÓN
            "  </body>\n" +
            "</html>");

        escritor.close();
    }

    public void inicializarExamenes() throws FileNotFoundException {
        PrintWriter escritor;

        File fichero = new File(RUTA_BASE + "examenes.txt");
//        examenesTXT = fichero.getAbsolutePath();

        if (!fichero.exists()) {
            escritor = new PrintWriter(fichero);

            for (int i = 1; i <= 3; i++) {
                escritor.println("Titulo " + i);
                escritor.println("Descripcion");
                escritor.println("preguntas...");
            }

            escritor.close();
        }
    }
}
