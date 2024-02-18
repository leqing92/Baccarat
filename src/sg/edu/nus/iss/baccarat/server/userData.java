package sg.edu.nus.iss.baccarat.server;

import java.io.File;
import java.util.List;

public class userData {
    
    private String userName;
    private String fund;
    private File userFile;
    private String bet;
    private String deal; //in lowercase liao
    private List<String> remainingCards;
    private String cardSet;
    private List<String> bCard;
    private List<String> pCard;
    private List<String> statisticWin;  

    //getter  
    public String getUserName() {
        return userName;
    }    
    public String getFund() {
        return fund;
    }    
    public File getUserFile() {
        return userFile;
    }
    public String getBet() {
        return bet;
    }    
    public String getDeal() {
        return deal;
    }        
    public List<String> getRemainingCards() {
        return remainingCards;
    }    
    public String getCardSet() {
        return cardSet;
    }    
    public List<String> getbCard() {
        return bCard;
    }    
    public List<String> getpCard() {
        return pCard;
    }    
    public List<String> getStatisticWin() {
        return statisticWin;
    }

    //setter
    public void setUserName(String userName) {
        this.userName = userName;
    }    
    public void setFund(String totalBet) {
        this.fund = totalBet;
    }
    public void setUserFile(File userFile) {
        this.userFile = userFile;
    }
    public void setBet(String bet) {
        this.bet = bet;
    }
    public void setDeal(String deal) {
        this.deal = deal;
    }    
    public void setRemainingCards(List<String> remainingCards) {
        this.remainingCards = remainingCards;
    }    
    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }
    public void setbCard(List<String> bCard) {
        this.bCard = bCard;
    }
    public void setpCard(List<String> pCard) {
        this.pCard = pCard;
    }
    public void setStatisticWin(List<String> statisticWin) {
        this.statisticWin = statisticWin;
    }
}
    