package Servlets.Examen;

import Funciones.Examen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Servlets.Examenes.Estadisticas", urlPatterns = {"/Servlets.Examenes.Estadisticas"})
public class Estadisticas extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        generarPagina(respuesta);
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
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

        escritor.println("<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"es\">\n" +
                "    <head>\n" +
                "        <title>Login</title>\n" +
                "    \n" +
                "        <meta charset=\"utf-8\">\n" +
                "    \n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"adornos/estilos.css\">\n" +
                "    </head>\n" +
                "    \n" +
                "    <body>\n" +
                "        <div id=\"contenido\">\n" +
                "            <p>$estadisticas</p>\n" +
                "\n" +
                "            <ul id=\"navegador\">\n" +
                "                <li><br><a href=\"Servlets.Paginas.Principal\">Volver al Inicio</a></li>\n" +
                "            </ul>" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");

        escritor.close();
    }
}
