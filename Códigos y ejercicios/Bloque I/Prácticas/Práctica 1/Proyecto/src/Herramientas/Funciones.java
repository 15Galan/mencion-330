package Herramientas;

import Paquetes.TFTP;

import java.io.*;

public class Funciones {

    /**
     * Lee el opcode de un paquete para identificar su estructura.
     *
     * @param buffer    Datos de un DatagramPacket recibido
     *
     * @return  El opcode del paquete
     */
    public static int leerOPCODE(byte[] buffer) {
        short opcode;

        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(buffer));
            opcode = in.readShort();

        } catch (IOException e) {
            opcode = -1;
        }

        return opcode;
    }

    /**
     * Lee 512 bytes (o menos) de un flujo de datos y los almacena en un array
     * con la dimensión de los datos leídos, creando una partición de los datos.
     * Si se leen 0 bytes (partición múltiplo de 512) crea una partición vacía.
     *
     * @param lector    Flujo de salida de datos del que crear la partición
     *
     * @return      Partición en forma de array de bytes de entre 0 y 512 bytes
     *
     * @throws IOException  Hubo un problema al leer los datos
     */
    public static byte[] crearParticion(FileInputStream lector) throws IOException {
        byte[] particion =  new byte[TFTP.LONGITUD_MAX];
        int leido = lector.read(particion);     // Escribir en la partición

        // Ajustar la partición si se leen menos
        if (leido == -1) {
            particion = new byte[0];

        } else if (leido < TFTP.LONGITUD_MAX) {
            byte[] aux = new byte[leido];

            System.arraycopy(particion, 0, aux, 0, leido);
            particion = aux;
        }

        return particion;
    }

    /**
     * Concatena los datos recibidos en un paquete DATA con los datos
     * recibidos anteriormente.
     *
     * @param contenido     Datos totales
     * @param particion     Partición de fichero recibida
     *
     * @return      Array con los datos anteriores y actuales
     */
    public static byte[] agregar(byte[] contenido, byte[] particion) {
        byte[] union = new byte[contenido.length + particion.length];

        System.arraycopy(contenido, 0, union, 0, contenido.length);
        System.arraycopy(particion, 0, union, contenido.length, particion.length);

        return union;
    }

    /**
     * Escribe el contenido de un array de bytes en un fichero.
     *
     * @param contenido     Datos a escribir
     * @param fichero       Fichero donde escribir
     *
     * @return      TRUE si la operación tuvo éxito, FALSE en caso contrario
     *
     * @throws IOException  Hubo un problema con el fichero
     */
    public static boolean escribirFichero(byte[] contenido, File fichero) throws IOException {
        boolean res;

        if (fichero.createNewFile()) {
            FileOutputStream escritor = new FileOutputStream(fichero.getAbsolutePath());     // Flujo de entrada de datos

            // Escribir en el fichero
            escritor.write(contenido);
            escritor.close();

            res = true;

        } else {
            res = false;
        }

        return res;
    }
}
