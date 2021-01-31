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
                "\n" +
                "<html lang=\"es\">\n" +
                "    <head>\n" +
                "        <title>Inicio</title>\n" +
                "\n" +
                "        <meta charset=\"utf-8\">\n" +
                "        <!--<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">-->\n" +
                "\n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"adornos/estilos.css\">\n" +
                "    </head>\n" +
                "\n" +
                "    <body>\n" +
                "        <div id=\"contenido\">\n" +
                "             <div id=\"banner\">\n" +
                "                 <img id=\"logo\" src=\"adornos/Seiis.jpg\" alt=\"\">\n" +
                "                 \n" +
                "                 <div>\n" +
                "                     <button onclick=\"document.getElementById('logo').src='adornos/Seiis.jpg'\">-1</button>\n" +
                "                     <button onclick=\"document.getElementById('logo').src='adornos/Siette.jpg'\">+1</button>\n" +
                "                 </div>\n" +
                "\n" +
                "                 <h1>Bienvenido al Sistema Tutor</h1>\n" +
                "            </div>\n" +
                "\n" +
                "            <ul id=\"navegador\">\n" +
                "                <li><a href=\"Servlets.Paginas.Login\">Iniciar Sesión</a></li>\n" +
                "                <li><a href=\"Servlets.Paginas.Registro\">Registrarse</a></li>\n" +
                "            <!--<li><a href=\"Servlets.Paginas.Principal\">Salir</a></li>-->\n" +
                "            </ul>\n" +
                "\n" +
                "            <div class=\"footer\">\n" +
                "                <p id=top class=\"ftp\">Antonio J. Galán Herrera (Universidad de Málaga)</p>\n" +
                "                <p class=\"ftp\">Copyright &#169; Todos los derechos reservados</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");

        escritor.close();
    }
}
