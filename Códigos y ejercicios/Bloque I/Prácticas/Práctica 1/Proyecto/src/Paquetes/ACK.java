package Paquetes;

/*
 * Universidad de Málaga
 * E.T.S Ingeniería Informática
 *
 * Desarrollo de Servicios Telemáticos
 * Práctica 1: Servidor y cliente TFTP
 *
 * Antonio J. Galán Herrera
 */

import java.io.*;

public class ACK implements TFTP {

    // Paquete TFTP
    public short opcode = 4;
    public byte[] buffer;

    // Paquete ACK
    private int bloque = 0;


    // Constructores
    public ACK() throws IOException {
        montar();
    }

    public ACK(int bloque) throws IOException {
        this.bloque = bloque;

        montar();
    }

    public ACK(byte[] buffer) throws IOException {
        this.buffer = buffer;

        desmontar();
    }


    // Getters
    public int getBloque() {
        return bloque;
    }

    // Setters
    public void setBloque(int valor) {
        bloque = valor;
    }


    /**
     * Construye un buffer de tipo "Acknowledgement" (ACK)
     * cuya estructura es la siguiente:
     *
     *  2 bytes    2 bytes
     *  ---------------------
     * |  04   |   bloque #  |
     *  ---------------------
     */
    @Override
    public void montar() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);

        out.writeShort(opcode);
        out.writeShort(bloque);

        buffer = byteStream.toByteArray();
    }

    @Override
    public void desmontar() throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
        DataInputStream in = new DataInputStream(byteStream);

        in.readShort();     // Ignorar opcode
        bloque = in.readShort();
    }
}
