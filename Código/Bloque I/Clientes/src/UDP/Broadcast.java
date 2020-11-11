package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class Broadcast {

    private static final int BUFFER = 255;      // Longitud predeterminada del buffer de los mensajes
    public static int PUERTO;

    public static void main(String[] args) {
        comprobarArgumentos(args);

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            DatagramPacket paquete = new DatagramPacket(new byte[BUFFER], BUFFER);

            while (true) {
                paquete.setLength(BUFFER);   // Limpiar buffer

                System.out.println("Esperando mensaje...\n");

                socket.receive(paquete);     // Recibir un datagrama del EMISOR

                System.out.println("\tEmisor:  " + paquete.getAddress().getHostAddress() + ":" + paquete.getPort());
                System.out.println("\tMensaje: " + (new String(paquete.getData()).trim()) + "\n");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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

            System.out.println("Parámetros inciales no encontrados.");
            System.out.println("Introducir puerto manualmente.\n");
            System.out.print("Puerto: ");

            PUERTO = Integer.parseInt(sc.nextLine());

            System.out.println("\n");
        }
    }
}
