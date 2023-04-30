import java.util.ArrayList;

public class Player {
    protected ArrayList<String> hand;
    protected ArrayList<String> storedCards;
    protected int score;

    public Player() {
        this.hand = new ArrayList<String>();
        this.storedCards = new ArrayList<String>();
        this.score = 0;
    }

    public ArrayList<String> getHand() {return this.hand;}
    public ArrayList<String> getStoredCards() {return this.storedCards;}
    public int getScore() {return this.score;}

    public void addCard(String card) {}
    public void addCards(ArrayList<String> cards) {} // storedCards.addAll(cards)
    public void addScore(int score) {this.score += score;}
}
