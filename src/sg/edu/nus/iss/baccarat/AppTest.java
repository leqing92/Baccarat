package sg.edu.nus.iss.baccarat;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import sg.edu.nus.iss.baccarat.client.card;
import sg.edu.nus.iss.baccarat.server.BaccaratEngine;

/**
 * Unit test for simple App.
 */

public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testLogin() {
        String input = "testLogin|100";
        String expectedOutput = "Hi, testLogin. You have $100 in your account.";
        assertEquals(expectedOutput, BaccaratEngine.login(input));        
    }

    @Test //tested logined ; bet ; checkBetValid ; adjFund ; create1cardSet ; loadDeck ; shuffledCard ;  
    public void testBetValid() {
        // Assuming user has sufficient funds and remaining cards
        BaccaratEngine.login("testBetValid|1000");
        BaccaratEngine.loadDeck(4);
        BaccaratEngine.bet("500");

        assertEquals("true", BaccaratEngine.checkBetValid());
        
        BaccaratEngine.adjFund("-600");
    
        assertEquals("testBetValid's fund is insufficient, please adjust your fund.\nCurrent fund = 400", BaccaratEngine.checkBetValid());
         
    }

    @Test
    public void testCheckWin() {
        BaccaratEngine.login("testCheckWin|1000");
        BaccaratEngine.loadDeck(4);
        BaccaratEngine.bet("500");        
        BaccaratEngine.initiateCard("b");
        String cardCombination = "P|13.1|10.1|5.1,B|13.1|6";
        
        String result = card.checkCard(cardCombination);
        assertEquals("Banker wins with 6 points (6-card rule)", result);
           
        String betResult = BaccaratEngine.checkWin(result.toLowerCase());
        assertEquals("Player win $250\nTotal fund : $1250", betResult);
    }
    
}

