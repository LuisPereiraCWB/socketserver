package com.test.socketserver;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ServerGUI {
    private JFrame frame;
    private JButton startButton;
    private JButton stopButton;
    private JButton clearLogButton;
    private JButton sendButton;
    private JTextField portField;
    private JTextField ipField;
    private JTextField messageField;
    private JTextArea logArea;
    private SocketServer server;

    public ServerGUI() {
        frame = new JFrame("Servidor Socket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes

        ipField = new JTextField("127.0.0.1", 15);
        portField = new JTextField("3000", 10);
        messageField = new JTextField(20);
        startButton = new JButton("Iniciar");
        stopButton = new JButton("Parar");
        clearLogButton = new JButton("Limpar Log");
        sendButton = new JButton("Enviar");
        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Adicionando componentes com GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("IP:"), gbc);
        
        gbc.gridx = 1;
        frame.add(ipField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Porta:"), gbc);

        gbc.gridx = 1;
        frame.add(portField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(startButton, gbc);

        gbc.gridx = 1;
        frame.add(stopButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(messageField, gbc);

        gbc.gridx = 1;
        frame.add(sendButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa duas colunas
        frame.add(clearLogButton, gbc);

        gbc.gridy = 5;
        frame.add(scrollPane, gbc);

        // Configurar action listeners
        startButton.addActionListener(new StartButtonListener());
        stopButton.addActionListener(new StopButtonListener());
        clearLogButton.addActionListener(new ClearLogButtonListener());
        sendButton.addActionListener(new SendButtonListener());

        frame.setVisible(true);
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());
            server = new SocketServer(logArea);
            new Thread(() -> server.start(port, ip)).start();
        }
    }

    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (server != null) {
                server.stop();
            }
        }
    }

    private class ClearLogButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logArea.setText(""); // Limpa a área de log
        }
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = messageField.getText();
            if (server != null) {
                if (server.hasClients()) { // Verifica se há clientes conectados
                    server.sendMessage(message); // Envia a mensagem para o servidor
                    messageField.setText(""); // Limpa o campo de mensagem
                } else {
                    JOptionPane.showMessageDialog(frame, "Nenhum cliente conectado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "O servidor não está em execução.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerGUI::new);
    }
}