import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Serverconnection implements Runnable {

    private Socket server;
    private BufferedReader in;
    private String [] names;



    public Serverconnection(Socket s, String  name) throws IOException {
        server = s;
        for(int i=0; i< 4; i++) {
            if(names[i] == null) {
                names[i] = name;
                break;
            }
        }

        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    @Override
    public void run() {

        try {
            while (true) {
                String serverResponse = in.readLine();
                if(serverResponse == null) break;
                System.out.println(names + ": " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

