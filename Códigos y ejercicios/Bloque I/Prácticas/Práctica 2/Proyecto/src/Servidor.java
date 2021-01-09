/*
 * Universidad de Málaga
 * E.T.S Ingeniería Informática
 *
 * Desarrollo de Servicios Telemáticos
 * Práctica 2: Chat multicliente con Selector / NIO
 *
 * Antonio J. Galán Herrera
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

public class Servidor {

    // Conexión
    private static ServerSocketChannel servidor;    // Socket para el Selector
    private static Selector selector;               // Selector del servidor
    private static int puerto;                      // Puerto del servidor

    // Datos
    private static int usuarios = 0;        // Número de usuarios conectados al servidor


    public static void main(String[] args) throws IOException {
        comprobarArgumentos(args);

        inicializarServidor();

        System.out.println("\nEsperando conexiones...");
        System.out.println("----------------------------------------------------------------");

        do {
            selector.select();

            Set<SelectionKey> canales = selector.selectedKeys();

            for(var it = canales.iterator(); it.hasNext();) {
                SelectionKey canal = it.next();
                it.remove();

                // Identificar entre 'nuevo cliente' o 'mensaje recibido'
                if(canal.isAcceptable()) {
                    aceptarCliente();

                }else if(canal.isReadable()){
                    leerCliente(canal);
                }
            }

        } while (usuarios > 0);

        System.out.println("----------------------------------------------------------------");
        System.out.println("No hay usuarios conectados, fin del servicio.");

        selector.close();

        System.out.println("\nServidor cerrado.");
    }


    /**
     * Recopila la información de un cliente y forma una cadena de texto.
     *
     * @param canal     Canal del cliente
     *
     * @return  Una cadena con la estructura 'IP:PUERTO' (sin las comillas)
     */
    private static String info(SocketChannel canal) {
        return canal.socket().getInetAddress().toString() + ":" + canal.socket().getPort();
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
            System.out.println("No se encontraron parámetros iniciales para la clase.");

            System.out.print("Asignar puerto: ");

            puerto = sc.nextInt();
        }
    }

    /**
     * Arranca el Selector e inicia el Socket, abriendo la conexión
     * del servidor a los clientes.
     *
     * @throws IOException  Hubo un problema con el Selector
     */
    private static void inicializarServidor() throws IOException {
        // Crear el Selector
        selector = Selector.open();

        // Crear los sockets para aceptar conexiones
        servidor = ServerSocketChannel.open();
        servidor.socket().bind(new InetSocketAddress(puerto));      // Enlazar el socket TCP al puerto
        servidor.configureBlocking(false);                          // Establecer lectura no bloqueante (servidor)

        // Asignar el Selector al socket y la SelectionKey de aceptación
        servidor.register(selector, SelectionKey.OP_ACCEPT);

        // Mostrar información
        System.out.println("\nServidor activo en el puerto " + puerto + ".");
        System.out.println("Ofreciendo servicio de chat grupal.");
    }

    /**
     * Recibe la conexión de un nuevo cliente y registra
     * la posibilidad de leer los datos que envíe.
     *
     * @throws IOException  Hubo un problema con el Selector
     */
    private static void aceptarCliente() throws IOException {
        SocketChannel socketCliente = servidor.accept();    // Aceptar una conexión entrante

        System.out.println("['" + info(socketCliente) + "' se ha conectado]");     // Informar de una nueva conexión
        System.out.println("Hay un total de (" + (++usuarios) + ") usuarios conectados.");

        socketCliente.configureBlocking(false);     // Establecer una lectura no bloqueante (servidor)

        socketCliente.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));  // Registrar la lectura
    }

    /**
     * Lee los datos recibidos a través de un canal y muestra por
     * pantalla información sobre los datos recibidos.
     *
     * @param canal     Canal del que leer los datos
     *
     * @throws IOException  Hubo un problema con el canal.
     */
    private static void leerCliente(SelectionKey canal) throws IOException {
        SocketChannel socketCliente = (SocketChannel) canal.channel();

        // Crear un buffer asociado al canal del cliente (vacío por defecto)
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // Escribir los bytes leídos en el buffer
        // y devolver la cantidad de bytes leídos
        int numBytes = socketCliente.read(buffer);

        // Crear mensaje sin bytes nulos (ajustado)
        String mensaje = new String(buffer.array()).trim();

        // Comprobar si la conexión con el cliente se cerró
        if (numBytes != -1) {
            System.out.println("\tEl cliente '" + info(socketCliente) + "' ha enviado \"" + mensaje + "\".");

            // Inicializar el buffer a enviar
            byte[] datos = new byte[numBytes];
            System.arraycopy(buffer.array(), 0, datos, 0, numBytes);
            canal.attach(ByteBuffer.wrap(datos));               // Asignar el nuevo buffer al canal

            // Actualizar el canal del cliente: ahora también pasa a ser
            // de escritura por si recibe un mensaje de otro cliente
            canal.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            // Reatransmitir el mensaje entre todos los canales (clientes conectados)
            broadcast(canal, datos);

        } else {
            // Cerrar la conexión con el cliente
            canal.cancel();
            socketCliente.close();

            System.out.println("['" + info(socketCliente) + "' se ha desconectado]");   // Informar de una nueva conexión
            System.out.println("Hay un total de (" + (--usuarios) + ") usuarios conectados.");
        }
    }

    /**
     * Simula la funcionalidad de retransmisión broadcast a través
     * de los canales de un Selector.
     *
     * @param canal     Canal del que extraer la información del cliente
     * @param datos     Mensaje que ha enviado el cliente
     *
     * @throws IOException  Hubo un problema con el canal
     */
    private static void broadcast(SelectionKey canal, byte[] datos) throws IOException {
        for(SelectionKey canalActual : selector.keys()) {
            // Filtrar por canales READABLEs o WRITEABLEs
            if(canalActual.isValid() && canalActual.channel() instanceof SocketChannel) {
                // Inicialización de las variables para el cliente actual del bucle
                SocketChannel cliente = (SocketChannel) canalActual.channel();
                ByteBuffer bufferCliente = (ByteBuffer) canalActual.attachment();
                bufferCliente.flip();

                // Asignar el nuevo buffer con el tamaño exacto
                bufferCliente = ByteBuffer.wrap(construirMensaje(canal, datos));

                // Enviar el mensaje
                cliente.write(bufferCliente);

                // Mostrar el mensaje con espacios para mejorar la visión en la consola
                System.out.println("\t\tEnviado a '" + info(cliente) + "'.");

                if (!bufferCliente.hasRemaining()) {            // Fin de la operacion de escritura.
                    canal.interestOps(SelectionKey.OP_READ);    // Una vez escrito, ya solo interesa leer.
                }

                bufferCliente.compact();      // Hacer sitio para leer mas datos.
            }
        }

        System.out.println(/* Separar secuencia de broadcast. */);
    }

    /**
     * Construye el mensaje que será escrito en el buffer del canal y
     * enviado al cliente. Este mensaje será impreso por el mismo en la GUI.
     *
     * @param canal     Canal del que extraer la información del cliente
     * @param datos     Mensaje que ha enviado el cliente
     *
     * @return  Una cadena con la estructura '/cliente/ > mensaje' (sin las comillas)
     */
    private static byte[] construirMensaje(SelectionKey canal, byte[] datos) {
        // Parte del mensaje que muestra el usuario que lo envía
        byte[] usuario = (info((SocketChannel) canal.channel()) + "/ > ").getBytes();

        // Crear el mensaje completo: usuario + mensaje
        byte[] mensaje = new byte[usuario.length + datos.length];           // Array con el mensaje final
        System.arraycopy(usuario, 0, mensaje, 0, usuario.length);           // Introduccion del usuario
        System.arraycopy(datos, 0, mensaje, usuario.length, datos.length);  // Introduccion de su mensaje

        return mensaje;
    }
}
