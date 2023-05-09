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
        String selectedCard = this.hand.get(0);
        int selectedCardValue = score.getCardPoint(selectedCard);
        // Calculate the total value of the cards on the board
        int boardCardsValue = 0;
        for (String card: boardCards) {
            boardCardsValue += score.getCardPoint(card);
        }
        // If there is at least 1 card on the board
        if (boardCards.size() > 1) {
            int currentCardValue = 0;
            boolean selected = false;
            String lastCardOnBoard = boardCards.get(boardCards.size()-1);
            // Select a card in the hand if there is any valuable and suitable card to play
            for (String card: this.hand) {
                currentCardValue = score.getCardPoint(card);
                // Play J if it exists and has more points than others
                if (card.charAt(1) == 'J' && currentCardValue > selectedCardValue) {
                    // Select the card if the total value of the cards on the board is positive
                    if (currentCardValue + boardCardsValue > 0) { 
                        selectedCard = card;
                        selectedCardValue = currentCardValue;
                        selected = true;
                    }
                }
                // Play a suitable card if it has more points than others
                else if (card.charAt(1) == lastCardOnBoard.charAt(1) && currentCardValue > selectedCardValue) {
                    // Select the card if the total value of the cards on the board is positive
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
        }
        else {
            // Select a random card in the hand except for J
            // If all of the cards are J, select first card
            selectedCard = this.hand.get(0);
            for (String card: this.hand) {
                if (card.charAt(1) != 'J') {
                    selectedCard = card;
                    break;
                }
            }
        }
        this.hand.remove(selectedCard);
        return selectedCard;
    }
    private String expertPlay(Score score, ArrayList<String> boardCards) {
        String selectedCard = this.hand.get(0);
        int selectedCardValue = score.getCardPoint(selectedCard);
        // Calculate the total value of the cards on the board
        int boardCardsValue = 0;
        for (String card: boardCards) {
            boardCardsValue += score.getCardPoint(card);
        }
        // If there is at least 1 card on the board
        if (boardCards.size() > 1) {
            int currentCardValue = 0;
            boolean selected = false;
            String lastCardOnBoard = boardCards.get(boardCards.size()-1);
            // Select a card in the hand if there is any valuable and suitable card to play
            for (String card: this.hand) {
                currentCardValue = score.getCardPoint(card);
                // Play J if it exists and has more points than others
                if (card.charAt(1) == 'J' && currentCardValue > selectedCardValue) {
                    // Select the card if the total value of the cards on the board is positive
                    if (currentCardValue + boardCardsValue > 0) { 
                        selectedCard = card;
                        selectedCardValue = currentCardValue;
                        selected = true;
                    }
                }
                // Play a suitable card if it has more points than others
                else if (card.charAt(1) == lastCardOnBoard.charAt(1) && currentCardValue > selectedCardValue) {
                    // Select the card if the total value of the cards on the board is positive
                    if (currentCardValue + boardCardsValue > 0) {
                        selectedCard = card;
                        selectedCardValue = currentCardValue;
                        selected = true;
                    }
                }
            }
            // Choose the card which has the lowest value if none of the cards is suitable
            // And the card's rank has been played less until now
            int currentRankCount = 0;
            int LowestRankCount = 0;
            if (!selected) {
                for (String card: this.hand) {
                    currentRankCount = 0;
                    for (String mCard: this.memory) {
                        if (mCard.charAt(1) == card.charAt(1)) currentRankCount++;
                    }
                    if (currentRankCount < LowestRankCount) {
                        selectedCard = card;
                        LowestRankCount = currentRankCount;
                    }
                }
            }
        }
        else {
            // Select a random card in the hand except for J
            // If all of the cards are J, select first card
            selectedCard = this.hand.get(0);
            for (String card: this.hand) {
                if (card.charAt(1) != 'J') {
                    selectedCard = card;
                    break;
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
