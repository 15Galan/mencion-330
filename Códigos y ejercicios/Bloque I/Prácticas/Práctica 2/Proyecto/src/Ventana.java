/*
 * Universidad de Málaga
 * E.T.S Ingeniería Informática
 *
 * Desarrollo de Servicios Telemáticos
 * Práctica 2: Chat multicliente con Selector / NIO
 *
 * Antonio J. Galán Herrera
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class Ventana extends JFrame implements Observer {

    // Cliente
    private final ClienteGUI.AccesoChat cliente;    // Cliente al que se le construye la ventana

    // Campos de la GUI
    private JTextField escrituraTexto;      // Área para escribir el mensaje a enviar
    private JTextArea areaTexto;            // Área en el que se muestran los mensajes recibidos


    // Constructor
    public Ventana(ClienteGUI.AccesoChat cliente) {
        this.cliente = cliente;
        cliente.addObserver(this);

        crearGUI();
    }

    /**
     * Construye la GUI del cliente.
     */
    private void crearGUI() {
        // Inicializar el área de mensajes recibidos
        areaTexto = new JTextArea(20, 50);

        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        // Crear un botón enviar el mensaje escrito
        JButton botonEnvio = new JButton("Enviar");

        // Crear una zona para escribir mensajes
        escrituraTexto = new JTextField();

        // Crear una sección de ventana con lo anterior
        Box box = Box.createHorizontalBox();
        add(box, BorderLayout.SOUTH);
        box.add(escrituraTexto);
        box.add(botonEnvio);

        // Definir la acción para el campo del mensaje: enviar pulsando el botón
        ActionListener listener_envio = e -> {
            String mensaje = escrituraTexto.getText();

            if (mensaje != null && mensaje.trim().length() > 0) {
                cliente.enviar(mensaje);
            }

            escrituraTexto.selectAll();
            escrituraTexto.requestFocus();
            escrituraTexto.setText("");
        };

        // Asignar la acción anterior al campo de escritura y al botón
        escrituraTexto.addActionListener(listener_envio);
        botonEnvio.addActionListener(listener_envio);

        // Definir la acción al cerrar la ventana: finalizar el cliente
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                cliente.cerrar();
            }
        });
    }


    /**
     *  Actualizar la GUI con los nuevos datos recibidos;
     *  es decir, mostrar los mensajes nuevos.
     */
    @Override
    public void update(Observable o, Object arg) {
        final Object argumento = arg;

        SwingUtilities.invokeLater(() -> {
            areaTexto.append(argumento.toString());
            areaTexto.append("\n");
        });
    }
}