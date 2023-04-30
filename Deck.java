import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private final char[] suits = {'S', 'C', 'H', 'D'};
    private final char[] ranks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
    private ArrayList<String> deck;

    public Deck() {
        deck = new ArrayList<String>();
        createDeck();
        Collections.shuffle(deck);
        cutDeck();
    }
    
    public void createDeck() {
        for(char s: suits){
            for(char r: ranks){
                String card = ""+s+r;
                deck.add(card);
            }
        }
    }
    // Collections.shuffle() - Shuffle
    private void cutDeck() {
        Random r = new Random(System.currentTimeMillis());
        ArrayList<String> newdeck = new ArrayList<String>();
        int cutIndex = r.nextInt(51)+1;  //for setting the point to cut.
        System.out.println(cutIndex);
        for(int i = 0; i<52; i++){
            newdeck.add(this.deck.get(i<52-cutIndex ? cutIndex+i: i-(52-cutIndex)));
        }
        this.deck = newdeck;    
    }

    public void dealCards(ArrayList<Player> players) {}
}
