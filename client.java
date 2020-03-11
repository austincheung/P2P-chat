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


    private client(String username, String host, int portNum ){
        this.username = username;
        this.serverHost = host;
        this.serverPort = portNum;
    }

    public static void main(String[] args) throws IOException {
        /*String name= null;
        Scanner scanner= new Scanner(System.in);
        System.out.println("Please enter name");
        while(name == null || !(name.equals("")) ){
            name = scanner.nextLine();
            if(name.equals("")){
                System.out.println("Invalid Name Please enter in something");
            }
        }
        client client = new client(name, SERVER_IP,SERVER_PORT);
        client.startClient(scanner);
        */
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);


        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Please enter name");
        String client_name =keyboard.readLine();
        System.out.println("Welcome " + client_name);

        Serverconnection serverConn = new Serverconnection(socket);
        new Thread(serverConn).start();
        System.out.println(client_name + " is now offline");
        while(true) {
            System.out.print("> ");
            String command = keyboard.readLine();

            if(command.equals("quit")) break;
            out.println(command + "\n");
        }
        socket.close();
        System.exit(0);
    }

    /*private void startClient(Scanner scan) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        Serverconnection serverConn = new Serverconnection(socket);
        new Thread(serverConn).start();
        while(true) {
            if(scan.equals("quit")) {
            break;}
        }
        socket.close();
        System.exit(0);
    }*/



}