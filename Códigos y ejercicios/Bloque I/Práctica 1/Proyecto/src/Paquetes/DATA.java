package Paquetes;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class DATA implements TFTP {

    // Paquete TFTP
    public short opcode = 3;
    public byte[] buffer;

    // Paquete DATA
    private int bloque = 1;
    private byte[] datos;

    public DATA() {
    }

    public DATA(byte[] buffer) {
        this.buffer = buffer;
        datos  = new byte[LONGITUD_MAX];
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

//        datos = new String(datos).trim().getBytes();    // Limpiar bytes nulos sobrantes
    }

    public void actualizar() {
        bloque++;
    }

    /**
     * Lee un array de bytes hasta el byte nulo (byte 0), a través de
     * un stream de datos, que contiene el array de bytes del que leer.
     * Este método también lee el byte nulo, pero no lo incluye.
     *
     * @param stream    Stream de datos que lee un array de bytes
     *
     * @return          Un array de bytes sin incluir el byte nulo (byte 0)
     *
     * @throws IOException  El array de bytes no pudo leerse correctamente
     */
    private byte[] leerDatos(DataInputStream stream) throws IOException {
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
