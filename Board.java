import java.util.ArrayList;

public class Board {
    private ArrayList<String> boardCards;

    public Board() {
        this.boardCards = new ArrayList<String>();
    }

    public ArrayList<String> getBoardCards() {return this.boardCards;}
    public String getLastCard() {
        if (this.boardCards.size() > 0) return this.boardCards.get(this.boardCards.size()-1);
        return "";
    }

    public void addCard(String card) {
        Bot.addToMemory(card);
        this.boardCards.add(card);
    }
    public void clearBoard() {this.boardCards.clear();}

    public boolean checkCards(Player player, Board board, Score score) {
        // If there are at least 2 cards on the board, check matches
        if (boardCards.size() > 1) {
            if (this.boardCards.get(this.boardCards.size()-1).charAt(1) == 'J' ||
                this.boardCards.get(this.boardCards.size()-1).charAt(1) == this.boardCards.get(this.boardCards.size()-2).charAt(1))
            {
                player.addtoStoredCards(board, score);
                return true;
            }
        }
        return false;
    }
}
