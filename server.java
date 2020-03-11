
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    /*private static ArrayList<clienthandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException{
        ServerSocket listener = new ServerSocket(PORT);
        System.out.println("[SERVER} waiting for client connection...");

        while(true) {
            Socket client = listener.accept();
            System.out.println("[SERVER] Connected to client");
            clienthandler clientThread = new clienthandler(client, clients);
            clients.add(clientThread);
            for (int i=0; i < clients.size();i++){
                System.out.println(clients.get(i));
            }
            pool.execute(clientThread);
        }
    }*/
}

