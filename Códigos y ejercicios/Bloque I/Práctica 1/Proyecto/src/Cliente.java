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
    private static byte[] contenido;                // Fichero en el caso de RRQ

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
                        cmd.error("Conexion aun no inicializada");
                    }

                    break;

                case ("get"):
                    if (socket != null) {
                        recibir(new File(cmd.getArgumento1()));

                    } else {
                        cmd.error("Conexion aun no inicializada");
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
     * Genera un paquete WRQ y lo envía al servidor si está conectado a uno.
     *
     * @param archivo   Archivo a enviar.
     */
    public static void enviar(File archivo) {
        try (FileInputStream lector = new FileInputStream(archivo)) {

            // Enviar petición de escritura (WRQ)
            WRQ escritura = new WRQ(archivo.getName(), modo);
            escritura.montar();

            DatagramPacket paquete = new DatagramPacket(escritura.buffer, escritura.buffer.length, direccion, puerto);
            socket.send(paquete);

            cmd.escribir("WRQ '" + escritura.getModo() + "'  -------->");

            // Cruce de ACKs y DATAs
            intercambioWRQ(lector);

        } catch (FileNotFoundException e) {
            cmd.error("No se ha encontrado el fichero");

        } catch (IOException e) {
            cmd.error("No se ha podido leer el fichero");
        }
    }

    /**
     * Ejecuta el intercambio de paquetes ACKs y DATAs entre el cliente y el servidor.
     *
     * @param lector   stream de lectura del fichero
     */
    private static void intercambioWRQ(FileInputStream lector) {
        try {
            do {
                // Recibir ACK del servidor
                DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
                socket.receive(paquete);

                ACK confirmacion = new ACK(paquete.getData());
                confirmacion.desmontar();

                cmd.escribir("<----------------  ACK " + confirmacion.getBloque());

                // Crear un DATA para el trozo
                DATA datos = new DATA();
                datos.setDatos(Funciones.crearParticion(lector));
                datos.setBloque(confirmacion.getBloque() + 1);
                datos.montar();

                // Enviar el DATA
                paquete = new DatagramPacket(datos.buffer, datos.buffer.length, direccion, puerto);
                socket.send(paquete);

                cmd.escribir("DATA " + datos.getBloque() + " (" + datos.getDatos().length + ")  -------->");

            } while (lector.available() > 0);

            // Recibir el último ACK del servidor
            DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
            socket.receive(paquete);

            ACK confirmacion = new ACK(paquete.getData());
            confirmacion.desmontar();

            cmd.escribir("<----------------  ACK " + confirmacion.getBloque());
            cmd.escribir("Fichero enviado sin errores");

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void recibir(File archivo) {
        contenido = new byte[0];

        try {
            // Se crea la petición RRQ
            RRQ paquete = new RRQ(archivo.getName(), "octet");
            paquete.montar();

            // Se envía la petición RRQ
            socket.send(new DatagramPacket(paquete.buffer, paquete.buffer.length, direccion, puerto));

            intercambioRRQ();

            // Se crea el fichero
            File fichero = new File("src/Archivos/Cliente/" + archivo.getName());
            fichero.createNewFile();

            // Se escribe el fichero
            FileOutputStream out = new FileOutputStream(fichero.getAbsolutePath());

            out.write(contenido);
            out.close();

            System.out.println("Fichero escrito.");

        } catch (FileNotFoundException e) {
            cmd.error("No se ha encontrado el fichero");

        } catch (IOException e) {
            cmd.error("No se ha podido leer el fichero");
        }
    }

    private static void intercambioRRQ() throws IOException {
        boolean terminar = false;

        do {
            // Recibir DATA
            DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
            socket.receive(paquete);

            // Guardar datos
            DATA datos = new DATA(paquete.getData());
            datos.desmontar();

            System.out.println("\tRecibido DATA " + datos.getBloque() + " (" + datos.getDatos().length + ")");

            contenido = Funciones.agregar(contenido, datos.getDatos());

            if (datos.getDatos().length < TFTP.LONGITUD_MAX) {
                terminar = true;
            }

            // Crear el ACK (n)
            ACK confirmacion = new ACK();
            confirmacion.setBloque(datos.getBloque());
            confirmacion.montar();

            // Enviar el ACK
            socket.send(new DatagramPacket(confirmacion.buffer, confirmacion.buffer.length, direccion, puerto));

        } while (!terminar);
    }
}
