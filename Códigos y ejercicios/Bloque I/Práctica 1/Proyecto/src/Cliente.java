import Paquetes.*;
import java.io.*;
import java.net.*;

public class Cliente {

    // Conexión
    private static DatagramSocket socket;
    private static InetAddress direccion;   // Dirección del servidor
    private static int puerto;              // Puerto del servidor

    // Interfaz
    private static final CMDsimple cmd = new CMDsimple();   // Simulación de consola


    public static void main(String[] args) {
        comprobarArgumentos(args);

        try {
            detectarComandos();

            // Final de la conexión
            if (direccion != null) {
                cmd.escribir("Conexión finalizada con " + direccion + ":" + puerto);
                socket.close();

            } else {
                cmd.error("No se establecio ninguna conexión");;
            }

        } catch (NullPointerException e){
            cmd.error("Argumento(s) nulo(s)");

        } catch (UnknownHostException e) {
            cmd.error("Dirección desconocida");

        } catch (IOException e) {
            cmd.error("Lectura fallida");
        }
    }


    /**
     * Comprueba si ya existen argumentos para la clase.
     *
     * @param argumentos    Argumentos iniciales (si los hay)
     */
    private static void comprobarArgumentos(String[] argumentos) {
        if(argumentos.length == 2){
            try {
                direccion = InetAddress.getByName(argumentos[0]);
                puerto = Integer.parseInt(argumentos[1]);
                socket = new DatagramSocket();

                cmd.escribir("connect " + direccion.getHostAddress() + " " + puerto + " (generado automaticamente)");

            } catch (UnknownHostException e) {
                cmd.error("Direccion desconocida");

            } catch (SocketException e) {
                cmd.error("Error durante la creacion del socket");
            }

        } else {
            cmd.escribir("No se encontraron suficientes parametros para la clase");
        }
    }

    /**
     * Activa la simulación de la consola y lee constantemente la pantalla,
     * interpretando los comandos y sus argumentos de las líneas entrantes.
     *
     * @throws UnknownHostException     Dirección del servidor desconocida
     * @throws SocketException          Problema al crear el socket
     */
    private static void detectarComandos() throws UnknownHostException, SocketException {
        boolean seguir = true;

        while (seguir) {
            cmd.leer();
            String comando = cmd.getComando();

            switch (comando.toLowerCase()) {
                case ("connect"):
                    direccion = InetAddress.getByName(cmd.getArgumento1());
                    puerto = Integer.parseInt(cmd.getArgumento2());
                    socket = new DatagramSocket(puerto, direccion);

                    cmd.escribir("Conectado a " + direccion + ":" + puerto);

                    break;

                case ("quit"):
                    cmd.escribir("Terminando conexion...");
                    seguir = false;
                    break;

                case ("?"):
                    cmd.escribir(cmd.getInfo());
                    break;

                case ("put"):
                    if (socket != null) {
                        enviar(cmd.getArgumento1());

                    } else {
                        cmd.error("Conexion aun no inicializada");
                    }

                    break;

                case ("get"):
                    if (socket != null) {
                        recibir();

                    } else {
                        cmd.error("Conexion aun no inicializada");
                    }

                    break;

                default:
                    cmd.error("Comando no registrado");
            }
        }
    }

    /**
     * Genera un paquete WRQ y lo envía al servidor si está conectado a uno.
     *
     * @param fichero   Archivo a enviar.
     */
    public static void enviar(String fichero){
        try {
            // Inicialización de variables
            FileInputStream entrada = new FileInputStream(fichero);
            boolean terminado = false;

            // Enviar petición de escritura (WRQ)
            WRQ paqueteWQR = new WRQ();
            paqueteWQR.montar();
            // paqueteWQR.fichero = fichero;          // Asignar el fichero

            // DatagramPacket paquete = new DatagramPacket(paqueteWQR.montar(), paqueteWQR.buffer.length, direccion, puerto);    // Primer buffer
            DatagramPacket paquete = new DatagramPacket(paqueteWQR.buffer, paqueteWQR.buffer.length, direccion, puerto);
            socket.send(paquete);

//            // Recibir ACK(0) del servidor.
//            socket.receive(paquete);            // Sobreescritura del contenido del buffer
//            byte[] datos = paquete.getData();   // Obtener los datos (payload) del buffer
//
//            Paquete_ACK paqueteACK = new Paquete_ACK();
//            paqueteACK.buffer = datos;
//            paqueteACK.desmontar(new byte[10]);
//
//            int ACK = paqueteACK.bloque;
//
//            // Dividir el archivo y enviarlo poco a poco.
//            while(entrada.available() > 0 || terminado){
//                byte[] leido = new byte[512];
//                int numBytes = entrada.read(leido);
//
//                try {
//                    Paquete_DATA paqueteDATA = new Paquete_DATA();
//                    paqueteDATA.setDatos(leido, numBytes);      // Datos del paquete
//                    paqueteDATA.montar();                       // Crear estructura
//
//                    paquete = new DatagramPacket(paqueteDATA.buffer, paqueteDATA.buffer.length);
//                    paquete.setData(paqueteDATA.buffer);
//
//                }catch (IOException e){
//                    cmd.error("Error al crear el paquete DATA");
//
//                    Paquete_ERROR paqueteERROR = new Paquete_ERROR();
//                    paqueteERROR.codigo = 0;
//                    paqueteERROR.mensaje = paqueteERROR.ERROR_0;
//
//                    paquete = new DatagramPacket(paqueteERROR.buffer, paqueteERROR.buffer.length);
//                    paquete.setData(paqueteERROR.buffer);
//                }
//
//                socket.send(paquete);
//
//                socket.receive(paquete);
//
//                byte[] buffer = paquete.getData();
//                Paquete_ERROR paqueteERROR = new Paquete_ERROR();
//                Paquete_ACK paquete_ACK = new Paquete_ACK();
//                paqueteERROR.buffer = buffer;
//                paquete_ACK.buffer = buffer;
//
//                if (paqueteERROR.opcode == 5){
//                    cmd.error("Paquete de ERROR recibido. Codigo " + paqueteERROR.codigo + " - " + paqueteERROR.mensaje);
//                    terminado = true;
//
//                } else if (paqueteACK.opcode == 4){
//                    cmd.escribir("(<-) ACK: " + paquete_ACK.bloque);
//
//                    if(paquete_ACK.bloque != ACK+1){
//                        cmd.error("ACK incorrecto, se espera " + ACK+1);
//                    }
//                }
//            }

        } catch (FileNotFoundException e) {
            cmd.error("No se ha encontrado el fichero");

        } catch (IOException e) {
            cmd.error("No se ha podido leer el fichero");
        }
    }

    public static void recibir(){
        // TODO - Recibir paquete
    }
}
