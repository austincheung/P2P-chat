import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

    public static String[] names;
    private static final int PORT=9090;

    private static ArrayList<clienthandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException{
        ServerSocket listener = new ServerSocket(PORT);
        while(true) {
            System.out.println("[SERVER} waiting for client connection...");
            Socket client = listener.accept();
            System.out.println("[SERVER] Connected to client");
            clienthandler clientThread = new clienthandler(client, clients);
            clients.add(clientThread);

            pool.execute(clientThread);
        }



    }
}


