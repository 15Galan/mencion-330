import java.util.Scanner;

public class CMDsimple {

    private String nombre;          // Nombre de la CMD
    private String info;            // Información de los comandos
    private String[] argumentos;    // Comando y sus argumentos


    public CMDsimple() {
        this("TFTP");
    }

    public CMDsimple(String nombre) {
        this.nombre = nombre;

        info =         "Comando              Argumentos             Descripcion\n" +
                "       ---------+-------------------------------+-----------------------------------------------------\n" +
                "       connect     <IP>  <puerto>                  Registrar el servidor con los datos indicados\n" +
//                "       connect                                     Mostrar los datos del servidor actual\n" +
                "       mode        <octet|ascii|netascii|mail>     Cambiar el modo de lectura/escritura de WRQ y RRQ\n" +
//                "       mode                                        Mostrar el modo de lectura/escritura actual" +
                "       get         <fichero>                       Recibir el archivo del servidor registrado\n" +
                "       put         <fichero>                       Enviar el archivo al servidor registrado\n" +
                "       quit                                        Terminar la conexión y cerrar el cliente\n" +
                "       ?                                           Mostrar de nuevo esta lista de comandos";

        argumentos = new String[3];     // El comando más largo requiere 2 argumentos
    }


    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setInformacion(String mensajeInformativo) {
        info = mensajeInformativo;
    }

    public String getNombre() {
        return nombre;
    }

    // Getters
    public String getInfo() {
        return info;
    }

    public String getComando() {
        return argumentos[0] != null ? argumentos[0] : "null";
    }

    public String getArgumento1() {
        return argumentos[1] != null ? argumentos[1] : "null";
    }

    public String getArgumento2() {
        return argumentos[2] != null ? argumentos[2] : "null";
    }


    /**
     * Simula una petición de comando y sus argumentos de una consola.
     */
    public void leer() {
        Scanner consola = new Scanner(System.in);
        boolean error = true;

        do {
            System.out.print(nombre + " > ");
            argumentos = consola.nextLine().trim().split(" ");   // Separar argumentos (split) sin saltos de linea (trim)

            if (argumentos.length <= 3) {
                error = false;

            } else {
                System.out.println(nombre + " > " + "Hay más argumentos de la cuenta: máximo 3.");
                argumentos = new String[3];
            }
        } while (error);
    }

    /**
     * Escribe un mensaje por pantalla simulando una consola.
     *
     * @param mensaje   Mensaje a mostrar.
     */
    public void escribir(String mensaje) {
        try {
            Thread.sleep(500);
            System.out.println(nombre + " > " + mensaje);
            Thread.sleep(500);

        } catch (InterruptedException e){
            System.err.println(nombre + " > error");
        }
    }

    /**
     * Escribe un mensaje en formato de error simulando una consola.
     *
     * @param error     Mensaje de error a mostrar.
     */
    public void error(String error) {
        try {
            Thread.sleep(500);
            System.err.println(nombre + " > " + error);
            Thread.sleep(500);

        } catch (InterruptedException e){
            System.err.println(nombre + " > " + error);
        }
    }
}
