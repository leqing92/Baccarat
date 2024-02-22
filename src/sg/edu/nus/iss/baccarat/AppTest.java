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
        BaccaratEngine testEngine1 = new BaccaratEngine();
        assertEquals(expectedOutput, testEngine1.login(input));        
    }

    @Test //tested logined ; bet ; checkBetValid ; adjFund ; create1cardSet ; loadDeck ; shuffledCard ;  
    public void testBetValid() {
        // Assuming user has sufficient funds and remaining cards
        BaccaratEngine testEngine1 = new BaccaratEngine();
        testEngine1.login("testBetValid|1000");
        testEngine1.loadDeck(4);
        testEngine1.bet("500");

        assertEquals("true", testEngine1.checkBetValid());
        
        testEngine1.adjFund("-600");
    
        assertEquals("testBetValid's fund is insufficient, please adjust your fund.\nCurrent fund = 400", testEngine1.checkBetValid());
         
    }

    @Test
    public void testCheckWin() {
        BaccaratEngine testEngine1 = new BaccaratEngine();
        testEngine1.login("testCheckWin|1000");
        testEngine1.loadDeck(4);
        testEngine1.bet("500");        
        testEngine1.initiateCard("b");
        String cardCombination = "P|13.1|10.1|5.1,B|13.1|6";
        
        String result = card.checkCard(cardCombination);
        assertEquals("Banker wins with 6 points (6-card rule)", result);
           
        String betResult = testEngine1.checkWin(result.toLowerCase());
        assertEquals("Player win $250\nTotal fund : $1250", betResult);
    }
    
}

