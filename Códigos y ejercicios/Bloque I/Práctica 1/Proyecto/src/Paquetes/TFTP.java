package Paquetes;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

public interface TFTP {

    int LONGITUD_MAX = 512;     // Tamaño máximo de una partición de fichero

    /**
     * Genera un paquete TFTP según la estructura indicada por su opcode.
     *
     * @throws IOException      No se ha podido montar el paquete
     */
    void montar() throws IOException;

    /**
     * Extrae la información de un paquete TFTP según la estructura
     * indicada por su opcode.
     *
     * @throws IOException      No se ha podido desmontar el paquete
     */
    void desmontar() throws IOException;

    /**
     * Lee un array de bytes hasta el byte nulo (byte 0), a través de
     * un flujo de datos, que contiene el array de bytes del que leer.
     * Este método también lee el byte nulo, pero no lo incluye.
     *
     * @param stream    Flujo de datos que lee un array de bytes
     *
     * @return          Sub-array del array inicial, hasta un byte nulo
     *
     * @throws IOException  El array de bytes no pudo leerse correctamente
     */
    default byte[] leerDatos(DataInputStream stream) throws IOException {
        byte[] fichero = new byte[LONGITUD_MAX];
        int leido = 0;
        byte b;

        do {
            b = stream.readByte();

            if (b != 0) {
                fichero[leido] = b;
                leido++;
            }

        } while (b != 0);

        return Arrays.copyOfRange(fichero, 0, leido);
    }
}
