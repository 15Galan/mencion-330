package Herramientas;

import Paquetes.TFTP;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

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

    public static byte[] crearParticion(FileInputStream lector) throws IOException {
        byte[] particion = new byte[TFTP.LONGITUD_MAX];
        int leido = lector.read(particion);     // Escribir en la partición

        // Ajustar la partición si se leen menos
        if (leido < TFTP.LONGITUD_MAX) {
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
     * @param datos         Datos totales
     * @param particion     Partición de fichero recibida
     *
     * @return      Array con los datos anteriores y actuales
     */
    public static byte[] agregar(byte[] datos, byte[] particion) {
        byte[] union = new byte[datos.length + particion.length];

        System.arraycopy(datos, 0, union, 0, datos.length);
        System.arraycopy(particion, 0, union, datos.length, particion.length);

        return union;
    }
}
