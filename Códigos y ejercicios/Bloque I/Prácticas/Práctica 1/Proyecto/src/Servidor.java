import Herramientas.Funciones;
import Paquetes.*;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Servidor {

    // Conexión
    private static DatagramSocket socket;
    private static InetAddress direccion;
    private static int puerto;

    // Parámetros
    private final static int RECEPCION_MAX = 1024;
    private static int puerto_local = 5555;


    public static void main(String[] args) {
        comprobarArgumentos(args);

        try {
            socket = new DatagramSocket(puerto_local);

            System.out.println("Servidor activo sobre el puerto " + puerto_local + ".");

            // TODO - Indicar una condición mejor de fin de servicio

            while(true) {
                System.out.println("Esperando transacción...");

                // Petición de transacción
                DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX);
                socket.receive(paquete);

                // Datos del cliente
                direccion = paquete.getAddress();
                puerto = paquete.getPort();

                System.out.println("Cliente conectado: " + direccion + ":" + puerto);

                recibirPeticion(paquete);
            }

//            socket.close();

        } catch (SocketException e) {
            System.err.println("Error en la conexión: '" + e.getMessage() + "'");

        } catch (IOException e) {
            System.err.println("Error en la lectura de los datos");
        }
    }


    /**
     * Comprueba si ya existen argumentos para la clase.
     *
     * @param argumentos    Argumentos iniciales (si los hay)
     */
    private static void comprobarArgumentos(String[] argumentos) {
        if(argumentos.length == 1){
            puerto_local = Integer.parseInt(argumentos[0]);

        } else {
            Scanner sc = new Scanner(System.in);
            System.err.println("No se encontraron suficientes parámetros para la clase");

            try {
                do {
                    System.out.print("Asignar puerto: ");

                    puerto_local = sc.nextInt();

                    if (puerto_local == 69) {
                        System.out.println("No puede usarse ese puerto.");
                    }

                } while (puerto_local == 69);

            } catch (InputMismatchException e) {
                System.out.println("Ha ocurrido un error; puerto por defecto asignado.");
            }
        }
    }

    /**
     * Identifica el tipo de paquete recibido y ejecuta una
     * acción en base al mismo.
     *
     * @param paquete   Paquete a identificar
     */
    private static void recibirPeticion(DatagramPacket paquete) {
        byte[] buffer = paquete.getData();
        int opcode = Funciones.leerOPCODE(buffer);

        try {
            switch (opcode) {
                case 1 -> accionRRQ(buffer);
                case 2 -> accionWRQ(buffer);
                default -> System.err.println("Error al leer el OPCODE '" + opcode + "'.");
            }

        } catch (IOException e) {
            System.err.println("Algo ha salido mal.");
        }
    }

    /**
     * Realiza una transacción RRQ.
     *
     * @param buffer    Paquete RRQ recibido
     *
     * @throws IOException  La transacción ha fallado
     */
    public static void accionRRQ(byte[] buffer) throws IOException {
        System.out.println("Petición RRQ recibida.");

        // Extraer la información del paquete recibido
        RRQ rrq = new RRQ(buffer);

        File fichero = new File("src/Archivos/Servidor/" + rrq.getFichero());

        System.out.println("Se quiere leer el fichero: " + fichero.getName() + ".");

        // Comprobar que existe el archivo
        if (fichero.exists()) {
            System.out.println("Archivo encontrado.");

            intercambioRRQ(fichero);

        } else {
            System.out.println("El fichero no existe.");
        }

    }

    /**
     * Realiza una transacción WRQ.
     *
     * @param buffer    Paquete WRQ recibido
     *
     * @throws IOException  La transacción ha fallado
     */
    public static void accionWRQ(byte[] buffer) throws IOException {
        System.out.println("Petición WRQ recibida.");

        byte[] contenido;     // Contenido del fichero que se recibe

        // Se extrae la información del paquete recibido
        WRQ peticion = new WRQ(buffer);

        // Cruces de ACKs y DATAs
        contenido = intercambioWRQ();

        // Se escribe el fichero
        if (Funciones.escribirFichero(contenido, new File("src/Archivos/Servidor/" + peticion.getFichero()))) {
            System.out.println("\tFichero enviado.");

        } else {
            System.out.println("\tFallo al enviar el fichero.");
        }
    }

    /**
     * Realiza un intercambio de paquetes DATAs y ACKs.
     *
     * @throws IOException      Un paquete no se ha leído correctamente
     */
    private static void intercambioRRQ(File fichero) throws IOException {
        boolean terminar = false;

        // Crear flujo de escritura para el fichero
        FileInputStream lector = new FileInputStream(fichero);

        // Crear y enviar el DATA (1)
        DATA datos = new DATA(Funciones.crearParticion(lector), 1);
        socket.send(new DatagramPacket(datos.buffer, datos.buffer.length, direccion, puerto));

        System.out.println("\tEnviado DATA " + datos.getBloque() + " (" + datos.getDatos().length + ")");

        do {
            // Recibir ACK (n)
            DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
            socket.receive(paquete);

            // Guardar datos del ACK (n)
            ACK confirmacion = new ACK(paquete.getData());

            System.out.println("\tRecibido ACK " + confirmacion.getBloque());

            if (datos.getDatos().length == TFTP.LONGITUD_MAX) {
                // Crear (actualizar) y enviar el DATA (n)
                datos.setDatos(Funciones.crearParticion(lector));
                datos.setBloque(confirmacion.getBloque() + 1);
                datos.montar();

                socket.send(new DatagramPacket(datos.buffer, datos.buffer.length, direccion, puerto));

            } else if (datos.getBloque() == confirmacion.getBloque()) {
                terminar = true;
            }

        } while (!terminar);

        System.out.println("Fichero enviado.");
    }

    /**
     * Realiza un intercambio de paquetes ACKs y DATAs.
     *
     * @throws IOException      Un paquete no se ha leído correctamente
     */
    private static byte[] intercambioWRQ() throws IOException {
        byte[] contenido = new byte[0];
        boolean terminar = false;

        // Crear y enviar el ACK (0)
        ACK inicial = new ACK();
        socket.send(new DatagramPacket(inicial.buffer, inicial.buffer.length, direccion, puerto));

        do {
            // Recibir DATA (n)
            DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
            socket.receive(paquete);

            // Guardar los datos
            DATA datos = new DATA(paquete.getData());

            System.out.println("\tRecibido DATA " + datos.getBloque() + " (" + datos.getDatos().length + ")");

            // Añadir la partición al contenido del fichero
            contenido = Funciones.agregar(contenido, datos.getDatos());

            if (datos.getDatos().length < TFTP.LONGITUD_MAX) {
                terminar = true;
            }

            // Crear y enviar el ACK (n)
            ACK confirmacion = new ACK(datos.getBloque());
            socket.send(new DatagramPacket(confirmacion.buffer, confirmacion.buffer.length, direccion, puerto));

        } while (!terminar);

        return contenido;
    }
}
