package Servlets;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import java.io.*;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Servlets.MostrarLogin", urlPatterns = {"/Servlets.MostrarLogin"})
public class MostrarLogin extends HttpServlet {


    private String usuario = "", contra = "";
    private String errorUsuario = "", errorContra = "";


    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        inicializar();

        Map errors = (Map) peticion.getAttribute("errors");

        if (errors != null) {
            String errorHeader = "<font color=\"red\"><b><b>";
            String errorFooter = "</b></font>";

            if (errors.containsKey("loginName")) {
                usuario = peticion.getParameter("loginName");
                errorUsuario = errorHeader + errors.get("loginName") + errorFooter;

            } else if (errors.containsKey("password")) {
                contra = peticion.getParameter("password");
                errorContra = errorHeader + errors.get("password") + errorFooter;
            }
        }

        generarPagina(respuesta);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }


    // Métodos propios
    public void generarPagina(HttpServletResponse respuesta) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

//        /* Inicio. */
//        escritor.println("<html><head><title>");
//        escritor.println("Biblioteca Pública María Moliner");
//        escritor.println("</title></head>");
//        escritor.println("<body text=\"#000000\" bgcolor=\"#ffffff\">");
//
//        /* Inicio del form. */
//        escritor.println("<form method=\"POST\" action=\"Servlets.ProcesoLogin\">");
//        escritor.println("<form method=\"POST\" action=\"Servlets.ProcesoLogin\">");
//        escritor.println("<h1 align=\"center\"> Biblioteca Pública María Moliner </h1>");
//        escritor.println("<h1 align=\"center\"> Como la del diccionario de uso de la lengua. </h1>");
//
//        /* Inicio de la table. */
//        escritor.println("<table width=\"100%\" border=\"0\" align=\"center\"cellspacing=\"12\">");
//
//        /* Login. */
//        escritor.println("<tr>");
//        escritor.println("<th align=\"right\" width=\"50%\"> Usuario </th>");
//
//        escritor.println("<td align=\"left\">" + "<input type=\"text\" name=\"loginName\" "
//                + " value=\"" + usuario + "\" size=\"16\" maxlength=\"16\">"
//                + errorUsuario + "</td>");
//        escritor.println("</tr><br>");
//
//        escritor.println("<tr>");
//        escritor.println("<th align=\"right\" width=\"50%\"> Contraseña </th>");
//        escritor.println("<td align=\"left\"> <input type=\"password\" name=\"password\" " + " value=\"" + contra
//                + "\" size=\"16\" maxlength=\"16\">" + errorContra + "</td>");
//        escritor.println("</tr>");
//
//        /* Registrarse/Invitado */
//        escritor.println("<tr>");
//        escritor.println("<td width=\"50%\"></td>");
//        escritor.println("<td align=\"left\" width=\"50%\"> " + "<a href=\"Servlets.Registro\">Registrarse</a>");
//        escritor.println("</tr><tr>");
//        escritor.println("<td width=\"50%\"></td>");
//        escritor.println("<td align=\"left\" width=\"50%\"> " + "Entrar como <a href=\"MainPageServletGuest\">invitado</a>");
//        escritor.println("</tr>");
//
//        /* Boton de login. */
//        escritor.println("<tr>");
//        escritor.println("<td width=\"50%\"></td>");
//        escritor.println("<td align=\"left\" width=\"50%\"> " + "<input type=\"submit\" value=\"Login\"></td>");
//        escritor.println("</tr>");
//
//        /* Fin de tabla. */
//        escritor.println("</table>");
//
//        /* Fin. */
//        escritor.println("</body></html>");

        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Mostrar Login</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <a href=Servlets.PaginaPrincipal> Crear </a><br>" +     // REDIRECCIÓN
                "  </body>\n" +
                "</html>");

        escritor.close();
    }

    /**
     * Introduce unos usuarios y contraseñas predefinidos
     * en el fichero que actúa como base de datos.
     *
     * @throws IOException    El fichero no se encuentra
     */
    public void inicializar() throws IOException {
        PrintWriter escritor;
        File fichero;

        fichero = new File("usuarios.txt");

        if (!fichero.exists()) {
            if (fichero.createNewFile()) {
                System.out.println("Fichero '" + fichero.getAbsolutePath() + "' creado.");

            } else {
                System.out.println("No pudo crearse el fichero '" + fichero.getName() + "'.");
            }

            escritor = new PrintWriter(fichero);

            escritor.println("juanita juanita");
            escritor.println("aprobado aprobado");
            escritor.println("juan juan");

            escritor.close();
        }

        fichero = new File("documentos.txt");

        if (!fichero.exists()) {
            if (fichero.createNewFile()) {
                System.out.println("Fichero '" + fichero.getAbsolutePath() + "' creado.");

            } else {
                System.out.println("No pudo crearse el fichero '" + fichero.getName() + "'.");
            }

            escritor = new PrintWriter(fichero);

            for (int i = 1; i <= 10; i++) {
                escritor.print("Titulo" + i + "\r\n");
                escritor.print("Autor" + i + "\r\n");
                escritor.print("titulo" + i + ".txt" + "\r\n");
                escritor.println("resumenblablabla" + i + "\r\n");
            }

            escritor.close();
        }
    }
}
