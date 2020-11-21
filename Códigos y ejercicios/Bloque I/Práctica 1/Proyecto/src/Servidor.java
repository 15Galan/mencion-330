import Paquetes.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Servidor {

    // Parámetros
    private static int puerto = 6789;
    private static boolean fin = false;         // Variable que indica el final del servicio

    // Datos
    private static String cliente;
    private static String fichero;
    private static String modo = "octet";

    public static void main(String[] args) {
        comprobarArgumentos(args);

        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor activo sobre el puerto " + puerto);

            while(!fin) {
                DatagramPacket paquete = new DatagramPacket(new byte[1024], 1024);
                socket.receive(paquete);

                cliente = paquete.getAddress() + ":" + paquete.getPort();

                System.out.println("Cliente conectado: " + cliente);

                leerPaquete(paquete);
            }

        } catch (SocketException e) {
            System.err.println("Error en la conexion: '" + e.getMessage() + "'");

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
            puerto = Integer.parseInt(argumentos[0]);

        } else {
            Scanner sc = new Scanner(System.in);
            System.err.println("No se encontraron suficientes parametros para la clase");

            System.out.print("Asignar puerto: ");

            try {
                puerto = sc.nextInt();

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

        try {
            switch (leerOPCODE(buffer)) {
                case 1 -> accionRRQ(buffer);
                case 2 -> accionWRQ(buffer);
                case 3 -> accionDATA(buffer);
                case 4 -> accionACK(buffer);
                case 5 -> accionERROR(buffer);
                default -> System.err.println("El OPCODE " + leerOPCODE(buffer) + " no pertence al protocolo TFTP.");
            }

        } catch (IOException e) {
            System.err.println("Algo ha salido mal.");
        }
    }

    /**
     * Lee el opcode de un paquete para identificar su estructura.
     *
     * @param buffer    Datos de un DatagramPacket recibido
     *
     * @return  El opcode del paquete
     */
    public static int leerOPCODE(byte[] buffer) {
        short opcode;

        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(buffer));
            opcode = in.readShort();

        } catch (IOException e) {
            opcode = -1;
        }

        return opcode;
    }

    public static void accionRRQ(byte[] buffer) throws IOException {
//        RRQ rrq = new RRQ(buffer);
//
//        rrq.desmontar();
//
//        fichero = rrq.fichero;
    }

    public static void accionWRQ(byte[] buffer) throws IOException {
        // Se extrae la información del paquete recibido
        WRQ paquete = new WRQ(buffer);

        paquete.desmontar();
        fichero = paquete.getFichero();
        modo = paquete.getModo();

//        System.out.println("Petición WRQ para: " + fichero);

        // Comienza el proceso de intercambio

    }

    public static void accionDATA(byte[] paquete) throws IOException {
//        Paquete_DATA data = new Paquete_DATA(paquete);
//
//        data.desmontar();
//
//        data.bloque;
    }

    public static void accionACK(byte[] buffer) throws IOException {
        // TODO
    }

    public static void accionERROR(byte[] buffer) throws IOException {
        // TODO
    }
}
