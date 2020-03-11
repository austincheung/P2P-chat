import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private String username;
    private String serverHost;
    private  int serverPort;
    private Scanner userInput;
    private boolean isOnline = false;

    private client(String username, String host, int portNum, boolean online ){
        this.username = username;
        this.serverHost = host;
        this.serverPort = portNum;
        this.isOnline=online;
    }

    public static void main(String[] args) throws IOException {
        String name = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter name");
        while(name == null || name.trim().equals("")){
            // null, empty, whitespace(s) not allowed.
            name = scanner.nextLine();
            if(name.trim().equals("")){
                System.out.println("Invalid. Please enter again:");
            }
        }
        client client = new client(name, SERVER_IP, SERVER_PORT,false);
        client.startClient(scanner);
    }

    private void startClient(Scanner scan) throws IOException {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            serverConn serverThread = new serverConn(socket, username);
            Thread serverAccessThread = new Thread(serverThread);
            serverAccessThread.start();
            while (serverAccessThread.isAlive()) {
                if(scan.hasNextLine()){
                    serverThread.addNextMessage(scan.nextLine());
                }
            }
        } catch (IOException ex) {
            System.err.println("Fatal Connection error!");
            ex.printStackTrace();
        }
    }
}



