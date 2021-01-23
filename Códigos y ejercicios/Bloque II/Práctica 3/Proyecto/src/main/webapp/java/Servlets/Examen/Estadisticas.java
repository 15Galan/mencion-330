package Servlets.Examen;

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
    private void generarPagina(HttpServletResponse respuesta) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Estadísticas</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <a href=Servlets.PaginaUsuario> Volver al perfil </a><br>" +       // REDIRECCIÓN
                "  </body>\n" +
                "</html>");
    }
}
