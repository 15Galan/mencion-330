package Servlets;/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;


@WebServlet (name = "Servlets.PerfilUsuario", urlPatterns = {"/Servlets.PerfilUsuario"})
public class PerfilUsuario extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        generarPagina(respuesta, peticion.getParameter("loginName"));

        // respuesta.sendRedirect("Servlets.PerfilUsuario");
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        doGet(peticion, respuesta);
    }


    // Métodos propios
    private void generarPagina(HttpServletResponse respuesta, String usuario) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        // TODO - Estilizar la página principal
//        // Botón de LOGIN, botón de REGISTRO y logo de Aplicación
//        escritor.println("<html><head><title>");
//        escritor.println("Biblioteca Pública María Moliner");
//        escritor.println("</title></head>");
//        escritor.println("<body text=\"#000000\" bgcolor=\"#ffffff\" link=\"#000ee0\" vlink=\"#551a8b\" alink=\"#000ee0\">");
//        escritor.println("<h1>¡Buenos días, " + usuario + "! </h1>");
//        escritor.println("<br><br><br>");
//        escritor.println("Busqueda<br>");
//        escritor.println("<select name=\"tipo\">\n"
//            + "<option selected>Titulo\n"
//            + "<option>Autor\n</select>"
//            + "<input type=\"text\" name=\"patronus\" value=\"\" required> "
//            + "<input type=\"submit\" name=\"send\" value =\"BUSCAR\"> </form>");
//        escritor.println("<a href=Servlets.Examen.Examen> Generar TEST </a><br>");      //  REDIRECCIÓN
//        escritor.println("<a href=Servlets.Examen.Seleccion> Elegir TEST </a><br>");    // REDIRECCIÓN
//        escritor.println("</body></html>");

        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Nuevo Examen</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <a href=Servlets.Examen.Examen> Generar TEST </a><br>" +       // REDIRECCIÓN
                "    <a href=Servlets.Examen.Seleccion> Elegir TEST </a><br>" +     // REDIRECCIÓN
                "  </body>\n" +
                "</html>");

        escritor.close();
    }
}
