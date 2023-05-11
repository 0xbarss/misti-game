import java.util.Scanner;
import java.util.ArrayList;

public abstract class Player {
    protected String nickname;
    protected ArrayList<String> hand, storedCards;
    protected int score;

    public Player(String nickname) {
        this.nickname = nickname;
        this.hand = new ArrayList<String>();
        this.storedCards = new ArrayList<String>();
        this.score = 0;
    }

    public String getNickName() {return this.nickname;}
    public String getLevel() {return "";}
    public ArrayList<String> getHand() {return this.hand;}
    public ArrayList<String> getStoredCards() {return this.storedCards;}
    public int getScore() {return this.score;}

    public void addtoHand(String card) {hand.add(card);}

    public void printHand() {
        for (int i=0; i<this.hand.size(); i++) {
            System.out.println((i+1)+"-"+this.hand.get(i));
        }
    }

    public void updateScore(ArrayList<String> cards, Score s) {
        int score  = 0;
        for(String i: cards){
            score += s.getCardPoint(i);
        }
        if(cards.size() == 2) {
            score = score*5;
            System.out.println(this.nickname + " made a misti!");
        }
        else {
            System.out.println(this.nickname + " won the cards!");
        }
        this.score += score;
    }
    
    public void addtoStoredCards(Board b, Score s) {  //takes cards and board as an argument
        updateScore(b.getBoardCards(), s);   //updates the score of the player.
        storedCards.addAll(b.getBoardCards());   //adds the taken cards to storedcards.
        b.clearBoard();              //cleans the board.
    }

    public String play(Scanner sc, Score score, ArrayList<String> boardCards) {
        return "";
    }

}
