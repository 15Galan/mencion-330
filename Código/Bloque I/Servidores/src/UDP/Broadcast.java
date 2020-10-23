package UDP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Broadcast {

    private static int PUERTO;

    public static void main(String[] args) {
        comprobarArgumentos(args);

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("192.168.167.255");     // IP broadcast

            System.out.println("Emisor activo en " + serverAddress + ":" + PUERTO);

            // Leer de teclado
            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Mensaje a propagar: ");
                String mensaje = sc.nextLine();

                DatagramPacket paquete = new DatagramPacket(mensaje.trim().getBytes(), mensaje.trim().getBytes().length, serverAddress, PUERTO);

                socket.send(paquete);
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

            System.err.println("Parámetros inciales no encontrados.");
            System.err.println("Introducir puerto manualmente.");
            System.out.print("\nPuerto: ");

            PUERTO = Integer.parseInt(sc.nextLine());

            System.out.println("\n");
        }
    }
}
