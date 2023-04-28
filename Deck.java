import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final char[] suits = {'S', 'C', 'H', 'D'};
    private final char[] ranks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
    private ArrayList<String> cards;

    public Deck() {

    }
    
    public void createDeck() {}
    // Collections.shuffle() - Shuffle
    public void cutDeck() {}
    public void dealCards(ArrayList<Player> players) {}
}
