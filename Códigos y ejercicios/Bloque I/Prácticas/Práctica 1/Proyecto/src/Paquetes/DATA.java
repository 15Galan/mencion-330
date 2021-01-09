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

public class DATA implements TFTP {

    // Paquete TFTP
    public short opcode = 3;
    public byte[] buffer;

    // Paquete DATA
    private int bloque = 1;
    private byte[] datos;


    // Constructores
    public DATA(byte[] datos, int bloque) throws IOException {
        this.datos  = datos;
        this.bloque = bloque;

        montar();
    }

    public DATA(byte[] buffer) throws IOException {
        this.buffer = buffer;

        desmontar();
    }


    // Getters
    public int getBloque() {
        return bloque;
    }

    public byte[] getDatos() {
        return datos;
    }

    // Setters
    public void setBloque(int valor) {
        bloque = valor;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }


    /**
     * Construye un buffer de tipo "Data" (DATA)
     * cuya estructura es la siguiente:
     *
     *  2 bytes    2 bytes       n bytes
     *  -----------------------------------
     * |  03   |   bloque #  |    datos    |
     *  -----------------------------------
     */
    @Override
    public void montar() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);

        out.writeShort(opcode);
        out.writeShort(bloque);
        out.write(datos);

        buffer = byteStream.toByteArray();
    }

    @Override
    public void desmontar() throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
        DataInputStream in = new DataInputStream(byteStream);

        in.readShort();     // Ignorar opcode
        bloque = in.readShort();
        datos = leerDatos(in);
    }
}
