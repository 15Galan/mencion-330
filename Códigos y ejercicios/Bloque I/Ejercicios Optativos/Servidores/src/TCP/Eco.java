package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Eco {
    private static int PUERTO;

    public static void main (String[] args) {
        comprobarArgumentos(args);

        try (ServerSocket socketServidor = new ServerSocket(PUERTO)) {
            String mensaje;

            System.out.println("Servidor TCP de ECO activo sobre localhost:" + PUERTO + ".\n");

            do {
                Socket socketCliente = socketServidor.accept();

                String cliente = socketCliente.getInetAddress() + ":" + socketCliente.getLocalPort();

                System.out.println("Cliente " + cliente + " conectado.");

                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

                mensaje = entrada.readLine();
                System.out.println("\tMensaje recibido: '" + mensaje + "'");

                salida.println(mensaje);
                System.out.println("\tMensaje enviado:  '" + mensaje + "'\n");

                socketCliente.close();

            } while (mensaje != null);

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
