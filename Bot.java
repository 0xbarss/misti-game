import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Bot extends Player {
    private String level;
    private ArrayList<String> memory;

    public Bot(String nickname, String level) {
        super(nickname);
        this.level = level;
        if (this.level.equals("E")) {
            this.memory = new ArrayList<String>();
        }
    }

    public String getLevel() {
        if (this.level.equals("N")) return "Novice";
        if (this.level.equals("R")) return "Regular";
        if (this.level.equals("E")) return "Expert";
        return "";
    }
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
        // And the selected suit has been played less until now (<%50)
        int suitCount = 0;
        if (!selected) {
            for (String card: this.hand) {
                currentCardValue = score.getCardPoint(card);
                if (currentCardValue < selectedCardValue) {
                    suitCount = 0;
                    for (String mcard: this.memory) {
                        if (card.charAt(0) == mcard.charAt(0)) suitCount++;
                    }
                    if (suitCount < 7) {
                        selectedCard = card;
                        selectedCardValue = currentCardValue;
                    }
                }
            }
        }
        this.hand.remove(selectedCard);
        return selectedCard;
    }
    @Override
    public String play(Scanner sc, Score score, ArrayList<String> boardCards) {
        String card = "";
        switch (this.level) {
            case "N":
                card = novicePlay();
                break;
            case "R":
                card = regularPlay(score, boardCards);
                break;
            case "E":
                card = expertPlay(score, boardCards);
                break;
        }
        return card;
    }
}
