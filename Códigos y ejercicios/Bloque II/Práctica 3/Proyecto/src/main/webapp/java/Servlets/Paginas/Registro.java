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
//        escritor.println("<!DOCTYPE html>\n" +
//                "\n" +
//                "<html lang=\"es\">\n" +
//                "    <head>\n" +
//                "        <title>Registro</title>\n" +
//                "\n" +
//                "        <meta charset=\"utf-8\">\n" +
//                "\n" +
//                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"adornos/estilos.css\">\n" +
//                "    </head>\n" +
//                "\n" +
//                "    <body>" +
//                "        <div id\"contenido\">\n" +
//                "            <div id=\"banner\">\n" +
//                "                <img id=\"logo\" src=\"adornos/Seiis.jpg\" alt=\"\">\n" +
//                "\n" +
//                "                <h1>Registro</h1>\n" +
//                "            </div>" +
//                "        <form method=\"POST\" action=\"Servlets.Proceso.Registro\">\n" +
//                "            <table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"12\">\n" +
//                "\n" +
//                "                <tr>\n" +
//                "                    <th align=\"right\" width=\"50%\">Usuario</th>\n" +
//                "\n" +
//                "                    <td align=\"left\">\n" +
//                "                        <input type=\"text\" name=\"loginName\" value=\"" + usuario + "\" size=\"16\" maxlength=\"16\">\n" +
//                "                       " + errorUsuario +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "                <br>\n" +
//                "\n" +
//                "                <tr>\n" +
//                "                    <th align=\"right\" width=\"50%\">Contraseña</th>\n" +
//                "                    \n" +
//                "                    <td align=\"left\">\n" +
//                "                        <input type=\"password\" name=\"password\" value=\"" + contra + "\" size=\"16\" maxlength=\"16\">\n" +
//                "                        " + errorContra +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "                \n" +
//                "                <tr>\n" +
//                "                    <th align=\"right\" width=\"50%\">Repite contraseña</th>\n" +
//                "                    \n" +
//                "                    <td align=\"left\">\n" +
//                "                        <input type=\"password\" name=\"password2\">\n" +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "                \n" +
//                "                <tr>\n" +
//                "                    <th align=\"right\" width=\"50%\"> DNI </th>\n" +
//                "                    \n" +
//                "                    <td align=\"left\">\n" +
//                "                        <input type=\"text\" name=\"dni\" required>\n" +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "                \n" +
//                "                <tr>\n" +
//                "                    <th align=\"right\" width=\"50%\">Correo electrónico</th>\n" +
//                "                    \n" +
//                "                    <td align=\"left\">\n" +
//                "                        <input type=\"text\" name=\"email\" required>\n" +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "                                \n" +
//                "                <tr>\n" +
//                "                    <th align=\"right\" width=\"50%\">Teléfono</th>\n" +
//                "                    \n" +
//                "                    <td align=\"left\">\n" +
//                "                        <input type=\"text\" name=\"telefono\" required>\n" +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "                                \n" +
//                "                <tr>\n" +
//                "                    <th align=\"right\" width=\"50%\">Dirección</th>\n" +
//                "                    \n" +
//                "                    <td align=\"left\">\n" +
//                "                        <input type=\"text\" name=\"direccion\" required>\n" +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "                \n" +
//                "                <tr>\n" +
//                "                    <td width=\"50%\"></td>\n" +
//                "                    \n" +
//                "                    <td align=\"left\" width=\"50%\">\n" +
//                "                        <input type=\"submit\" value=\"Registrarse y acceder\">\n" +
//                "                    </td>\n" +
//                "                </tr>\n" +
//                "            </table>\n" +
//                "        </form>\n" +
//                "    </div></body>\n" +
//                "</html>\n");

        escritor.println("<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"es\">\n" +
                "    <head>\n" +
                "        <title>Registro</title>\n" +
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
                "                <h1>Registro</h1>\n" +
                "            </div>\n" +
                "\n" +
                "            <form method=\"POST\" action=\"Servlets.Proceso.Registro\">\n" +
                "                <table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"12\">\n" +
                "                <tr>\n" +
                "                    <th align=\"right\" width=\"50%\">Usuario</th>\n" +
                "\n" +
                "                    <td align=\"left\">\n" +
                "                        <input type=\"text\" name=\"loginName\" value=\"" + usuario + "\" size=\"16\" maxlength=\"16\">\n" +
                "                       " + errorUsuario +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <br>\n" +
                "\n" +
                "                <tr>\n" +
                "                    <th align=\"right\" width=\"50%\">Contraseña</th>\n" +
                "                    \n" +
                "                    <td align=\"left\">\n" +
                "                        <input type=\"password\" name=\"password\" value=\"" + contra + "\" size=\"16\" maxlength=\"16\">\n" +
                "                        " + errorContra +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <th align=\"right\" width=\"50%\">Repite contraseña</th>\n" +
                "\n" +
                "                        <td align=\"left\">\n" +
                "                            <input type=\"password\" name=\"password2\">\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <th align=\"right\" width=\"50%\"> DNI </th>\n" +
                "\n" +
                "                        <td align=\"left\">\n" +
                "                            <input type=\"text\" name=\"dni\" required>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <th align=\"right\" width=\"50%\">Correo electrónico</th>\n" +
                "\n" +
                "                        <td align=\"left\">\n" +
                "                            <input type=\"text\" name=\"email\" required>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <th align=\"right\" width=\"50%\">Teléfono</th>\n" +
                "\n" +
                "                        <td align=\"left\">\n" +
                "                            <input type=\"text\" name=\"telefono\" required>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <th align=\"right\" width=\"50%\">Dirección</th>\n" +
                "\n" +
                "                        <td align=\"left\">\n" +
                "                            <input type=\"text\" name=\"direccion\" required>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr>\n" +
                "                        <td width=\"50%\"></td>\n" +
                "\n" +
                "                        <td align=\"left\" width=\"50%\">\n" +
                "                            <input type=\"submit\" value=\"Registrarse\">\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");

        escritor.close();
    }
}
