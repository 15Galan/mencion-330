package Servlets.Examen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Servlets.Examenes.Seleccion", urlPatterns = {"/Servlets.Examenes.Seleccion"})
public class Seleccion extends HttpServlet {

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

        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
            "  <head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <title>Examenes Seleccionado</title>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <a href=Servlets.Examenes.Pregunta> Comenzar </a><br>" +   // REDIRECCIÓN
            "  </body>\n" +
            "</html>");

        escritor.close();
    }
}
