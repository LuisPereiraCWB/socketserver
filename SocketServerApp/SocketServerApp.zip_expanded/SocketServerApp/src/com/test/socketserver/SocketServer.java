package com.test.socketserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JTextArea;

public class SocketServer {
    private ServerSocket serverSocket;
    private boolean isRunning;
    private JTextArea logArea;
    private List<PrintWriter> clientWriters;
    private static Logger logger;

    public SocketServer(JTextArea logArea) {
        this.logArea = logArea;
        this.clientWriters = new ArrayList<>();
        setupLogger();
    }
    

    private void setupLogger() {
    	try {
            // Cria o logger e define o nível de log
            logger = Logger.getLogger(SocketServer.class.getName());
            logger.setLevel(Level.ALL);

            // Cria um arquivo de log na mesma pasta do JAR
            FileHandler fileHandler = new FileHandler("server.log", true); // 'true' para anexar ao arquivo existente
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            log("Erro ao configurar o logger: " + e.getMessage());
        }
    }

    public void start(int port, String ip) {
        isRunning = true;
        try {
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName(ip));
            log("Servidor iniciado em " + ip + " na porta: " + port);
            logger.info("Servidor iniciado em " + ip + " na porta: " + port);

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Aceita a conexão
                    new ClientHandler(clientSocket, logArea, this).start();
                } catch (SocketException e) {
                    if (!isRunning) {
                        break; // Sai do loop se o servidor estiver parando
                    }
                    throw e; // Lança o erro se for outro tipo de erro
                }
            }
        } catch (IOException e) {
            log("Erro ao iniciar o servidor: " + e.getMessage());
            logger.severe("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) { // Verifica se o socket está aberto
                serverSocket.close();
            }
            log("Servidor parado.");
            logger.info("Servidor parado."); // Loga a informação
        } catch (IOException e) {
            log("Erro ao parar o servidor: " + e.getMessage());
            logger.severe("Erro ao parar o servidor: " + e.getMessage()); // Loga o erro
        }
    }

    public void addClient(PrintWriter out) {
        clientWriters.add(out); // Adiciona o PrintWriter do cliente
    }

    public void removeClient(PrintWriter out) {
        clientWriters.remove(out); // Remove o PrintWriter do cliente
    }

    public void sendMessage(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message); // Envia a mensagem para todos os clientes
        }
        log("Mensagem enviada: " + message);
        logger.info("Mensagem enviada: " + message); // Loga a informação
    }

    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public boolean hasClients() {
        return !clientWriters.isEmpty(); // Retorna true se houver clientes conectados
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private JTextArea logArea;
        private PrintWriter out; // Para enviar mensagens ao cliente
        private SocketServer server; // Referência ao servidor

        public ClientHandler(Socket socket, JTextArea logArea, SocketServer server) {
            this.clientSocket = socket;
            this.logArea = logArea;
            this.server = server;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                this.out = out; // Armazena o PrintWriter para enviar mensagens
                server.addClient(out); // Adiciona o PrintWriter à lista de clientes

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    log("Recebido: " + inputLine);
                    logger.info("Recebido: " + inputLine); // Loga a informação
                    //out.println("Eco: " + inputLine);
                    //log("Eco: " + inputLine);
                }
            } catch (IOException e) {
                log("Erro no cliente: " + e.getMessage());
                logger.severe("Erro no cliente: " + e.getMessage());
            } finally {
                try {
                    server.removeClient(out); // Remove o cliente da lista
                    clientSocket.close();
                } catch (IOException e) {
                    log("Erro ao fechar o socket: " + e.getMessage());
                    logger.severe("Erro ao fechar o socket: " + e.getMessage());
                }
            }
        }

        private void log(String message) {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }
}