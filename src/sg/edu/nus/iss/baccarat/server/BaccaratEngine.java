package sg.edu.nus.iss.baccarat.server;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BaccaratEngine {

    private userData user = new userData();
    private File cardFile = new File("1cardSet.db");
    private File remainingCardsFile = new File("cards.db");    
    private final int ELEMENTS_PER_ROW = 6;
    
    public String login(String input){ //input: uSeRnAmE|+ve int
        String output = "";

        String[] inputSplitted = input.split("\\|");
        String userName = inputSplitted[0].trim();        
        List <String> fund = new ArrayList<>();
        fund.add(inputSplitted[1].trim());
        
        String userFilepath = userName + ".db";
        File userFile = new File(userFilepath);
        user.setUserName(userName);
        user.setUserFile(userFile);

        if((!userFile.exists()) && !(fund.get(0).contains("-"))){
            writeFile(userFile, fund);            
            user.setFund(fund.get(0));
            System.out.println(userFilepath + " is created.");
            output = "Hi, " + user.getUserName() + ". You have $" + user.getFund() + " in your account.";            
        }
        else if(!(fund.get(0).contains("-"))){
            List<String> tempBet = loadFile(userFile);
            int totalBetinInt = Integer.parseInt(fund.get(0)) + Integer.parseInt(tempBet.get(0));
            
            List <String> totalFund = new ArrayList<>();
            totalFund.add(Integer.toString(totalBetinInt));
            
            writeFile(userFile, totalFund);            
            user.setFund(totalFund.get(0));
            System.out.println(userName + " is login.");
            output = "Hi, " + user.getUserName() + ". You have $" + user.getFund() + " in your account.";;
        }
        else{
            output = "Hi," + user.getUserName() + ". The initial fund cannot be negative value.";
        }      
        return output;
    }

    public String bet(String bet){//input: "b" or "p"
        String output;
        int betInInt = Integer.parseInt(bet);
        // if(!user.getTotalBet().isEmpty()){
        int totalFundinInt = Integer.parseInt(user.getFund());
        // }
        // else{
        //     System.out.println("Please login first!");
        // }
        if(totalFundinInt < betInInt){
            output = "The bet exceeded your fund";
            System.out.println("User's bet more than fund");            
        }else{
            System.out.println("bet accepted");
            output = "Please choose to deal to Player or Banker";
            user.setBet(bet);
        }
        return output;
    }
        
    public String checkBetValid (){
        String betValid = "checking valid bet or not; seem like it is out of condition";
        if(Integer.parseInt(user.getFund()) >= Integer.parseInt(user.getBet()) && user.getRemainingCards().size() >= 6){   
            betValid = "true";
        }else if (!(Integer.parseInt(user.getFund()) >= Integer.parseInt(user.getBet()))) {
            betValid = user.getUserName() + "'s fund is insufficient, please adjust your fund.\nCurrent fund = " + user.getFund(); 
        }else if (!(user.getRemainingCards().size() >= 6)){
            betValid = "Remaining card deck is less than 6 cards.\nNew card deck is draw.";            
        }
        return betValid;
    }

    public String checkWin(String input){ //client.card.checkCard in lowercase
        int bet = Integer.parseInt(user.getBet());
        System.out.println("bet: " + bet);
        int fund = Integer.parseInt(user.getFund());
        System.out.println("fund: " + fund);
        List <String> fundL = new ArrayList<>();
        List <String> statistic = new ArrayList<>();        
        String deal = user.getDeal();
        System.out.println("deal: " + deal);
        String output = "";
        System.out.println("input: " + input);
        //load game_history.csv file
        user.setStatisticWin(loadCSVFile());
        statistic = user.getStatisticWin();

        //tie case
        if(input.equals("tie")){
            output = "The game is a tie." + "\nTotal fund : $" + fund;
            statistic.add("D");
        }
        //Player hand win case
        if(input.contains("player")){
            statistic.add("P");
            if(deal.equals("p")){
                fund += bet;
                output = "Player win $" + bet + "\nTotal fund : $" + fund;
            }
            else if(deal.equals("b")){
                fund -= bet;
            output = "Player lose $" + bet + "\nTotal fund : $" + fund;
            }
        } 
        //Banker hand win case
        if(input.contains("banker")){
            statistic.add("B");
            if(deal.equals("p")){
                fund -= bet;
                output = "Player lose $" + bet + "\nTotal fund : $" + fund;
            }            
            else if(input.contains("6-card rule")  && deal.equals("b")){
                fund += bet/2;            
                output = "Player win $" + bet / 2 + "\nTotal fund : $" + fund;
            }
            else if(deal.equals("b")){
                fund += bet;
                output = "Player win $" + bet + "\nTotal fund : $" + fund;
            }
        }        
        
        user.setStatisticWin(statistic);
        writeCSVFile(statistic);
        System.out.println("new fund: " + fund);
        user.setFund(Integer.toString(fund));
        fundL.add(user.getFund()); //as writefile required a List<String>
        writeFile(user.getUserFile(), fundL);
        System.out.println("output: " + output);
        return output;
    }

    public String adjFund(String input){ //+ int
        int inputInInt = Integer.parseInt(input);
        int totalFundinInt = Integer.parseInt(user.getFund());
        String output = "";
        if((totalFundinInt + inputInInt) < 0){
            output = "Cannot withdraw amount more than the fund you have. Your fund = " + user.getFund();
        }else{
            user.setFund((Integer.toString(totalFundinInt + inputInInt)));
            output = "Your new fund = " + user.getFund();

            //upadate to file
            List <String> totalFund = new ArrayList<>();
            totalFund.add(user.getFund());            
            writeFile(user.getUserFile(), totalFund); 
        }
        return output;
    }    
    
    public List<String> create1cardSet(){
        List<String> cardSet = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            for (int j = 1; j <= 4; j++) {
                cardSet.add(i + "." + j);
            }
        }
        writeFile(cardFile, cardSet);
        return cardSet;
    }

    public List<String> loadDeck(int decksSet){ //2nd args at serverApp 
        create1cardSet();
        List <String> deck = loadFile(cardFile);
        List <String> fullDeck = shuffleCard(deck, decksSet);              
        writeFile(remainingCardsFile, fullDeck);

        user.setRemainingCards(fullDeck);
        return fullDeck;
    }
    
    private List <String> shuffleCard(List<String> array, int decksSet)
    {
        int index;
        String temp;
        List <String> fullDeck = new ArrayList<>();
        
        Random random = new Random();
        
        for(int i = 0; i < decksSet; i++){
            fullDeck.addAll(array);
        }
        
        for (int i = fullDeck.size() - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i) 
            {   //i=size is -1, so i + 1 to make sure last position is possible also
                index = random.nextInt(i + 1); 
                temp = fullDeck.get(index) ;
                fullDeck.set(index, fullDeck.get(i));
                fullDeck.set(i, temp);
            }
        }
        return fullDeck;
    }

    public String initiateCard(String deal){
        List <String> fullDeck = user.getRemainingCards();
        List <String> bCard = new ArrayList<>();
        List <String> pCard = new ArrayList<>();
        String initialCard = "";
                
        pCard.add(fullDeck.remove(0));
        pCard.add(fullDeck.remove(1));
        user.setpCard(pCard); 
               
        bCard.add(fullDeck.remove(0));        
        bCard.add(fullDeck.remove(1)); 
        user.setbCard(bCard);

        user.setRemainingCards(fullDeck);
        writeFile(remainingCardsFile, fullDeck);
        
        initialCard = "P|" + String.join("|", pCard) + ",B|" + String.join("|", bCard);
        //System.out.println(initialCard);

        user.setCardSet(initialCard);
        user.setDeal(deal); //already checked input in clientHandler 

        return initialCard;
    }

    public String drawCard(String input){
        //get card deck and card combination
        List <String> fullDeck = user.getRemainingCards();
        List <String> bCard = new ArrayList<>();
        List <String> pCard = new ArrayList<>();
        pCard = user.getpCard();
        bCard = user.getbCard();

        String newCardCombination = "";

        //draw new card
        if(input.contains("player")){
            pCard.add(fullDeck.remove(0));
            user.setpCard(pCard);
        }
        if(input.contains("banker")){
            bCard.add(fullDeck.remove(0));
            user.setpCard(bCard);
        }

        //update card deck
        user.setRemainingCards(fullDeck);
        writeFile(remainingCardsFile, fullDeck);

        newCardCombination = "P|" + String.join("|", pCard) + ",B|" + String.join("|", bCard);
        System.out.println(newCardCombination);
        user.setCardSet(newCardCombination);

        return newCardCombination;
    }
    
    private List<String> loadFile(File filepath) {
        List <String> input = new ArrayList<>();
        
        //try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)))){
            try(BufferedReader br = new BufferedReader(new FileReader(filepath))){                       
                String line;           
                
                while ((line = br.readLine()) != null){
                    input.add(line);
                }            
                
            }catch(FileNotFoundException e){
                System.out.println("original 52 card file (server/card.db) not found");
            }catch(IOException e){
                System.out.println("IOException");
            }
            return input;
        }
        
    private void writeFile (File file, List <String> array){        
        
        if(!file.exists()){
            try {
                file.createNewFile();
                //System.out.println("File created: " + file.getAbsolutePath());
            } catch (IOException e) { //if the file already exists
                // TODO Auto-generated catch block
                e.printStackTrace();
            }            
        }
        
        //FileWriter suitable for string ; fileoutputstream suitable for all
        //try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String item : array) { 
                bw.write(item);
                bw.newLine();
            }
            //System.out.println("Data written to " + file.getName() + " successfully.");
            //bw.close(); //not required in try-with-resources as try-with-resources statement automatically closes the resources that are declared within its parentheses  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private List<String> loadCSVFile() {
        File gameHistoryFile = new File("game_history.csv");

        List <String> input = new ArrayList<>();            
    
        try (BufferedReader br = new BufferedReader(new FileReader(gameHistoryFile))) {
            String line;
    
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                input.addAll(Arrays.asList(elements));
            }                    
            
        }catch(FileNotFoundException e){
            System.out.println("csv file (game_history.csv) not found");
        }catch(IOException e){
            System.out.println("IOException");
        }
        return input;
    }
    
    private void writeCSVFile (List <String> array){        
        File gameHistoryFile = new File("game_history.csv");

        if(!gameHistoryFile.exists()){
            try {
                gameHistoryFile.createNewFile();
                //System.out.println("File created: " + gameHistoryFile.getAbsolutePath());
            } catch (IOException e) { 
                // TODO Auto-generated catch block
                e.printStackTrace();
            }      
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(gameHistoryFile))) {
            int count = 0; // Counter to keep track of elements written

            for (int i = 0; i < array.size(); i++) {
                if (count > 0) {
                    bw.write(","); // Add a comma if not the first element
                }
                bw.write(array.get(i));
                count++;
    
                if (count == ELEMENTS_PER_ROW) {
                    bw.newLine(); // Add a new line after every ELEMENTS_PER_ROW elements
                    count = 0; // Reset the counter for the next row
                }
            }
            //System.out.println(array + "written to " + gameHistoryFile.getName() + " successfully.");
            //bw.close(); //not required in try-with-resources as try-with-resources statement automatically closes the resources that are declared within its parentheses  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
        