import java.util.ArrayList;

public class Board {
    private static int handCount = 0;
    private static int moveCount = 0;
    private static int playedCardsCount = 0;
    private static String handString = "";
    private int numberOfPlayers;
    private ArrayList<String> boardCards;
    private boolean verbosenessMode;

    public Board(int numberOfPlayers, boolean verbosenessMode) {
        this.numberOfPlayers = numberOfPlayers;
        this.verbosenessMode = verbosenessMode;
        this.boardCards = new ArrayList<String>();
    }

    public String getHandString() {return handString;}
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

    public void checkCards(Player player, Board board, Score score) {
        playedCardsCount++;
        // Write the played card to string
        if (playedCardsCount % this.numberOfPlayers == 1 && this.verbosenessMode) {
            moveCount++;
            handString += "\n" + moveCount + ".";
        }
        // If there are at least 2 cards on the board, check matches
        if (boardCards.size() > 1) {
            if (this.boardCards.get(this.boardCards.size()-1).charAt(1) == 'J' ||
                    this.boardCards.get(this.boardCards.size()-1).charAt(1) == this.boardCards.get(this.boardCards.size()-2).charAt(1))
            {
                if (this.verbosenessMode) handString += " " + this.boardCards.get(this.boardCards.size()-1) + "!";
                player.addtoStoredCards(board, score);
            }
            else if (boardCards.size() > 0 && this.verbosenessMode) handString += " " + this.boardCards.get(this.boardCards.size()-1);
        }
        else if (boardCards.size() > 0 && this.verbosenessMode) handString += " " + this.boardCards.get(this.boardCards.size()-1);
    }

    public void writeHand(ArrayList<Player> players) {
        handCount++;
        moveCount = 0;
        handString = "\nHand " + handCount;
        Player player = null;
        if (this.verbosenessMode) {
            for (int i=0; i<players.size(); i++) {
                player = players.get(i);
                handString += "; Player-" + (i+1) + ": " + player.getHand().toString() + " Score: " + player.getScore();
            }
        }
        else {
            for (int i=0; i<players.size(); i++) {
                player = players.get(i);
                handString += "; Player-" + (i+1) + ": " + " Score: " + player.getScore();
            }
        }
    }
}
