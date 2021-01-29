package Servlets.Paginas;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;


@WebServlet (name = "Servlets.Paginas.Principal", urlPatterns = {"/Servlets.Paginas.Principal"})
public class Principal extends HttpServlet {

    private final String RUTA_BASE = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\resources\\";
    private final String RUTA_CSS = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\webapp\\estilos.css";

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        generarPagina(respuesta);

        // respuesta.sendRedirect("Servlets.Paginas.Principal");
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
    private void generarPagina(HttpServletResponse respuesta) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        // TODO - Estilizar la página principal
        escritor.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "        <link href=\"" + RUTA_CSS + "\" rel=\"stylesheet\" type=\"text/css\" />" +
                "        <title>Inicio</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div id=\"contenido\">\n" +
                "            <div id=\"banner\">\n" +
                "                <img src=\"" + RUTA_BASE + "Siette.jpg\">\n" +
                "            </div>\n" +
                "            <ul id=\"navegador\">\n" +
                "                    <li><a href=\"Servlets.Paginas.Login\">Login</a></li>\n" +
                "                    <li><a href=\"Servlets.Paginas.Registro\">Registro</a></li>\n" +
                "            </ul>\n" +
                "            <h1>Bienvenido al Sistema Tutor</h1>\n" +
                "                <p>Inserte su queja acá.</p>\n" +
                "            <p id=\"Copyright\">Antonio J. Galán Herrera (Universidad de Málaga)</p>\n" +
                "            <p id=\"Copyright\">Copyright(c) Todos los derechos reservados.</p>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");

        escritor.close();
    }
}
