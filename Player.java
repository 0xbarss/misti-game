import java.util.ArrayList;

public class Player {
    private ArrayList<String> hand;
    private ArrayList<String> storedCards;
    private int score;

    public Player() {

    }

    public void addCard(String card) {}
    public void addCards(ArrayList<String> cards) {} // storedCards.addAll(cards)
    public void addScore(int score) {this.score += score;}
}
