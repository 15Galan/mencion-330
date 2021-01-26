package Servlets.Paginas;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import java.io.*;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Servlets.Paginas.Login", urlPatterns = {"/Servlets.Paginas.Login"})
public class Login extends HttpServlet {

    private final String RUTA_BASE = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\resources\\";
//    private String usuariosTXT, examenesTXT;

    private String usuario = "", contra = "";
    private String errorUsuario = "", errorContra = "";


    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        inicializarUsuarios();

        erroresSesion(peticion);

        generarPagina(respuesta);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }


    // Métodos propios
    /**
     * Analiza los posibles errores de un inicio de sesión
     * y los almacena en un mapa para su posterior tratamiento.
     *
     * @param peticion  Petición que contiene los posibles errores
     */
    public void erroresSesion(HttpServletRequest peticion) {
        Map errores = (Map) peticion.getAttribute("errors");

        if (errores != null) {
            String errorHeader = "<font color=\"red\"><b><b>";
            String errorFooter = "</b></font>";

            if (errores.containsKey("loginName")) {
                usuario = peticion.getParameter("loginName");
                errorUsuario = errorHeader + errores.get("loginName") + errorFooter;

            } else if (errores.containsKey("password")) {
                contra = peticion.getParameter("password");
                errorContra = errorHeader + errores.get("password") + errorFooter;
            }
        }
    }

    /**
     * Define las sentencias HTML que forman la página.
     *
     * @param respuesta     Conexión a la que se envía la página
     *
     * @throws IOException  Error al escribir datos en la respuesta
     */
    public void generarPagina(HttpServletResponse respuesta) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        /* Inicio. */
        escritor.println("<html><head><title>");
        escritor.println("Biblioteca Pública María Moliner");
        escritor.println("</title></head>");
        escritor.println("<body text=\"#000000\" bgcolor=\"#ffffff\">");

        /* Inicio del form. */
        escritor.println("<form method=\"POST\" action=\"Servlets.Proceso.Login\">");
        escritor.println("<h1 align=\"center\"> Biblioteca Pública María Moliner </h1>");
        escritor.println("<h1 align=\"center\"> Como la del diccionario de uso de la lengua. </h1>");

        /* Inicio de la table. */
        escritor.println("<table width=\"100%\" border=\"0\" align=\"center\"cellspacing=\"12\">");

        /* Login. */
        escritor.println("<tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> Usuario </th>");

        escritor.println("<td align=\"left\">" + "<input type=\"text\" name=\"loginName\" "
            + " value=\"" + usuario + "\" size=\"16\" maxlength=\"16\">"
            + errorUsuario + "</td>");
        escritor.println("</tr><br>");

        escritor.println("<tr>");
        escritor.println("<th align=\"right\" width=\"50%\"> Contraseña </th>");
        escritor.println("<td align=\"left\"> <input type=\"password\" name=\"password\" " + " value=\"" + contra
            + "\" size=\"16\" maxlength=\"16\">" + errorContra + "</td>");
        escritor.println("</tr>");

        /* Registrarse/Invitado */
        escritor.println("<tr>");
        escritor.println("<td width=\"50%\"></td>");
        escritor.println("<td align=\"left\" width=\"50%\">");
        escritor.println("<a href=\"Servlets.Paginas.Registro\">Registrarse</a>");
        escritor.println("</tr><tr>");
        escritor.println("<td width=\"50%\"></td>");
        escritor.println("</tr>");

        /* Boton de login. */
        escritor.println("<tr>");
        escritor.println("<td width=\"50%\"></td>");
        escritor.println("<td align=\"left\" width=\"50%\"> ");
        escritor.println("<input type=\"submit\" value=\"Login\"></td>");
        escritor.println("</tr>");

//        escritor.println("<p>¿" + usuariosTXT + " existe?</p>");
//        escritor.println("<p>" + new File(usuariosTXT).exists() + "</p>");
//        escritor.println("<p>¿" + examenesTXT + " existe?</p>");
//        escritor.println("<p>" + new File(examenesTXT).exists() + "</p>");

        /* Fin de tabla. */
        escritor.println("</table>");

        /* Fin. */
        escritor.println("</body></html>");

//        escritor.println("<html lang=\"es\" dir=\"ltr\">\n" +
//                "  <head>\n" +
//                "    <meta charset=\"utf-8\">\n" +
//                "    <title>Mostrar Login</title>\n" +
//                "  </head>\n" +
//                "  <body>\n" +
//                "    <a href=Servlets.Paginas.Principal> Crear </a><br>" +     // REDIRECCIÓN
//                "  </body>\n" +
//                "</html>");

        escritor.close();
    }

    /**
     * Introduce unos usuarios y contraseñas predefinidos
     * en el fichero que actúa como base de datos.
     *
     * @throws IOException    El fichero no se encuentra
     */
    public void inicializarUsuarios() throws IOException {
        PrintWriter escritor;
        File fichero;

        // Generar las cuentas de usuario
        fichero = new File(RUTA_BASE + "usuarios.txt");
//        usuariosTXT = fichero.getAbsolutePath();

        if (!fichero.exists()) {
            escritor = new PrintWriter(fichero);

            escritor.println("srgalan 1234567890");
            escritor.println("alumno siette");
            escritor.println("jose jose");

            escritor.close();
        }
    }
}
