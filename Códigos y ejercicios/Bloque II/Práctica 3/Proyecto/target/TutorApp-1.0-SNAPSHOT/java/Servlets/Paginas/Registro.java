package Servlets.Paginas;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import java.io.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Servlets.Paginas.Registro", urlPatterns = {"/Servlets.Paginas.Registro"})
public class Registro extends HttpServlet {


    private String usuario = "", contra = "";
    private String errorUsuario = "", errorContra = "";


    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException, ServletException {
        Map errores = (Map) peticion.getAttribute("errors");

        // TODO - Añadir el resto de campos
        if (errores != null) {
            if (errores.containsKey("loginName")) {
                usuario = peticion.getParameter("loginName");
                errorUsuario = "<font color=\"red\"><b><b>" + errores.get("loginName") + "</b></font>";

            } else if (errores.containsKey("password")) {
                contra = peticion.getParameter("password");
                errorContra = "<font color=\"red\"><b><b>" + errores.get("password") + "</b></font>";
            }
        }

        generarPagina(respuesta);
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
    public void generarPagina(HttpServletResponse respuesta) throws IOException {
        // TODO - Estilizar la página de registro
        // TODO - Implementar 'required' en USUARIO y CONTRASEÑA
        // Generar respuesta
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        // Inicio
        escritor.println("<html><head><title>");
        escritor.println("Registro");
        escritor.println("</title></head>");
        escritor.println("<body text=\"#000000\" bgcolor=\"#ffffff\">");

        // Inicio del formulario
        escritor.println("<form method=\"POST\" action=\"Servlets.Proceso.Registro\">");

        escritor.println("<h1 align=\"center\"> Registro en el Sistema Tutor </h1>");
        escritor.println("<h1 align=\"center\"> Introduzca usuario y contraseña </h1>");

        // Inicio de la table
        escritor.println("<table width=\"100%\" border=\"0\" align=\"center\"cellspacing=\"12\">");

        // Login
        escritor.println("<tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> Usuario </th>");

        escritor.println("<td align=\"left\">"
                + "<input type=\"text\" name=\"loginName\" "
                + "value=\"" + usuario + "\" size=\"16\" maxlength=\"16\">" + errorUsuario + "</td>");
        escritor.println("</tr><br>");

        escritor.println("<tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> Contraseña </th>");
        escritor.println("<td align=\"left\"> <input type=\"password\" name=\"password\" "
                + " value=\"" + contra + "\" size=\"16\" maxlength=\"16\">" + errorContra + "</td>");
        escritor.println("</tr><tr>");

        escritor.println("<th align=\"right\" width=\"50%\"> Repite contraseña </th>");
        escritor.println("<td align=\"left\"> <input type=\"password\" name=\"password2\" </td>");
        escritor.println("</tr>");

        escritor.println("</tr><tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> DNI </th>");
        escritor.println("<td align=\"left\"> <input type=\"text\" name=\"dni\" required </td>");
        escritor.println("</tr>");

        escritor.println("</tr><tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> Correo electrónico </th>");
        escritor.println("<td align=\"left\"> <input type=\"text\" name=\"email\" required </td>");
        escritor.println("</tr>");

        escritor.println("</tr><tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> Teléfono </th>");
        escritor.println("<td align=\"left\"> <input type=\"text\" name=\"telefono\" required </td>");
        escritor.println("</tr>");

        escritor.println("</tr><tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> Dirección </th>");
        escritor.println("<td align=\"left\"> <input type=\"text\" name=\"direccion\" required </td>");
        escritor.println("</tr>");

        // Boton de login
        escritor.println("<tr>");
        escritor.println("<td width=\"50%\"></td>");
        escritor.println("<td align=\"left\" width=\"50%\">");
        escritor.println("<input type=\"submit\" value=\"Registrarse y acceder \"></td>");
        escritor.println("</tr>");

        // Fin de tabla
        escritor.println("</table>");

        // Fin
        escritor.println("</body></html>");

        // Cerrar el descriptor de salida
        escritor.close();
    }
}
