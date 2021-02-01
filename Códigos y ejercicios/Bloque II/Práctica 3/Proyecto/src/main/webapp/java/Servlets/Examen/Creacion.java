package Servlets.Examen;

import Funciones.Examen;
import Funciones.LoginManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Servlets.Examenes.Creacion", urlPatterns = {"/Servlets.Examenes.Creacion"})
public class Creacion extends HttpServlet {

    private Examen examen;
    private String RUTA_BASE;
    private String documento;


    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        RUTA_BASE = "C:\\Users\\Usuario\\Desktop\\D.S.T\\Códigos y ejercicios\\Bloque II\\Práctica 3\\Proyecto\\src\\main\\webapp\\examenes\\";
        documento = generarNombre();

        examen = new Examen("Nuevo Examen", "Blablabla blablá, bla blabla...");
        examen.escribirFichero(new File(RUTA_BASE + documento));

        generarPagina(respuesta, LoginManager.getLoginName(peticion));
    }

    @Override
    public void doPost(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        doGet(peticion, respuesta);
    }


    // Métodos propios
    /**
     * Define las sentencias HTML que forman la página.
     *
     * @param respuesta     Conexión a la que se envía la página
     * @param usuario       Dato de LOGIN
     *
     * @throws IOException  Error al escribir datos en la respuesta
     */
    private void generarPagina(HttpServletResponse respuesta, String usuario) throws IOException {
        respuesta.setContentType("text/html; charset=ISO-8859-1");

        PrintWriter escritor = respuesta.getWriter();

        escritor.println("<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"es\">\n" +
                "    <head>\n" +
                "        <title>Nuevo Examen</title>\n" +
                "\n" +
                "        <meta charset=\"utf-8\">\n" +
                "\n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"adornos/estilos.css\">\n" +
                "    </head>\n" +
                "\n" +
                "    <body>\n" +
                "        <div id=\"contenido\">\n" +
                "            <h1>Guardado como '" + documento + "'</h1>\n" +
                "            \n" +
                "            <p>Nombre: " + examen.getTitulo() + "</p>\n" +
                "            <p>Descripción: " + examen.getDescripcion() + "</p>\n" +
                "            <p>Preguntas: " + examen.getPreguntas().size() + "</p>\n" +
                "\n" +
                "            <form method=\"POST\" action=\"Servlets.Paginas.Usuario\">\n" +
                "                <input type=\"submit\" value=\"Regresar\">\n" +
                "            </form>\n" +
                "\n" +
                "            <ul id=\"navegador\">\n" +
                "                <li><br><a href=\"Servlets.Paginas.Principal\">Volver al Inicio</a></li>\n" +
                "            </ul>" +
                "\n" +
                "            <p>Conectado como '" + usuario + "'</p>" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");

        escritor.close();
    }


    // Métodos propios
    /**
     * Genera el nombre del fichero de un examen
     * respetando el de los exámenes anteriores.
     *
     * @return  Un nombre para el fichero de un examen
     */
    private String generarNombre() {
        return "examen" + (new File(RUTA_BASE).listFiles().length + 1) + ".txt";
    }
}
