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
        //System.out.println(BaccaratEngine.loadDeck(decksNo).toString());            

    }
}


// Socket socket = null;
        // InputStream is = null;
        // OutputStream os = null;
        // ServerSocket serverSocket = null;

        // try{
        //     serverSocket = new ServerSocket(portNumber);
        //     System.out.println("-----Baccarat Server Started-----");
        //     while(true){
        //         socket = serverSocket.accept();
        //         is = socket.getInputStream();
        //         DataInputStream dis = new DataInputStream(is);

        //         os = socket.getOutputStream();
        //         DataOutputStream dos = new DataOutputStream(os);

        //         while(true) {
        //             try{
        //                 String dataFromClient = dis.readUTF().toLowerCase();
        //                 System.out.println(dataFromClient);
        //                 dos.writeUTF("Data from server");
        //                 if(dataFromClient.equals("quit")){
        //                     System.out.println("Client requested to close connection");
        //                     dos.writeUTF("Goodbye");
        //                     socket.close();                        
        //                 }if(dataFromClient.equals("hi")){
        //                     dos.writeUTF("Hi , client");
        //                     dos.flush();
        //                 }else{
        //                     System.out.println("invalid command");
        //                 }
        //                 System.out.println("Data sent from server");
        //             }catch(EOFException e){                        
        //                 socket.close(); //with this, server will close socket and let client to reconnect with new data stream while waiting
        //                 break; //with this it re-run the while loop
        //             }
        //         }
        //     }
        // } finally{
        //     is.close();
        //     os.close();
        //     socket.close();
        //     serverSocket.close();
        // }
