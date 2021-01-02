import Herramientas.*;
import Paquetes.*;

import java.io.*;
import java.net.*;

public class Cliente {

    // Conexión
    private static DatagramSocket socket;   // Socket con el servidor
    private static InetAddress direccion;   // Dirección del servidor
    private static int puerto;              // Puerto del servidor

    // Interfaz
    private static final CMDsimple cmd = new CMDsimple();   // Simulación de consola

    // Paquetes
    private final static int RECEPCION_MAX = 1024;
    private static String modo = "octet";           // Modo por defecto de lectura/escritura de paquetes WRQ y RRQ


    public static void main(String[] args) {
        cmd.escribir(cmd.getInfo());

        comprobarArgumentos(args);

        try {
            detectarComandos();

        } catch (NullPointerException e){
            cmd.error("Argumento(s) nulo(s)");

        } catch (UnknownHostException e) {
            cmd.error("Dirección desconocida");

        } catch (IOException e) {
            cmd.error("Lectura fallida");
        }
    }


    /**
     * Comprueba si ya existen argumentos para la clase.
     *
     * @param argumentos    Argumentos iniciales (si los hay)
     */
    private static void comprobarArgumentos(String[] argumentos) {
        if(argumentos.length == 2){
            try {
                direccion = InetAddress.getByName(argumentos[0]);
                puerto = Integer.parseInt(argumentos[1]);
                socket = new DatagramSocket();

                cmd.escribir("connect " + direccion.getHostAddress() + " " + puerto + " (generado automaticamente)");

            } catch (UnknownHostException e) {
                cmd.error("Direccion desconocida");

            } catch (SocketException e) {
                cmd.error("Error durante la creacion del socket");
            }

        } else {
            cmd.escribir("No se encontraron parametros iniciales para la clase");
        }
    }

    /**
     * Activa la simulación de la consola y lee constantemente la pantalla,
     * interpretando los comandos y sus argumentos de las líneas entrantes.
     *
     * @throws UnknownHostException     Dirección del servidor desconocida
     * @throws SocketException          Problema al crear el socket
     */
    private static void detectarComandos() throws UnknownHostException, SocketException {
        boolean seguir = true;

        while (seguir) {
            cmd.leer();

            String comando = cmd.getComando();

            switch (comando.toLowerCase()) {
                case ("connect"):
                    if (!cmd.soloComando()) {
                        direccion = InetAddress.getByName(cmd.getArgumento1());
                        puerto = Integer.parseInt(cmd.getArgumento2());
                        socket = new DatagramSocket();

                    } else if (socket != null) {
                        cmd.escribir("Estás conectado a '" + direccion + ":" + puerto + "'");

                    } else {
                        cmd.escribir("No estás conectado a ningún servidor");
                    }

                    break;

                case ("mode"):
                    if (!cmd.soloComando()) {
                        if (modo.equals(cmd.getArgumento1().toLowerCase())) {
                            cmd.escribir("Ese es el modo actual");

                        } else {
                            modo = cmd.getArgumento1();
                            cmd.escribir("Modo cambiado");
                        }

                    } else {
                        cmd.escribir("Estás trabajando en modo '" + modo + "'");
                    }

                    break;

                case ("put"):
                    if (socket != null) {
                        enviar(new File(cmd.getArgumento1()));

                    } else {
                        cmd.error("Conexión aun no inicializada.");
                    }

                    break;

                case ("get"):
                    if (socket != null) {
                        recibir(new File(cmd.getArgumento1()));

                    } else {
                        cmd.error("Conexión aun no inicializada.");
                    }

                    break;

                case ("quit"):
                    if (socket != null) {
                        cmd.escribir("Conexión con " + direccion + ":" + puerto + " finalizada.");
                        socket.close();
                    }

                    cmd.escribir("Cliente TFTP cerrado.");

                    seguir = false;

                    break;

                case ("?"):
                    cmd.escribir(cmd.getInfo());
                    break;

                default:
                    cmd.error("Comando no registrado");
            }
        }
    }

    /**
     * Genera un paquete RRQ y lo envía al servidor si está conectado a uno.
     *
     * @param archivo   Archivo a enviar.
     */
    public static void recibir(File archivo) {
        byte[] contenido;     // Contenido del fichero que se recibe

        try {
            // Crear y enviar la petición RRQ
            RRQ peticion = new RRQ(archivo.getName(), "octet");
            socket.send(new DatagramPacket(peticion.buffer, peticion.buffer.length, direccion, puerto));

            cmd.escribir("RRQ '" + archivo.getName() + "'  -------->");

            // Cruce de DATAs y ACKs
            contenido = intercambioRRQ();

            // Se crea el fichero y se escribe
            if (Funciones.escribirFichero(contenido, new File("src/Archivos/Cliente/" + archivo.getName()))) {
                cmd.escribir("Fichero recibido correctamente.");

            } else {
                cmd.error("Fallo al recibir el fichero.");
            }

        } catch (FileNotFoundException e) {
            cmd.error("No se ha encontrado el fichero");

        } catch (IOException e) {
            cmd.error("No se ha podido leer el fichero");
        }
    }

    /**
     * Genera un paquete WRQ y lo envía al servidor si está conectado a uno.
     *
     * @param archivo   Archivo a enviar.
     */
    public static void enviar(File archivo) {
        try {

            // Enviar petición de escritura (WRQ)
            WRQ peticion = new WRQ(archivo.getName(), modo);

            DatagramPacket paquete = new DatagramPacket(peticion.buffer, peticion.buffer.length, direccion, puerto);
            socket.send(paquete);

            cmd.escribir("WRQ '" + peticion.getModo() + "'  -------->");

            // Cruce de ACKs y DATAs
            intercambioWRQ(archivo);

        } catch (FileNotFoundException e) {
            cmd.error("No se ha encontrado el fichero");

        } catch (IOException e) {
            cmd.error("No se ha podido leer el fichero");
        }
    }

    /**
     * Ejecuta el intercambio de paquetes ACKs y DATAs entre el cliente y el servidor.
     *
     * @param archivo   Fichero que enviar
     */
    private static void intercambioWRQ(File archivo) {
        try (FileInputStream lector = new FileInputStream(archivo)) {
            do {
                // Recibir ACK del servidor
                DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
                socket.receive(paquete);

                ACK confirmacion = new ACK(paquete.getData());

                cmd.escribir("<----------------  ACK " + confirmacion.getBloque());

                // Crear un DATA para el trozo
                DATA datos = new DATA(Funciones.crearParticion(lector), confirmacion.getBloque() + 1);

                // Enviar el DATA
                paquete = new DatagramPacket(datos.buffer, datos.buffer.length, direccion, puerto);
                socket.send(paquete);

                cmd.escribir("DATA " + datos.getBloque() + " (" + datos.getDatos().length + ")  -------->");

            } while (lector.available() > 0);

            // Recibir el último ACK del servidor
            DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
            socket.receive(paquete);

            ACK confirmacion = new ACK(paquete.getData());

            cmd.escribir("<----------------  ACK " + confirmacion.getBloque());
            cmd.escribir("Fichero enviado sin errores");

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta el intercambio de paquetes DATAs y ACKs entre el cliente y el servidor.
     */
    private static byte[] intercambioRRQ() throws IOException {
        byte[] contenido = new byte[0];
        boolean terminar = false;

        do {
            // Recibir DATA
            DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
            socket.receive(paquete);

            // Guardar los datos
            DATA datos = new DATA(paquete.getData());

            cmd.escribir("<----------------  DATA " + datos.getBloque() + " (" + datos.getDatos().length + ")");

            // Añadir la partición al contenido del fichero
            contenido = Funciones.agregar(contenido, datos.getDatos());

            if (datos.getDatos().length < TFTP.LONGITUD_MAX) {
                terminar = true;
            }

            // Crear y enviar el ACK (n)
            ACK confirmacion = new ACK(datos.getBloque());
            socket.send(new DatagramPacket(confirmacion.buffer, confirmacion.buffer.length, direccion, puerto));

            cmd.escribir("ACK " + confirmacion.getBloque() + "  -------->");

        } while (!terminar);

        return contenido;
    }
}
