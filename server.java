
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

    //public static String[] names;
    private static final int PORT=9090;

    private int serverPort;
    public server(int portNum){
        this.serverPort = portNum;
    }
    private ArrayList<clienthandler> clients = new ArrayList<>();

    public static void main(String[] args){
        server server = new server(PORT);
        server.startServer();
    if(!onlineUsers.isEmpty()){
        System.out.println("hi");
    }
    }

    private void startServer(){
       clients = new ArrayList<clienthandler>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
            acceptClients(serverSocket);
        } catch (IOException e){
            System.err.println("Could not listen on port: "+serverPort);
            System.exit(1);
        }
    }
    public ArrayList<clienthandler> getClients(){
        return clients;
    }
    private void acceptClients(ServerSocket serverSocket) {
        System.out.println("server starts port = " + serverSocket.getLocalSocketAddress());
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("accepts : " + socket.getRemoteSocketAddress());
                clienthandler client = new clienthandler(this, socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch (IOException ex) {
                System.out.println("Accept failed on : " + serverPort);
            }
        }
    }
    public static List<userProfile> onlineUsers = Collections.synchronizedList(new ArrayList<userProfile>());

    public static  class userProfile{
        public String username;
        public String hostname;
        public String ip;
        public int portnum;

        public userProfile(String username, String hostname, String ip, int portnum){
            this.username= username;
            this.hostname=hostname;
            this.ip=ip;
            this.portnum = portnum;
        }
    }

}

