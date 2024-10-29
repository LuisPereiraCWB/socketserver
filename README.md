# Socket Server em Java

Este projeto implementa um servidor socket multi-threaded em Java, permitindo a conexão de múltiplos clientes para envio e recebimento de mensagens. O servidor possui uma interface gráfica (GUI) para facilitar a interação, além de registrar logs de eventos e mensagens em um arquivo de log.

## Estrutura do Projeto

O projeto é composto por duas classes principais:

- **ServerGUI**: Interface gráfica que permite ao usuário configurar o servidor (IP e porta) e controlar sua execução (iniciar/parar o servidor). Além disso, a GUI permite enviar mensagens para todos os clientes conectados e exibir o log de atividades.

- **SocketServer**: Lida com as operações do servidor socket, incluindo:
  - Aceitação de conexões de múltiplos clientes.
  - Envio de mensagens a todos os clientes conectados.
  - Registro de eventos e mensagens em um arquivo de log (`server.log`).
  - Controle de threads para gerenciar as conexões de clientes.
 
- **SimpleClient**: Permite que você se conecte ao servidor socket para verificar a comunicação básica entre cliente e servidor.

## Funcionalidades

- **Iniciar/Parar Servidor**: Inicie e pare o servidor a partir da interface gráfica.
- **Envio de Mensagens**: Envie mensagens para todos os clientes conectados pelo campo de entrada de mensagens.
- **Registro de Log**: Registre mensagens e eventos em um arquivo de log (`server.log`), localizado na pasta do projeto.
- **Controle de Clientes**: Gerencie conexões de clientes e o envio de mensagens por meio de uma lista de clientes conectados.

## Como executar o Servidor Socket a partir do .jar

### Pré-requisitos
- Certifique-se de que o [Java Runtime Environment (JRE) versão 8 ou superior] esteja instalado.

### Executando o .jar

1. Navegue até o diretório onde o arquivo `.jar` está localizado (por exemplo, `target/socketserver.jar`).
2. Execute o seguinte comando no terminal:

   ```bash
   java -jar socketserver.jar

- Este comando iniciará a GUI, onde você poderá configurar o IP e a Porta para o servidor, além de iniciar/parar o servidor e gerenciar a comunicação com os clientes conectados.

## Compilar e Executar o Código Fonte
### Pré-requisitos

Para compilar e executar este projeto, você precisa de:

- Java Development Kit (JDK) 8 ou superior.
- Uma IDE compatível com projetos Java, como IntelliJ IDEA, Eclipse, ou o próprio `javac` e `java` para compilar e rodar o código.

## Uso

1. **Iniciar o Servidor:**
  - Abra a GUI.
  - Insira o IP e a Porta desejados para o servidor.
  - Clique em "Iniciar" para ativar o servidor socket.
2. **Conectar Clientes:**
  - Após o servidor estar em execução, clientes podem conectar-se usando o mesmo IP e Porta.
3. **Enviar Mensagens:**
  - Digite uma mensagem na GUI e clique em "Enviar" para enviar a mensagem para todos os clientes conectados.
4. **Parar o Servidor:**
  - Clique no botão "Parar" para finalizar o servidor.

## Estrutura de Arquivos

```plaintext
socket-server-java/
├── src/
│   └── com/test/socketserver/
│       ├── ServerGUI.java        # Classe da GUI
│       ├── SocketServer.java     # Classe principal do servidor
│       └── SimpleClientJava.java # Classe para simular um client
├── bin/                          # Arquivos .class compilados
└── server.log                    # Arquivo de log
````

## Configuração do Logger

O servidor registra logs em um arquivo server.log que contém:

  - Eventos de inicialização e encerramento do servidor.
  - Mensagens recebidas dos clientes.
  - Informações de erros e exceções.

## Exemplo de Uso

1. **Iniciar o Servidor:** Configure IP e Porta e clique em "Iniciar".
2. **Conectar Clientes:** Clientes conectam-se usando o mesmo IP e Porta.
3. **Envio de Mensagens:** Mensagens enviadas são retransmitidas a todos os clientes conectados.
4. **Logs:** Verifique o arquivo server.log para ver o histórico de mensagens e eventos.
