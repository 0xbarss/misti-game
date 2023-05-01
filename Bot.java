import java.util.Random;
import java.util.ArrayList;

public class Bot extends Player {
    private int level; // | 1->Novice | 2-> Regular | 3->Expert |
    private ArrayList<String> memory;

    public Bot(int level) {
        super();
        this.level = level;
        if (this.level == 2) {
            this.memory = new ArrayList<String>();
        }
    }

    public int getLevel() {return this.level;}
    
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
            if (card.charAt(1) == 'J' && currentCardValue > selectedCardValue) {
                if (currentCardValue + boardCardsValue > 0) {
                    selectedCard = card;
                    selectedCardValue = currentCardValue;
                    selected = true;
                }
            }
            else if ((card.charAt(0) == lastCardOnBoard.charAt(0) || card.charAt(1) == lastCardOnBoard.charAt(1)) && currentCardValue > selectedCardValue) {
                if (currentCardValue + boardCardsValue > 0) {
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
    private String expertPlay() {}
    public String play() {
        String card = "";
        switch (this.level) {
            case 1:
                card = novicePlay();
                break;
            case 2:
                card = regularPlay();
                break;
            case 3:
                card = expertPlay();
                break;
        }
        return card;
    }
}
