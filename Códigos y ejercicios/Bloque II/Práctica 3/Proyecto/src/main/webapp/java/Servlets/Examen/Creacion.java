package Servlets.Examen;

import Funciones.Examen;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Servlets.Examenes.Creacion", urlPatterns = {"/Servlets.Examenes.Creacion"})
public class Creacion extends HttpServlet {

    private Examen examen;

    @Override
    public void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
        examen = new Examen("Nuevo Examen", "Blablabla blablá, bla blabla...");

        generarPagina(respuesta);
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
     *
     * @throws IOException  Error al escribir datos en la respuesta
     */
    private void generarPagina(HttpServletResponse respuesta) throws IOException {
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
                "            <h1>Nuevo examen creado</h1>\n" +
                "            \n" +
                "            <p>Nombre: " + examen.getTitulo() + "</p>\n" +
                "            <p>Descripción: " + examen.getDescripcion() + "</p>\n" +
                "            <p>Preguntas: " + examen.getPreguntas().size() + "</p>\n" +
                "\n" +
                "            <form method=\"POST\" action=\"Servlets.Paginas.Usuario\">\n" +
                "                <input type=\"submit\" value=\"Regresar\">\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");

        escritor.close();
    }
}
