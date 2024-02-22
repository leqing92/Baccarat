package sg.edu.nus.iss.baccarat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class serverApp {
    public static void main(String[] args) throws Exception {
        System.out.println("ServerApp.main()");

        int decksNo = 1;
        int portNumber = 3000;        

        if(args.length > 0){
            portNumber = Integer.parseInt(args[0]);
            decksNo = Integer.parseInt(args[1]);
        }
        try (ExecutorService tp = Executors.newFixedThreadPool(2)) {
            while(true){
                try (ServerSocket server = new ServerSocket(portNumber)) {
                    Socket socket = server.accept();
                    clientHandler ch = new clientHandler(socket, decksNo);
                    tp.submit(ch);
                }
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}