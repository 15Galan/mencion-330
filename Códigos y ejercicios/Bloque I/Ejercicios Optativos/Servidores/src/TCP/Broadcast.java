package TCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

public class Broadcast {
    private static final int BUFFER = 255;
    private static int PUERTO;

    public static void main (String[] args) {
        comprobarArgumentos(args);

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            String servidor = "localhost:" + PUERTO;
            boolean activo = true;

            System.out.println("Servidor Simple activo sobre " + servidor + ".\n");

            socket.setBroadcast(true);  // Activado por defecto

            do {
                DatagramPacket paquete = new DatagramPacket(new byte[BUFFER], BUFFER);

                // Recibir el paquete
                socket.receive(paquete);

                String cliente = paquete.getAddress() + ":" + paquete.getPort();
                String mensaje = new String(paquete.getData()).trim();

                // Mostrar datos de recepción
                System.out.println("(<-) Paquete recibido:");
                System.out.println("\tOrigen:  " + cliente);
                System.out.println("\tMensaje: '" + mensaje + "'\n");

                // Comprobar si es "." para finalizar
                if (!mensaje.equals(".")) {
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Respuesta: ");
                    mensaje = sc.nextLine();

                    paquete.setData(mensaje.getBytes());

                } else {
                    activo = false;
                }

                // Enviar nuevo paquete (reenviar el recibido si es ".")
                socket.send(paquete);

                // Mostrar datos de envío
                System.out.println("(->) Paquete enviado:");
                System.out.println("\tDestino: " + cliente);
                System.out.println("\tMensaje: '" + mensaje + "'\n");

                // paquete.setLength(BUFFER);

            } while (activo);

        } catch (SocketException e) {
            System.err.println("Error en el socket: '" + e.getMessage() + "'.");

        } catch (IOException e) {
            System.err.println("Error de tipo I/O: '" + e.getMessage() + "'.");
        }

        System.out.println("Servidor Simple cerrado.");
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
