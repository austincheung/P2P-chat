import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class clienthandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<clienthandler> clients;

    public clienthandler(Socket clientSocket, ArrayList<clienthandler> clients) throws IOException {
    this.client = clientSocket;
    this.clients = clients;
    in = new BufferedReader(new InputStreamReader((client.getInputStream())));
    out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = in.readLine();
                if(request.startsWith("say")){
                    int firstSpace = request.indexOf(" ");
                    if(firstSpace != -1) {
                        outToAll(request.substring(firstSpace+1));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IO Exception in client handler");
            System.err.println(e.getStackTrace());
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
            }

        }


    private void outToAll(String msg){
    for(clienthandler  aClient : clients){
    aClient.out.println(msg);
    }
    }
}