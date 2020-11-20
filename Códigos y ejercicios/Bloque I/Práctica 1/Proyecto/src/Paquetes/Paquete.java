package Paquetes;

import java.io.IOException;

public interface Paquete {
    int LONGITUD_MAX = 512;     // Tamaño máximo de un trozo de fichero

    byte[] montar() throws IOException;

    void desmontar() throws IOException;
}
