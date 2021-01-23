package Funciones;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Examen {

    // Variables
    private String titulo;
    private String descripcion;
    private Map<Integer, String> preguntas;


    // Constructor
    public Examen(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        preguntas = new HashMap<>();
        generarExamen();
    }


    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Map<Integer, String> getPreguntas() {
        return preguntas;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPreguntas(Map<Integer, String> preguntas) {
        this.preguntas = preguntas;
    }


    /**
     * Genera de 5 a 10 preguntas cuyas respuestas se eligen aleatoriamente.
     */
    private void generarExamen() {
        int longitud = ThreadLocalRandom.current().nextInt(5, 10+1), x;
        String[] respuestas = {"Sí", "No", "A veces"};

        for (int i = 0; i < longitud; i++) {
            x = ThreadLocalRandom.current().nextInt(0, 2+1);
            preguntas.put(i, respuestas[x]);
        }
    }

    /**
     * Comprueba si la respuesta recibida por el usuario es correcta.
     *
     * @param pregunta      Identificador de la pregunta
     * @param respuesta     Valor de la respuesta recibida
     *
     * @return      TRUE si es correcta, FALSE en caso contrario.
     */
    private boolean correcta(int pregunta, String respuesta) {
        return preguntas.get(pregunta).equalsIgnoreCase(respuesta);
    }
}
