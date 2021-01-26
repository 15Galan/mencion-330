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
//        // Botón de LOGIN, botón de REGISTRO y logo de Aplicación
//        escritor.println("<html><head><title>");
//        escritor.println("Biblioteca Pública María Moliner");
//        escritor.println("</title></head>");
//        escritor.println("<body text=\"#000000\" bgcolor=\"#ffffff\" link=\"#000ee0\" vlink=\"#551a8b\" alink=\"#000ee0\">");
//        escritor.println("<h1>¡Buenos días, " + usuario + "! </h1>");
//        escritor.println("<br><br><br>");
//        // escritor.println("<form method=\"POST\" action=\"BusquedaServletGuest\">");
//        escritor.println("Busqueda<br>");
//        escritor.println("<select name=\"tipo\">\n"
//            + "<option selected>Titulo\n"
//            + "<option>Autor\n</select>"
//            + "<input type=\"text\" name=\"patronus\" value=\"\" required> "
//            + "<input type=\"submit\" name=\"send\" value =\"BUSCAR\"> </form>");
//        escritor.println("<a href=Servlets.Paginas.Login> Login </a><br>");          // REDIRECCIÓN
//        escritor.println("<a href=Servlets.Paginas.Registro> Registro </a><br>");           // REDIRECCIÓN
//        escritor.println("<a href=Servlets.Paginas.Usuario> Auxiliar </a><br>");      // REDIRECCIÓN (AUX)
//        escritor.println("</body></html>");

//        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
//                "  <head>\n" +
//                "    <meta charset=\"utf-8\">\n" +
//                "    <title>Nuevo Examenes</title>\n" +
//                "  </head>\n" +
//                "  <body>\n" +
//                "    <a href=Servlets.Paginas.Login> Login </a><br>" +              // REDIRECCIÓN
//                "    <a href=Servlets.Paginas.Registro> Registro </a><br>" +        // REDIRECCIÓN
////                "    <a href=Servlets.Paginas.Usuario> Auxiliar </a><br>" +      // REDIRECCIÓN (AUX)
//                "  </body>\n" +
//                "</html>");

        escritor.println("<!DOCTYPE <!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
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
