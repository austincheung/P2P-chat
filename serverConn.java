import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class serverConn implements Runnable {
    private Socket socket;
    private String name;
    private boolean isOn;
    private final ArrayList<String> messagesToSend;
    private boolean hasMessages = false;


    public serverConn(Socket socket, String user){
        this.socket = socket;
        this.name=user;
        messagesToSend = new ArrayList<String>();
    }

    public void addNextMessage(String message){
            hasMessages = true;
            messagesToSend.add(message);
    }
    private boolean offline = true;
    private boolean join = false;
    private boolean query = false;
    private boolean exit = false;
    public void run(){
        System.out.println("Welcome :" + name);
        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());
        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            while(!socket.isClosed()){
                String latest= "";
                if(!messagesToSend.isEmpty()){
                    latest = messagesToSend.get(0);
                }
                switch (latest){
                    case "commands": System.out.println("The list of commands are \nOnline: This will toggle your status from being offline to online. \nOffline: This will toggle your status from being online to offline.\n...");
                    case "online": offline=false; System.out.println("ONLINE");break;
                    case "join":join = true; break;
                }

                messagesToSend.clear();

            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }



    }
}

