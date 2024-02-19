package sg.edu.nus.iss.baccarat.client;

import java.util.ArrayList;
import java.util.List;

public class card {
    public static String checkCard(String cardCombination){
        List<String> pCard = new ArrayList<>();
        List<String> bCard = new ArrayList<>();
        String output = "";
        String[] parts = cardCombination.split(",");
        
        for (String part : parts) {
            String[] cardInfo = part.split("\\|");
            if (cardInfo[0].equals("P")) {
                for(int i = 1; i < cardInfo.length; i++){
                    pCard.add(cardInfo[i]);
                }                
            } else if (cardInfo[0].equals("B")) {
                for(int i = 1; i < cardInfo.length; i++){
                    bCard.add(cardInfo[i]);
                }
            }
        }

        int pCardValue = roundDown(pCard)%10;
        int bCardValue = roundDown(bCard)%10;
        
        if(pCardValue == bCardValue && pCardValue >= 6){
            output = "Tie";
        }
        //natural win cases
        else if(pCardValue > bCardValue && pCardValue >= 8){
            output = "Player wins with " + pCardValue + " points";
        }else if(pCardValue < bCardValue && bCardValue >= 8){
            output = "Banker wins with " + bCardValue + " points";        
        }
        //player & banker card value at 6 & 7
        else if(pCardValue == 7 && bCardValue == 6){
            output = "Player wins with " + pCardValue + " points";
        }else if(bCardValue == 7 && pCardValue == 6){
            output = "Banker wins with " + bCardValue + " points";
        }else if(pCardValue > bCardValue && pCardValue >= 6 && bCard.size() == 3){
            output = "Player wins with " + pCardValue + " points";
        }else if(pCardValue < bCardValue && pCardValue >= 6 && pCard.size() == 3){
            output = "Banker wins with " + pCardValue + " points";
        }
        //either player or banker card value at 6 & 7 only ; either 1 need to draw card
        else if(pCardValue <= 5 && bCardValue <= 5 && pCard.size() == 2 && bCard.size() == 2){
            output = "Draw card for Player and Banker";
        }else if(pCardValue <= 5 && bCardValue >= 6 && pCard.size() == 2){
            output = "Draw card for Player";
        }else if(pCardValue >= 6 && bCardValue <= 5 && bCard.size() == 2){
            output = "Draw card for Banker";
        }
        //after above no need draw card case anymore
        else if(bCardValue == 6 && bCardValue > pCardValue){
            output = "Banker wins with " + bCardValue + " points (6-card rule)";
        }
        else if(pCardValue > bCardValue){
            output = "Player wins with " + pCardValue + " points";
        }else if(pCardValue < bCardValue){
            output = "Banker wins with " + bCardValue + " points";
        }else if(pCardValue == bCardValue){
            output = "Tie";
        }else{
            output = "unhandle case";
        }
        return output;
    }

    public static int roundDown (List<String> card){
        int value = 0;

        for (String eachCard : card){
            if(Double.parseDouble(eachCard) < 10){
                value += (int) Double.parseDouble(eachCard);
            }else{
                value += 0;
            }
        }
        return value;
    }
}
