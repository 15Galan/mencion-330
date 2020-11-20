package Paquetes;

import java.io.*;

public class RRQ implements Paquete {

    String fichero;
    String modo;

    byte[] buffer;


    public RRQ() {

    }

    public RRQ(byte[] buffer) {
        this.buffer = buffer;
    }


    /**
     * Construye un buffer de tipo "Read Request" (RRQ)
     * cuya estructura es la siguiente:
     *
     *  2 bytes    String    1 byte    String    1 byte
     *  ------------------------------------------------
     * |   01  |   fichero   |   0  |    modo    |   0  |
     *  ------------------------------------------------
     *
     * @return  Buffer con la estructura RRQ
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
