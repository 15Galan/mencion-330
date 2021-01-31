package Servlets.Paginas;

/* @author  Lidia Fuentes
 * @editor  Antonio J. Galán Herrera
 */

import Funciones.Examen;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;


@WebServlet (name = "Servlets.Paginas.Usuario", urlPatterns = {"/Servlets.Paginas.Usuario"})
public class Usuario extends HttpServlet {

    private final String RUTA_BASE = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\resources\\";

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        inicializarExamenes();
        generarPagina(respuesta, peticion.getParameter("loginName"));

        // respuesta.sendRedirect("Servlets.Paginas.Usuario");
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
    private void generarPagina(HttpServletResponse respuesta, String usuario) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        // TODO - Estilizar la página principal
        escritor.println("<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"es\">\n" +
                "    <head>\n" +
                "        <title>Nuevo Examenes</title>\n" +
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
                "                \n" +
                "                <h1>Conectado como: " + usuario + "</h1>\n" +
                "            </div>\n" +
                "            \n" +
                "            <ul id=\"navegador\">\n" +
                "                <li><a href=Servlets.Examenes.Creacion>Generar TEST</a></li>\n" +
                "                <li><a href=Servlets.Examenes.Seleccion>Elegir TEST</a></li>\n" +
                "                <li><br><a href=Servlets.Paginas.Principal>Volver al Inicio</a></li>\n" +
                "            </ul>" +
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

    public void inicializarExamenes() {
        File fichero = new File(RUTA_BASE + "examenes.txt");
//        examenesTXT = fichero.getAbsolutePath();

        if (!fichero.exists()) {
            for (int i = 0; i < 3; i++) {
                anadirExamen(new Examen("Inicial " + (i+1), "Test generado en el arranque"), fichero);
            }
        }
    }

    public void anadirExamen(Examen examen, File fichero) {
        try (FileWriter escritor = new FileWriter(fichero, true)) {

            escritor.append(examen.getTitulo()).append("\n");
            escritor.append(examen.getDescripcion()).append("\n");

            for (int i = 0; i < examen.getPreguntas().size(); i++) {
                escritor.append(examen.getPreguntas().get(i)).append("\n");
                escritor.append(examen.getRespuestas().get(i)).append("\n");
            }

            escritor.append("\n");

        } catch (IOException e) {
            System.err.println("No pudo escribirse sobre '" + RUTA_BASE + "examenes.txt'.");
        }
    }
}
