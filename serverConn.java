import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;

public class serverConn implements Runnable {
    private Socket socket;
    private String name;
    private ArrayList<String> messagesToSend;
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
    private boolean exit = false;
    public synchronized void run(){
        System.out.println("Welcome " + name);
        System.out.println("The Port You are on is :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());
        System.out.println(name +" is currently OFFLINE");
        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);

            while(!socket.isClosed()){
                String latest= "";
                if(!messagesToSend.isEmpty()){
                    latest = messagesToSend.get(messagesToSend.size()-1);
                }
                switch (latest){
                    case "commands": System.out.println("The list of commands are \nOnline: This will toggle your status from being offline to online. \nOffline: This will toggle your status from being online to offline.\n...");
                    break;
                    case "online":
                            offline = false;
                            System.out.println("You are now ONLINE");
                        server.onlineUsers.add(new server.userProfile(name, "Person" + socket.getLocalPort(), "" + socket.getRemoteSocketAddress(), socket.getPort()));

                    //System.out.println(onlineUsers.get(0).username);
                    break;
                    case "offline": offline=true;
                        System.out.println("You are now OFFLINE");
                        if (!server.onlineUsers.isEmpty()) {
                            for (int i = 0; i < server.onlineUsers.size(); i++) {
                                if (server.onlineUsers.get(i).username.equals(name)) {
                                    server.onlineUsers.remove(i);
                                }
                            }
                        }

                    break;
                    case "query":
                        if(server.onlineUsers.isEmpty()){
                            System.out.println("There are no Users online :(");
                        }
                        else{
                            for(int i=0; i< server.onlineUsers.size();i++){
                                System.out.println(server.onlineUsers.get(i).toString());
                            }
                        }
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

