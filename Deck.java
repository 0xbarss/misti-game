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
    }
    
    public ArrayList<String> getDeck() {return deck;}
    
    private void createDeck() {
        for(char s: suits){
            for(char r: ranks){
                String card = ""+s+r;
                deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
        System.out.println("The cards are shuffled.");
    }

    public void cutDeck() {
        Random r = new Random(System.currentTimeMillis());
        ArrayList<String> newdeck = new ArrayList<String>();
        int size = deck.size();
        int cutIndex = r.nextInt(size-1)+1;  //for setting the point to cut.
        for(int i = 0; i<size; i++){
            newdeck.add(this.deck.get(i<size-cutIndex ? cutIndex+i: i-(size-cutIndex)));
        }
        this.deck = newdeck;  
        System.out.println("The cards are cut.");  
    }

    public void dealCards(ArrayList<Player> players, Board board) {
        shuffleDeck();
        cutDeck();
        if (deck.size() == 52) {
            for (int i=0; i<4; i++) {
                board.addCard(deck.get(deck.size()-1));
                deck.remove(deck.size()-1);
            }
            System.out.println("4 cards are placed on the board.");
        }
        for (int i=0; i<4; i++) {
            for (Player player: players) {
                player.addtoHand(deck.get(deck.size()-1));
                deck.remove(deck.size()-1);
            }
        }
        System.out.println("4 cards are dealt to each player.");
    }
}
