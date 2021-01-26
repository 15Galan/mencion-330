package Funciones;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Examen {

    // Variables
    private String titulo;
    private String descripcion;
    private Map<Integer, String> preguntas;
    private Map<Integer, String> respuestas;
    private int correctas = 0, incorrectas = 0;


    // Constructor
    public Examen(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        preguntas = new HashMap<>();
        respuestas = new HashMap<>();

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

    public Map<Integer, String> getRespuestas() {
        return respuestas;
    }

    public int getCorrectas() {
        return correctas;
    }

    public int getIncorrectas() {
        return incorrectas;
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

    public void setRespuestas(Map<Integer, String> respuestas) {
        this.respuestas = respuestas;
    }

    public void setCorrectas(int correctas) {
        this.correctas = correctas;
    }

    public void setIncorrectas(int incorrectas) {
        this.incorrectas = incorrectas;
    }


    // Métodos propios
    /**
     * Genera de 5 a 10 preguntas cuyas respuestas se eligen aleatoriamente.
     */
    private void generarExamen() {
        int longitud = ThreadLocalRandom.current().nextInt(5, 10+1);

        for (int i = 0; i < longitud; i++) {
            preguntas.put(i, crearPregunta(longitud));
            respuestas.put(i, crearRespuesta());
        }
    }

    /**
     * Genera una pregunta aleatoria entre 10 opciones posibles.
     * Las opciones están predefinidas.
     *
     * @param longitud  Número de preguntas total del examen
     *
     * @return      Una pregunta relacionada con el estudio
     */
    private String crearPregunta(int longitud) {
        int semilla = ThreadLocalRandom.current().nextInt(1, longitud+1);
        String pregunta;

        switch (semilla) {
            case 1:
                pregunta = "¿Estudias los lunes?";
                break;

            case 2:
                pregunta = "¿Estudias los martes?";
                break;

            case 3:
                pregunta = "¿Estudias los miércoles?";
                break;

            case 4:
                pregunta = "¿Estudias los jueves?";
                break;

            case 5:
                pregunta = "¿Estudias los viernes?";
                break;

            case 6:
                pregunta = "¿Estudias los sábados?";
                break;

            case 7:
                pregunta = "¿Estudias los domingos?";
                break;

            case 8:
                pregunta = "¿Estudias solo?";
                break;

            case 9:
                pregunta = "¿Estudias acompañado?";
                break;

            case 10:
                pregunta = "¿Estudias?";
                break;

            default:
                pregunta = "¿Cómo hemos llegado aquí?";
        }

        return pregunta;
    }

    /**
     * Genera una respuesta aleatoria entre 3 opciones posibles.
     * Las opciones están predefinidas.
     *
     * @return  "Sí", "No" o "A veces"
     */
    private String crearRespuesta() {
        String[] opciones = {"Sí", "No", "A veces"};
        int semilla = ThreadLocalRandom.current().nextInt(0, opciones.length);

        return opciones[semilla];
    }

    /**
     * Comprueba si la respuesta recibida por el usuario es correcta.
     *
     * @param identificador      Identificador de la pregunta
     * @param respuesta     Valor de la respuesta recibida
     *
     * @return      TRUE si es correcta, FALSE en caso contrario.
     */
    private boolean esCorrecta(int identificador, String respuesta) {
        return respuestas.get(identificador).equalsIgnoreCase(respuesta);
    }

    /**
     * Genera las estadísticas del examen, indicando
     * la calificación y un mensaje acorde a la misma.
     *
     * @return  Mensaje con la calificación del alumno
     */
    private String estadisticas() {
        StringBuilder mensaje = new StringBuilder();
        int respondidas = correctas + incorrectas;

        if (respondidas != preguntas.size()) {
            mensaje.append("Se ha realizado el examen de forma errónea.");
            mensaje.append("Respondiste " + respondidas + "/" + preguntas.size());

        } else {
            mensaje.append("Calificación: " + correctas + "/" + preguntas.size());

            if (correctas >= 0.66 * preguntas.size()) {
                mensaje.append("Enhorabuena, buen trabajo.");

            } else if (correctas >= 0.33 * preguntas.size()) {
                mensaje.append("Necesitas estudiar un poco más.");

            } else {
                mensaje.append("No deberías persentarte.");
            }
        }

        return mensaje.toString();
    }
}
