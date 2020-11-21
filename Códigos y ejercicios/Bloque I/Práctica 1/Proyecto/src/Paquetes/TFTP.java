package Paquetes;

import java.io.IOException;

public interface TFTP {

    int LONGITUD_MAX = 512;     // Tamaño máximo de un trozo de fichero

    /**
     * Genera un paquete TFTP según la estructura indicada por su opcode.
     *
     * @throws IOException      No se ha podido montar el paquete
     */
    public abstract void montar() throws IOException;

    /**
     * Extrae la información de un paquete TFTP según la estructura
     * indicada por su opcode.
     *
     * @throws IOException      No se ha podido desmontar el paquete
     */
    public abstract void desmontar() throws IOException;
}
