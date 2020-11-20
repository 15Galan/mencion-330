import Paquetes.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {

    private static final int PUERTO = 6789;
    private static final int BUFFER = 512;      // Bytes
    private static boolean fin = false;         // Variable que indica el final del servicio.

    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("SERVIDOR ACTIVO\nEsperando paquete...");

            while(!fin) {
                DatagramPacket paquete = new DatagramPacket(new byte[BUFFER], BUFFER);
                socket.receive(paquete);

                byte[] datos = paquete.getData();

                switch (leerOPCODE(datos)) {
                    case 1 -> accionRRQ(datos);
                    case 2 -> accionWRQ(datos);
                    case 3 -> accionDATA(datos);
                    case 4 -> accionACK(datos);
                    case 5 -> accionERROR(datos);
                    default -> System.err.println("El OPCODE del paquete no pertence al protocolo TFTP");
                }
            }

        } catch (SocketException e) {
            System.err.println("Error en la conexion: '" + e.getMessage() + "'");

        } catch (IOException e) {
            System.err.println("Error en la lectura de los datos");
        }
    }


    /**
     * Lee el OPCODE de un paquete para filtrar.
     *
     * @param buffer    Datos de un DatagramPacket recibido
     *
     * @return  El OPCODE del paquete
     *
     * @throws IOException  Error de lectura
     */
    public static short leerOPCODE(byte[] buffer) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
        DataInputStream in = new DataInputStream(byteStream);

        return in.readShort();
    }

    public static void accionRRQ(byte[] buffer) throws IOException {
        // TODO
    }

    public static void accionWRQ(byte[] buffer) throws IOException {
        // TODO
    }

    public static void accionDATA(byte[] buffer) throws IOException {
        // TODO
    }

    public static void accionACK(byte[] buffer) throws IOException {
        // TODO
    }

    public static void accionERROR(byte[] buffer) throws IOException {
        // TODO
    }
}
