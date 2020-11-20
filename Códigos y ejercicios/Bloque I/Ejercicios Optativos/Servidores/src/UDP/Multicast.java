package UDP;

import java.net.*;
import java.util.Arrays;

public class Multicast {
    public static void main(String[] args) throws Exception {
        // Unirse a un grupo multicast y enviar hola
        byte[] msg = {'H', 'e', 'l', 'l', 'o'};

        /* Dirección multicast de clase "D". Si la definimos en
        el fichero /etc/hosts podremos utilizar un nombre */
        InetAddress group = InetAddress.getByName("228.5.6.7");
        System.out.println(group);

        /* Creo un socket multicast y lo asocio al puerto
        representante del grupo */
        MulticastSocket s = new MulticastSocket(6789);

        // Me uno al grupo identificado por 228.5.6.7 y puerto 6789
        s.joinGroup(group);

        // Envío el hello
        DatagramPacket hi = new DatagramPacket(msg, msg.length,group, 6789);
        s.send(hi);

        // leo las respuestas de los demás, incluida la mía !
        byte[] buf = new byte[1000];

        // tengo que hacer tantas lecturas como participantes
        DatagramPacket recv = new DatagramPacket(buf,buf.length);

        s.receive(recv);
        System.out.println(Arrays.toString(recv.getData()));

        // Abandonar el grupo multicast
        s.leaveGroup(group);
    }
}
