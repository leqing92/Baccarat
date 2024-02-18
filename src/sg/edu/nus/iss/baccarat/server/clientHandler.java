package sg.edu.nus.iss.baccarat.server;

import java.io.IOException;
import java.net.Socket;

public class clientHandler implements Runnable{
    private Socket sock;
    private int decksNo;
    boolean stop = false;       

    public clientHandler(Socket sock, int decksNo) {
        this.sock = sock;
        this.decksNo = decksNo;
    }

    @Override
    public void run() {
        NetworkIO nio = null;
        String req = "";

        try{
            System.out.println("waiting client to connect");
            nio = new NetworkIO(sock);
            
            while(!stop){                
                //nio.write("Welcome to Baccarat game"); 
                System.out.println("Client connected");
                while (!stop){  //1st while loop
                            
                    nio.write(">");                    
                    req = nio.read();
                    System.out.println("client1: " + req);                    
                    
                    if(req.equals("end")){
                        nio.write("Bye-bye");
                        System.out.println("Client1 log out");                        
                        stop = true;                
                        break;
                    }
                    else if(req.startsWith("login")){   
                        String response = BaccaratEngine.login(req.substring(6));
                        if(response.contains("negative")){
                            nio.write(response);
                            break;
                        }
                        BaccaratEngine.loadDeck(decksNo);
                        nio.write(response);
                        
                        while (!stop){ //2nd while loop
                            nio.write(">");
                            req = nio.read().toLowerCase();
                            System.out.println("client2: " + req);

                            if(req.equals("end")){
                                nio.write("Bye-bye");
                                System.out.println("Client2 log out");                        
                                stop = true;                
                                break;
                            }
                            else if(req.startsWith("login")){
                                nio.write("you had logged in");
                            }
                            else if(req.startsWith("bet")){
                                //System.out.println(BaccaratEngine.bet(req.substring(4)));
                                nio.write(BaccaratEngine.bet(req.substring(4)));
                            }
                            else if(req.startsWith("deal") && req.length() == 6){
                                String check = BaccaratEngine.checkBetValid(); // to check card deck and user fund
                                if(check.equals("true")){
                                    String deal = req.substring(5).toLowerCase();
                                    //System.out.println(deal);
                                    if(deal.equals("p") || deal.equals("b")){
                                        //initiate card
                                        String responseAfterDeal = BaccaratEngine.initiateCard(deal);
                                        System.out.println(responseAfterDeal);
                                        nio.write(responseAfterDeal);

                                        //response to user decoding
                                        String reqAfterDeal = nio.read().toLowerCase();
                                        System.out.println(reqAfterDeal);
                                        if(reqAfterDeal.contains("draw card")){
                                            nio.write(BaccaratEngine.drawCard(reqAfterDeal));
                                            reqAfterDeal = nio.read().toLowerCase();                                        
                                        }
                                        if(reqAfterDeal.contains("wins") || reqAfterDeal.contains("tie")){                                        
                                            String showFund = BaccaratEngine.checkWin(reqAfterDeal);
                                            System.out.println(showFund);
                                            nio.write(showFund);
                                        }
                                    }else{
                                        nio.write("Please deal on P:Player or B:Banker only");
                                        System.out.println("Client make invalid deal");
                                    }
                                }else if(check.contains("New card deck is draw.")){
                                    nio.write(check);
                                    BaccaratEngine.loadDeck(decksNo);
                                }else{
                                    nio.write(check);
                                }
                            }
                            else if(req.startsWith("adjust")){
                                response = BaccaratEngine.adjFund(req.substring(7));
                                System.out.println("Fund adjusted");
                                nio.write(response);
                            }
                            else{
                                nio.write("invalid input");
                            }                            
                        }                        
                    }
                    else{
                        nio.write("invalid input");
                    }
                } 
            }            
        }
        catch (Exception e){
            e.printStackTrace();
        } 
        finally{
            try {
                nio.close();
                sock.close();

                //System.out.println("Exiting the thread !");

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
