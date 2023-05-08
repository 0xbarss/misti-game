import java.util.ArrayList;

public class Board {
    private ArrayList<String> boardCards;

    public Board() {
        this.boardCards = new ArrayList<String>();
    }

    public ArrayList<String> getBoardCards() {return this.boardCards;}

    public void addCard(String card) {this.boardCards.add(card);}
    public void clearBoard() {this.boardCards.clear();}

    public void checkCards(Player player, Board board, Score score) {
        if (boardCards.size() > 1) {
            if (this.boardCards.get(this.boardCards.size()-1).charAt(1) == 'J' || 
                this.boardCards.get(this.boardCards.size()-1).charAt(1) == this.boardCards.get(this.boardCards.size()-2).charAt(1)) 
            {
                player.addtoStoredCards(board, score);
            }
        }
    }
}
