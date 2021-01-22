package Servlets.Examen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Servlets.Examen.Examen", urlPatterns = {"/Servlets.Examen.Examen"})
public class Examen extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        generarPagina(respuesta);
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        doGet(peticion, respuesta);
    }


    // Métodos propios
    private void generarPagina(HttpServletResponse respuesta) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
        "  <head>\n" +
        "    <meta charset=\"utf-8\">\n" +
        "    <title>Nuevo Examen</title>\n" +
        "  </head>\n" +
        "  <body>\n" +
        "    <a href=Servlets.PerfilUsuario> Crear </a><br>" +     // REDIRECCIÓN
        "  </body>\n" +
        "</html>");
    }
}
