/*
 * Práctica 2
 *
 * @author Antonio J. Galán Herrera
 */

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;

public class ClienteGUI {

    // Conexión
    private static Socket socket;
    private static String direccion;    // Dirección IP del servidor
    private static int puerto;          // Puerto del servidor


    public static void main(String[] args) {
        comprobarArgumentos(args);

        try {
            AccesoChat acceso = new AccesoChat();

            inicializarVentana(acceso);

            acceso.inicializarSocket(direccion, puerto);

            System.out.println("Conexión establecida con '" + direccion + ":" + puerto + "'.");

        } catch (IOException e) {
            System.out.println("Conexión fallida con '" + direccion + ":" + puerto + "'.");
            System.exit(1);
        }
    }


    /**
     * Comprueba si ya existen argumentos para la clase.
     *
     * @param argumentos Argumentos iniciales (si los hay)
     */
    private static void comprobarArgumentos(String[] argumentos) {
        if (argumentos.length == 2) {
            System.out.println("Se han encontrado parámetros para la clase.");

            // Asignación automática
            direccion = argumentos[0];
            puerto = Integer.parseInt(argumentos[1]);

        } else {
            System.out.println("No se encontraron parámetros para la clase.");

            // Asignación manual
            Scanner sc = new Scanner(System.in);

            System.out.print("Dirección IP: ");
            direccion = sc.nextLine();

            System.out.print("      Puerto: ");
            puerto = Integer.parseInt(sc.nextLine());
        }
    }

    /**
     * Construye una ventana para el cliente.
     */
    private static void inicializarVentana(AccesoChat acceso) {
        JFrame ventana = new Ventana(acceso);

        ventana.setTitle("Conectado a '" + direccion + ":" + puerto + "'");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();

        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);
    }


    // Clase interna para acceder al chat del cliente
    static class AccesoChat extends Observable {

        // Manejo de datos
        private static OutputStream escritor;   // Flujo de salida de datos


        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /**
         * Crea un socket y recibe una hebra.
         *
         * @param ip        Dirección IP del servidor
         * @param puerto    Puerto del servidor
         *
         * @throws IOException  Error con la hebra
         */
        public void inicializarSocket(String ip, int puerto) throws IOException {
            socket = new Socket(ip, puerto);
            escritor = socket.getOutputStream();

            // Definir la acción de una hebra: recibir mensajes de una conexión con un cliente
            Thread hebra = new Thread(() -> {
                try {
                    // Crear el flujo de entrada de datos
                    BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String mensaje;

                    // Notificar a los observadores
                    while ((mensaje = lector.readLine()) != null) {
                        notifyObservers(mensaje);
                    }

                } catch (IOException ex) {
                    notifyObservers(ex);
                }
            });

            hebra.start();
        }

        /**
         * Enviar una línea de texto.
         *
         * @param texto Línea a enviar
         */
        public void enviar(String texto) {
            try {
                escritor.write((texto + "\r\n").getBytes());
                escritor.flush();

            } catch (IOException e) {
                notifyObservers(e);
                System.err.println("Error enviando '" + texto + "'");
            }
        }

        /**
         * Cerrar el socket de la conexión.
         */
        public void cerrar() {
            try {
                socket.close();

            } catch (IOException e) {
                notifyObservers(e);
            }
        }
    }
}
