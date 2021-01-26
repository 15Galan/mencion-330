package Servlets.Examen;

import Funciones.Examen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Servlets.Examenes.Creacion", urlPatterns = {"/Servlets.Examenes.Creacion"})
public class Creacion extends HttpServlet {

    private Examen examen;

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        examen = new Examen("Nuevo Examen", "Blablabla blablá, bla blabla...");

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
            "    <title>Nuevo Examen</title>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <p>Examen creado: " + examen.getTitulo() + "</p>\n" +
            "    <p>Descripción: " + examen.getDescripcion() + "</p>\n" +
            "    <p>Número de preguntas: " + examen.getPreguntas().size() + "</p>" +
            "    <form action=\"Servlets.Paginas.Usuario\" method=\"POST\">\n" +
            "      <input type=\"submit\" href=Servlets.Examenes.Usuario value=\"Volver al perfil\">\n" +
            "    </form>" +
//            "    <a href=Servlets.Paginas.Usuario> Volver al perfil </a><br>" +     // REDIRECCIÓN
            "  </body>\n" +
            "</html>");

        escritor.close();
    }
}
