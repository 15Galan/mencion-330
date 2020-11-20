package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

public class Eco {
    private static final int BUFFER = 255;
    private static int PUERTO;

    public static void main (String[] args) {
        comprobarArgumentos(args);

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            String mensaje;

            System.out.println("Servidor UDP de ECO activo sobre localhost:" + PUERTO + ".\n");

            do {
                DatagramPacket paquete = new DatagramPacket(new byte[BUFFER], BUFFER);

                socket.receive(paquete);

                String cliente = paquete.getAddress() + ":" + paquete.getPort();

                mensaje = new String(paquete.getData()).trim();

                System.out.println("(<-) Paquete recibido:");
                System.out.println("\tOrigen:  " + cliente);
                System.out.println("\tMensaje: '" + mensaje + "'\n");

                socket.send(paquete);
                System.out.println("(->) Paquete enviado:");
                System.out.println("\tDestino: " + cliente);
                System.out.println("\tMensaje: '" + mensaje + "'\n");

                paquete.setLength(BUFFER);

            } while (!mensaje.equals("."));

        } catch (SocketException e) {
            System.err.println("Error en el socket: '" + e.getMessage() + "'.");

        } catch (IOException e) {
            System.err.println("Error de tipo I/O: '" + e.getMessage() + "'.");
        }

        System.out.println("Servidor cerrado.");
    }

    /**
     * Verifica si el programa se ha ejecutado con el puerto como
     * parámetro y, en caso contrario, pide un puerto al usuario.
     *
     * @param args  Argumentos de la clase.
     */
    private static void comprobarArgumentos(String[] args) {
        if (args.length == 1) {
            System.out.println("Parámetro inicial encontrado: " + args[0] + ".\n");
            PUERTO = Integer.parseInt(args[0]);

        } else {
            Scanner sc = new Scanner(System.in);

            System.err.println("Parámetros inciales no encontrados.");
            System.err.println("Introducir puerto manualmente.");
            System.out.print("\nPuerto: ");

            PUERTO = Integer.parseInt(sc.nextLine());

            System.out.println("\n");
        }
    }
}
