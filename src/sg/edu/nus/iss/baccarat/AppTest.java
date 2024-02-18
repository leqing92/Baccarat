package sg.edu.nus.iss.baccarat;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
        String input = "username|100";
        String expectedOutput = "Hi, username. You have $100 in your account.";
        assertEquals(expectedOutput, BaccaratEngine.login(input));        
    }

    @Test
    public void testBetValid() {
        // Assuming user has sufficient funds and remaining cards
        BaccaratEngine.login("LQ|1000");
        BaccaratEngine.loadDeck(4);
        BaccaratEngine.bet("500");

        assertEquals("true", BaccaratEngine.checkBetValid());

    //     // Assuming user doesn't have sufficient funds
         
    //     user.setFund("50");
    //     assertEquals("username's fund is insufficient, please adjust your fund.\nCurrent fund = 50", BaccaratEngine.checkBetValid());

    //     // Assuming remaining cards are less than 6
    //     user.setFund("100");
    //     List<String> emptyDeck = new ArrayList<>();
    //     user.setRemainingCards(emptyDeck);
    //     assertEquals("Remaining card deck is less than 6 cards.\nNew card deck is draw.", BaccaratEngine.checkBetValid());
    }
}

