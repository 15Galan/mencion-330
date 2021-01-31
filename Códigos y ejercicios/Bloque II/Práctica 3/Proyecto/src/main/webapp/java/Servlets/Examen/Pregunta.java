package Servlets.Examen;

import Funciones.Examen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Servlets.Examen.Pregunta", urlPatterns = {"/Servlets.Examen.Pregunta"})
public class Pregunta extends HttpServlet {


    private Examen examen = new Examen("Ejemplo", "Examen de ejemplo");
    private int iteracion = 0;


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
                "<html lang=\"es\" dir=\"ltr\">\n" +
                "    <head>\n" +
                "        <title>Pregunta</title>\n" +
                "\n" +
                "        <meta charset=\"utf-8\">\n" +
                "\n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"adornos/estilos.css\">\n" +
                "    </head>\n" +
                "\n" +
                "    <body>\n" +
                "        <div id=\"contenido\">\n" +
                "            <p>\n" +
                "                Pregunta " + (++iteracion) + "\n" +
                "            </p>\n" +
                "\n" +
                "            <p>\n" +
                "                <br>\n" +
                "\n" +
                "                <input type=\"radio\" name=\"respuesta\" value=\"si\">Sí<br>\n" +
                "                <input type=\"radio\" name=\"respuesta\" value=\"no\">No<br>\n" +
                "                <input type=\"radio\" name=\"respuesta\" value=\"aveces\">A veces<br>\n" +
                "            </p>\n" +
                "\n");

        if (iteracion >= examen.getPreguntas().size()) {
            // escritor.println("<p><input type=\"submit\" href=Servlets.Examenes.Estadisticas value=\"Finalizar\"></p>");    // REDIRECCIÓN
            escritor.println("<p><input type=\"submit\" href=Servlets.Examenes.Usuario value=\"Finalizar\"></p>");    // REDIRECCIÓN

        } else {
            escritor.println("<p><input type=\"submit\" href=Servlets.Examenes.Pregunta value=\"Siguiente\"></p>");   // REDIRECCIÓN
        }

        escritor.println("        </div>\n" +
                "    </body>\n" +
                "</html>\n");

        escritor.close();
    }
}
