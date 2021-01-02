package Paquetes;

import java.io.*;

public class WRQ implements TFTP {

    // Paquete TFTP
    public final short opcode = 2;
    public byte[] buffer;

    // Paquete WRQ
    private String fichero;
    private String modo;


    // Constructores
    public WRQ(String fichero, String modo) throws IOException {
        this.fichero = fichero;
        this.modo = modo;

        montar();
    }

    public WRQ(byte[] buffer) throws IOException {
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
     * Construye un buffer de tipo "Write Request" (WRQ)
     * cuya estructura es la siguiente:
     *
     *  2 bytes    String    1 byte    String    1 byte
     *  ------------------------------------------------
     * |   02  |   fichero   |   0  |    modo    |   0  |
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
        fichero = new String(leerDatos(in));
        modo = new String(leerDatos(in));
    }
}
