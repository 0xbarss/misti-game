import java.util.Random;
import java.util.ArrayList;

public class Bot extends Player {
    private enum Level {NOVICE, REGULAR, EXPERT}
    private Level level;
    private ArrayList<String> memory;

    public Bot(String nickname, int level) {
        super(nickname);
        this.level = Level.values()[level-1];
        if (this.level == Level.EXPERT) {
            this.memory = new ArrayList<String>();
        }
    }

    public Level getLevel() {return this.level;}
    public void addToMemory(String card) {this.memory.add(card);}
    
    private String novicePlay() {
        Random r = new Random(System.currentTimeMillis());
        int card_index = r.nextInt(this.hand.size());
        String card = this.hand.get(card_index);
        this.hand.remove(card_index);
        return card;
    }
    private String regularPlay(Score score, ArrayList<String> boardCards) {
        // Calculate the total value of the cards on the board
        int boardCardsValue = 0;
        for (String card: boardCards) {
            boardCardsValue += score.getCardPoint(card);
        }
        // Select a card in the hand if there is any valuable and suitable card to play
        String lastCardOnBoard = boardCards.get(boardCards.size()-1);
        String selectedCard = this.hand.get(0);
        int selectedCardValue = score.getCardPoint(selectedCard);
        int currentCardValue = 0;
        boolean selected = false;
        for (String card: this.hand) {
            currentCardValue = score.getCardPoint(card);
            if (card.charAt(1) == 'J' && currentCardValue > selectedCardValue) { // Play J if it exists and has more points than others
                if (currentCardValue + boardCardsValue > 0) { // Select the card if the total value of the cards on the board is positive
                    selectedCard = card;
                    selectedCardValue = currentCardValue;
                    selected = true;
                }
            }
            else if (card.charAt(1) == lastCardOnBoard.charAt(1) && currentCardValue > selectedCardValue) { // Play a suitable card if it has more points than others
                if (currentCardValue + boardCardsValue > 0) { // Select the card if the total value of the cards on the board is positive
                    selectedCard = card;
                    selectedCardValue = currentCardValue;
                    selected = true;
                }
            }
        }
        // Choose the card which has the lowest value if none of the cards is suitable
        if (!selected) {
            for (String card: this.hand) {
                currentCardValue = score.getCardPoint(card);
                if (currentCardValue < selectedCardValue) {
                    selectedCard = card;
                    selectedCardValue = currentCardValue;
                }
            }
        }
        this.hand.remove(selectedCard);
        return selectedCard;
    }
    private String expertPlay(Score score, ArrayList<String> boardCards) {
        return "";
    }
    @Override
    public String play(Score score, ArrayList<String> boardCards) {
        String card = "";
        switch (this.level) {
            case NOVICE:
                card = novicePlay();
                break;
            case REGULAR:
                card = regularPlay(score, boardCards);
                break;
            case EXPERT:
                card = expertPlay(score, boardCards);
                break;
        }
        return card;
    }
}
