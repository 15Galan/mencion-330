package Paquetes;

import java.io.*;

public class RRQ implements TFTP {

    // Paquete TFTP
    public short opcode;
    public byte[] buffer;

    // Paquete RRQ
    private String fichero;
    private String modo = "octet";


    public RRQ() {
        this(null, null);
    }

    public RRQ(String fichero, String modo) {
        this.fichero = fichero;
        this.modo = modo;
        opcode = 1;
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

        opcode = in.readShort();
        fichero = new String(leerDatos(in)).trim();
        modo = new String(leerDatos(in)).trim();
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
        int i = 0;
        byte b;

        do {
            b = stream.readByte();

            if (b != 0) {
                fichero[i] = b;
                i++;
            }

        } while (b != 0);

        return fichero;
    }
}
