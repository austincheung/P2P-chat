import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class clienthandler implements Runnable {

    private Socket socket;
    private PrintWriter clientOut;
    private server server;

    public clienthandler(server server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    private PrintWriter getWriter(){
        return clientOut;
    }

    @Override
    public void run() {
        try{
            // setup
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);

            Scanner in = new Scanner(socket.getInputStream());

            // start communicating
            while(!socket.isClosed()){
                if(in.hasNextLine()){
                    String input = in.nextLine();
                    // NOTE: if you want to check server can read input, uncomment next line and check server file console.
                    // System.out.println(input);
                    for(clienthandler thatClient : server.getClients()){
                        PrintWriter thatClientOut = thatClient.getWriter();
                        if(thatClientOut != null){
                            thatClientOut.write(input + "\r\n");
                            thatClientOut.flush();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}