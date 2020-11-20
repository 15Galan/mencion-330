package Paquetes;

import java.io.*;

public class WRQ implements Paquete {

    String fichero;
    String modo;

    public byte[] buffer;


    public WRQ() {

    }

    public WRQ(String fichero) {
        this.fichero = fichero;
    }

    /**
     * Construye un buffer de tipo "Write Request" (WRQ)
     * cuya estructura es la siguiente:
     *
     *  2 bytes    String    1 byte    String    1 byte
     *  ------------------------------------------------
     * |   02  |   fichero   |   0  |    modo    |   0  |
     *  ------------------------------------------------
     *
     * @return  Buffer con la estructura mencionada
     */
    @Override
    public byte[] montar() throws IOException {
        // TODO

        return buffer;
    }

    @Override
    public void desmontar() throws IOException {
        // TODO
    }
}
