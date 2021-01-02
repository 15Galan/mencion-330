/**
 * Práctica 2
 *
 * @author Antonio J. Galán Herrera
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Multicliente {

    private static Selector selector;               // Selector de este servidor.
    private static ServerSocketChannel servidor;    // Socket de este servidor.

    public static void main(String[] args) throws IOException {
        int PUERTO = 6789;

        inicializar(PUERTO);

        while(true){
            System.out.println("\nEsperando conexiones...");

            selector.select();
            Set canales = selector.selectedKeys();

            for(Iterator it = canales.iterator(); it.hasNext(); ){
                SelectionKey canal = (SelectionKey) it.next();
                it.remove();

                if(canal.isAcceptable()) {
                    aceptar();

                }else if(canal.isReadable()){
                    lectura(canal);
                }
            }
        }
    }

    private static String info(SocketChannel canal) {
        return canal.socket().getInetAddress().toString() + ":" + canal.socket().getPort();
    }

    private static void inicializar(int puerto) throws IOException {
        // Creacion del Selector.
        selector = Selector.open();

        // Creacion de los sockets para aceptar conexiones.
        servidor = ServerSocketChannel.open();
        servidor.socket().bind(new InetSocketAddress(puerto));      // Enlazar el socket TCP al PUERTO.
        servidor.configureBlocking(false);                          // Establecer lectura no bloqueante.

        // Asignar el Selector al socket y el tipo de SelectionKey (en funcion del proposito del canal).
        servidor.register(selector, SelectionKey.OP_ACCEPT);

        // Mostrar informacion.
        System.out.println("SERVIDOR INICIADO");
        System.out.println("Servicios activos:");
        System.out.println("ECO (TCP):\t" + puerto);
    }

    private static void aceptar() throws IOException {
        System.out.print("[ACCEPT] ");
        SocketChannel socketCliente = servidor.accept();
        socketCliente.configureBlocking(false);

        System.out.println("Se ha conectado: " + info(socketCliente));
        socketCliente.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    }

    public static void lectura(SelectionKey canal) throws IOException {
        System.out.print("[READABLE] ");
        SocketChannel socketCliente = (SocketChannel) canal.channel();

        // Buffer asociado al canal del cliente (por defecto vacio).
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // Devuelve la cantidad de bytes leidos.
        int numBytes = socketCliente.read(buffer);      // Escribe los bytes leidos en 'buffer'.

        // Mensaje sin saltos de linea.
        String mensaje = new String(buffer.array()).trim();

        // Comprobar si la conexion con el cliente se cerro.
        if (numBytes != -1) {
            System.out.println(info(socketCliente) + " ha enviado '" + mensaje + "'");

            // Procesar el mensaje de entrada.
            byte[] datos = new byte[numBytes];
            System.arraycopy(buffer.array(), 0, datos, 0, numBytes);    // Rellenar el nuevo buffer.
            canal.attach(ByteBuffer.wrap(datos));   // Asignar el nuevo buffer al canal.

            // El canal ahora tambien pasa a ser de escritura por si recibe un mensaje de otro cliente.
            canal.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            // Retransmision del mensaje entre todos los clientes conectados.
            broadcast(canal, datos);

        } else {
            canal.cancel();
            socketCliente.close();

            System.out.println("Se ha desconectado: " + info(socketCliente));
        }
    }

    private static void broadcast(SelectionKey canal, byte[] datos) throws IOException {
        String tab = "           ";
        System.out.println(tab + "[BROADCAST] Difundiendo...");

        byte[] usuario = (info((SocketChannel) canal.channel()) + " -> ").getBytes();

        for(SelectionKey canalBUCLE : selector.keys()) {
            // Filtrar por canales READABLEs o WRITEABLEs.
            if(canalBUCLE.isValid() && canalBUCLE.channel() instanceof SocketChannel) {
                // Inicializacion de las variables para el cliente del bucle.
                SocketChannel cliente = (SocketChannel) canalBUCLE.channel();
                ByteBuffer bufferCliente = (ByteBuffer) canalBUCLE.attachment();
                bufferCliente.flip();

                // Procesar el mensaje que se envia: "<cliente> -> <mensaje>".
                byte[] cYm = new byte[usuario.length + datos.length];                   // Array con el mensaje "final".
                System.arraycopy(usuario, 0, cYm, 0, usuario.length);   // Introduccion del usuario.
                System.arraycopy(datos, 0, cYm, usuario.length, datos.length);  // Introduccion de su mensaje.

                bufferCliente = ByteBuffer.wrap(cYm);   // Asignar el nuevo buffer con el tamaño exacto.

                cliente.write(bufferCliente);       // Enviar el mensaje con la estructura "<cliente> -> <mensaje>".

                // Mensaje con espacios 'tab' para mejorar su vision en la consola.
                System.out.println(" " + tab + tab + info(cliente) + " lo ha recibido");

                if (!bufferCliente.hasRemaining()) {            // Fin de la operacion de escritura.
                    canal.interestOps(SelectionKey.OP_READ);    // Una vez escrito, ya solo interesa leer.
                }

                bufferCliente.compact();      // Hacer sitio para leer mas datos.
            }
        }
    }
}
