import Paquetes.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

            System.out.print("Asignar puerto: ");

            try {
                puerto_local = sc.nextInt();

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
        Scanner sc = new Scanner(System.in);
        String ruta;

        // Se guarda la ruta donde escribir el fichero
        System.out.println("Petición WRQ recibida.");
        System.out.print("Guardar el fichero en la ruta: ");
        ruta = sc.nextLine();

        // Se extrae la información del paquete recibido
        WRQ paquete = new WRQ(buffer);
        paquete.desmontar();

        // Cruces de ACKs y DATAs
        intercambio();

        // Se escribe el fichero
        FileOutputStream out = new FileOutputStream(ruta);

        out.write(contenido);
        out.close();

        System.out.println("Fichero escrito.");
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

    /**
     * Realiza un intercambio de paquetes DATAs y ACKs.
     *
     * @throws IOException      Un paquete no se ha leído correctamente
     */
    private static void intercambio() throws IOException {
        // Inicializar los paquetes DATA (recibido) y ACK (enviado)
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

            contenido = agregar(datos.getDatos());

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

    /**
     * Concatena los datos recibidos en un paquete DATA con los datos
     * recibidos anteriormente.
     *
     * @param particion     Partición de fichero recibida
     *
     * @return      Array con los datos anteriores y actuales
     */
    private static byte[] agregar(byte[] particion) {
        byte[] union = new byte[contenido.length + particion.length];

        System.arraycopy(contenido, 0, union, 0, contenido.length);
        System.arraycopy(particion, 0, union, contenido.length, particion.length);

        return union;
    }
}
