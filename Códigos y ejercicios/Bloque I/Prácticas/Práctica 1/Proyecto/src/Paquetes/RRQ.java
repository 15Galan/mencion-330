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

public class RRQ implements TFTP {

    // Paquete TFTP
    public final short opcode = 1;
    public byte[] buffer;

    // Paquete RRQ
    private String fichero;
    private String modo;


    // Constructores
    public RRQ(String fichero, String modo) throws IOException {
        this.fichero = fichero;
        this.modo = modo;

        montar();
    }

    public RRQ(byte[] buffer) throws IOException {
        this.buffer = buffer;

        desmontar();
    }


    // Getters
    public String getFichero() {
        return fichero;
    }

    public String getModo() {
        return modo;
    }

    // Setters
    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }


    /**
     * Construye un buffer de tipo "Read Request" (RRQ)
     * cuya estructura es la siguiente:
     *
     *  2 bytes    String    1 byte    String    1 byte
     *  ------------------------------------------------
     * |   01  |   fichero   |   0  |    modo    |   0  |
     *  ------------------------------------------------
     */
    @Override
    public void montar() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);

        out.writeShort(opcode);
        out.write(fichero.getBytes());
        out.writeByte(0);
        out.write(modo.toLowerCase().getBytes());
        out.writeByte(0);

        buffer = byteStream.toByteArray();
    }

    @Override
    public void desmontar() throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(buffer));

        in.readShort();     // Ignorar opcode
        fichero = new String(leerDatos(in)).trim();
        modo = new String(leerDatos(in)).trim();
    }
}
