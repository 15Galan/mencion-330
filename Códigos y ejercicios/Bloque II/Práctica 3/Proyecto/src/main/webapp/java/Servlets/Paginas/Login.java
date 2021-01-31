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
        escritor.println("<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"es\">\n" +
                "    <head>\n" +
                "        <title>Login</title>\n" +
                "\n" +
                "        <meta charset=\"utf-8\">\n" +
                "\n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"adornos/estilos.css\">\n" +
                "    </head>\n" +
                "\n" +
                "    <body>\n" +
                "        <div id=\"contenido\">\n" +
                "            <div id=\"banner\">\n" +
                "                <img id=\"logo\" src=\"adornos/Seiis.jpg\" alt=\"\">\n" +
                "\n" +
                "                <h1>Inicia Sesión</h1>\n" +
                "            </div>\n" +
                "            \n" +
                "            <form method=\"POST\" action=\"Servlets.Proceso.Login\">\n" +
                "                <table align=\"center\">\n" +
                "                    <tr>\n" +
                "                        <th align=\"right\" width=\"50%\">Usuario</th>\n" +
                "\n" +
                "                        <td>\n" +
                "                            <input type=\"text\" name=\"loginName\" value=\"\" size=\"16\" maxlength=\"16\"></td>\n" +
                "                    </tr>\n" +
                "                    <br>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <th align=\"right\" width=\"50%\">Contraseña</th>\n" +
                "\n" +
                "                        <td align=\"left\">\n" +
                "                            <input type=\"password\" name=\"password\" value=\"\" size=\"16\" maxlength=\"16\"></td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <td width=\"50%\"></td>\n" +
                "\n" +
                "                        <td align=\"left\" width=\"50%\">\n" +
                "                            <input type=\"submit\" value=\"Login\"></td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");

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
