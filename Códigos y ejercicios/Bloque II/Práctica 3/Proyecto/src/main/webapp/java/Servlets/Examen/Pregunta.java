package Servlets.Examen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Servlets.Examenes.Pregunta", urlPatterns = {"/Servlets.Examenes.Pregunta"})
public class Pregunta extends HttpServlet {

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
                "    <title></title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <p>\n" +
                "      Empleo actual:" +
                "      <br>\n" +
                "      <input type=\"radio\" name=\"respuesta\" value=\"si\"> Sí <br>\n" +
                "      <input type=\"radio\" name=\"respuesta\" value=\"no\"> No <br>\n" +
                "      <input type=\"radio\" name=\"respuesta\" value=\"veces\"> A veces\n" +
                "    </p>\n" +
                "    <p>" +
                "      <input type=\"submit\" value=\"Enviar datos\">" +
                "    </p>" +
                "    <a href=Servlets.Examenes.Pregunta> Siguiente pregunta </a><br>" +   // REDIRECCIÓN
                "    <a href=Servlets.Examenes.Estadisticas> Finalizar TEST </a><br>" +   // REDIRECCIÓN
                "  </body>\n" +
                "</html>");
    }
}
