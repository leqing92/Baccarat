package sg.edu.nus.iss.baccarat.client;

import java.io.Console;
import java.io.IOException;
import java.net.Socket;

import sg.edu.nus.iss.baccarat.server.NetworkIO;

public class clientApp {
    public static void main(String[] args) throws Exception {
        System.out.println("ClientApp.main()");
        
        String[] connInfo = args[0].split(":");        
        Socket sock = null;
        NetworkIO nioC = null;
        boolean stop = false;

        try{
            //System.out.println("client come here?");
            while(!stop){
                
                sock = new Socket(connInfo[0], Integer.parseInt(connInfo[1]));
                nioC = new NetworkIO(sock);
                //System.out.println("connect to server at " + sock);
                
                while(!stop){

                    String response = nioC.read();
                    //System.out.println("server: " + response);
                    
                    Console cons = System.console();

                    if(response.startsWith(">")){
                        
                        String req = cons.readLine("Send client command to the server: ");
                        nioC.write(req);
                                                
                        //System.out.println("Data sent to server");
                    }
                    else if(response.toLowerCase().startsWith("bye")){
                        System.out.println(response);
                        stop = true;
                        break;
                    }
                    else if(response.startsWith("P|")){
                        System.out.println("Card combination: " + response);
                        String reqAfterDeal = card.checkCard(response);
                        System.out.println("After check card combination: " + reqAfterDeal);
                        nioC.write(reqAfterDeal);                        
                    }
                    else{
                        System.out.println("server: " + response); 
                    }                     
                }
            }            
            nioC.close();
            sock.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
