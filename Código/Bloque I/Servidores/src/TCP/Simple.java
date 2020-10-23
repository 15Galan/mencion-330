package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Simple {
    private static int PUERTO;

    public static void main (String[] args) {
        comprobarArgumentos(args);

        try (ServerSocket socketServidor = new ServerSocket(PUERTO)) {
            boolean activo = true;
            String mensaje = "";

            System.out.println("Servidor TCP de ECO activo sobre localhost:" + PUERTO + ".\n");

            do {
                Socket socketCliente = socketServidor.accept();

                String cliente = socketCliente.getInetAddress() + ":" + socketCliente.getLocalPort();

                System.out.println("Cliente " + cliente + " conectado.");

                // Flujos de entrada y de salida de mensajes
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

                while (!mensaje.equals("FIN")) {
                    // Recibir mensaje
                    mensaje = entrada.readLine();
                    System.out.println("\tMensaje recibido: '" + mensaje + "'");

                    // Comprobar si es "." para finalizar
                    if (mensaje != null) {
                        Scanner sc = new Scanner(System.in);
                        System.out.print("\tRespuesta:         ");
                        mensaje = sc.nextLine();

                        // Si yo envío "FIN" se cierra el servidor
                        if (mensaje.equals("FIN")) {
                            activo = false;
                        }

                        System.out.println("'\n");

                        // Enviar mensaje
                        salida.println(mensaje);

                        if (!activo) {
                            socketCliente.close();
                        }

                    } else {
                        socketCliente.close();
                    }
                }

                socketCliente.close();

                System.out.println("Cliente " + cliente + " desconectado.\n");

            } while (activo);

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
