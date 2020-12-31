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
    private static int puerto_local = 6789;

    // Datos
    private static byte[] contenido;

    public static void main(String[] args) {
        comprobarArgumentos(args);

        try {
            socket = new DatagramSocket(puerto_local);

            System.out.println("Servidor activo sobre el puerto " + puerto_local);

            // TODO - Indicar una condición mejor de fin de servicio

            while(true) {
                System.out.println("Esperando conexión...");

                DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX);
                socket.receive(paquete);

                direccion = paquete.getAddress();
                puerto = paquete.getPort();

                System.out.println("Cliente conectado: " + direccion + ":" + puerto);

                contenido = new byte[0];    // Se inicia (o reinicia) antes de cada archivo

                leerPaquete(paquete);
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
            System.err.println("No se encontraron suficientes parametros para la clase");

            try {
                do {
                    System.out.print("Asignar puerto: ");

                    puerto_local = sc.nextInt();

                    if (puerto_local == 69) {
                        System.out.println("No puede usarse ese puerto.");
                    }

                } while (puerto_local == 69);

            } catch (InputMismatchException e) {
                System.out.println("Error, puerto por defecto asignado.");
            }
        }
    }

    /**
     * Identifica el tipo de paquete recibido y ejecuta una
     * acción en base al mismo.
     *
     * @param paquete   Paquete a identificar
     */
    private static void leerPaquete(DatagramPacket paquete) {
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

    public static void accionRRQ(byte[] buffer) throws IOException {
        System.out.println("Petición RRQ recibida.");

        // Se extrae la información del paquete recibido
        RRQ rrq = new RRQ(buffer);
        rrq.desmontar();

        File fichero = new File("src/Archivos/Servidor/" + rrq.getFichero());

        System.out.println("Se quiere leer el fichero: " + fichero.getName() + ".");

        // Se comprueba que exista el archivo
        if (fichero.exists()) {
            System.out.println("Archivo encontrado.");

            intercambioRRQ(fichero);

        } else {
            System.out.println("El fichero no existe.");
        }

    }

    public static void accionWRQ(byte[] buffer) throws IOException {
        System.out.println("Petición WRQ recibida.");

        // Se extrae la información del paquete recibido
        WRQ paquete = new WRQ(buffer);
        paquete.desmontar();

        // Se inicializa el fichero de destino
        File fichero = new File("src/Archivos/Servidor/" + paquete.getFichero());
        fichero.createNewFile();

        // Cruces de ACKs y DATAs
        intercambioWRQ();

        // Se escribe el fichero
        FileOutputStream out = new FileOutputStream(fichero.getAbsolutePath());

        out.write(contenido);
        out.close();

        System.out.println("Fichero escrito.");
    }

    private static void intercambioRRQ(File fichero) throws IOException {
        boolean terminar = false;

        FileInputStream lector = new FileInputStream(fichero);

        // Crear el DATA (1)
        DATA data = new DATA();
        data.setDatos(Funciones.crearParticion(lector));
        data.montar();

        // Enviar el DATA (1)
        socket.send(new DatagramPacket(data.buffer, data.buffer.length, direccion, puerto));

        do {
            // Recibir ACK
            DatagramPacket paquete = new DatagramPacket(new byte[RECEPCION_MAX], RECEPCION_MAX, direccion, puerto);
            socket.receive(paquete);

            // Guardar datos
            ACK confirmacion = new ACK(paquete.getData());
            confirmacion.desmontar();

            System.out.println("\tRecibido ACK " + confirmacion.getBloque());

            if (data.getDatos().length == TFTP.LONGITUD_MAX) {
                // Actualizar el DATA
                data.setDatos(Funciones.crearParticion(lector));
                data.actualizar();
                data.montar();

                // Enviar el DATA
                socket.send(new DatagramPacket(data.buffer, data.buffer.length, direccion, puerto));

            } else if (data.getBloque() == confirmacion.getBloque()) {
                terminar = true;
            }

        } while (!terminar);

        System.out.println("Fichero enviado.");
    }

    /**
     * Realiza un intercambio de paquetes DATAs y ACKs.
     *
     * @throws IOException      Un paquete no se ha leído correctamente
     */
    private static void intercambioWRQ() throws IOException {
        boolean terminar = false;

        // Crear el ACK (0)
        ACK confirmacion = new ACK();
        confirmacion.montar();

        // Enviar el ACK (0)
        socket.send(new DatagramPacket(confirmacion.buffer, confirmacion.buffer.length, direccion, puerto));

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

            // Actualizar el ACK
            confirmacion.actualizar();
            confirmacion.montar();

            // Enviar el ACK
            socket.send(new DatagramPacket(confirmacion.buffer, confirmacion.buffer.length, direccion, puerto));

        } while (!terminar);
    }
}
