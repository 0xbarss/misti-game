import java.util.ArrayList;

public class Board {
    private ArrayList<String> boardCards;

    public Board() {

    }

    public void addCard(String card) {this.boardCards.add(card);}
    public void clearBoard() {this.boardCards.clear();}
}
