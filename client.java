import java.io.*;
import java.net.Socket;

public class client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(SERVER_IP, SERVER_PORT);


        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Please enter name");
        String client_name =keyboard.readLine();
        for(int i=0; i< 4; i++) {
            if(server.names[i].isEmpty()) {
                server.names[i] = client_name;
                Serverconnection serverConn = new Serverconnection(socket, server.names[i]);
                new Thread(serverConn).start();
                break;
            }
        }

        while(true) {
            System.out.println("> ");
            String command = keyboard.readLine();

            if(command.equals("quit")) break;
            out.println(client_name + " is now offline");
            out.println(command);
        }
        socket.close();
        System.exit(0);
    }

}