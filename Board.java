import java.util.ArrayList;

public class Board {
    private ArrayList<String> boardCards;

    public Board() {
        this.boardCards = new ArrayList<String>();
    }

    public ArrayList<String> getBoardCards() {return this.boardCards;}

    public void addCard(String card) {this.boardCards.add(card);}
    public void clearBoard() {this.boardCards.clear();}
}
