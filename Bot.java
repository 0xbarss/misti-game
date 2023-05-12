import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Bot extends Player {
    private String level;
    private static ArrayList<String> memory = new ArrayList<String>();

    public Bot(String nickname, String level) {
        super(nickname);
        this.level = level;
    }

    public static void addToMemory(String card) {memory.add(card);}

    @Override
    public String getLevel() {
        if (this.level.equals("N")) return "Novice";
        if (this.level.equals("R")) return "Regular";
        if (this.level.equals("E")) return "Expert";
        return "";
    }
    
    private String novicePlay() {
        Random r = new Random(System.currentTimeMillis());
        int card_index = r.nextInt(this.hand.size());
        String card = this.hand.get(card_index);
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
                if (card.charAt(1) == 'J') {
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
                    if (card.charAt(1) == 'J') continue;
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
        return selectedCard;
    }

    private String expertPlay(Score score, ArrayList<String> boardCards) {
        // Regular Play
        String selectedCard = regularPlay(score, boardCards);

        // If there is no match
        if (selectedCard.charAt(1) != this.hand.get(this.hand.size()-1).charAt(1) &&
            selectedCard.charAt(1) != 'J') {
            // Choose the card which has the lowest value if none of the cards is suitable
            // And the card's rank has been played more until now
            // The card has not to be J
            String expertCard = null;
            int expertCardValue = 0;
            int currentRankCount = 0;
            int highestRankCount = 0;
            ArrayList<Integer> counts = new ArrayList<Integer>();
            for (String card: this.hand) {
                currentRankCount = 0;
                for (String mCard: memory) {
                    if (mCard.charAt(1) == card.charAt(1)) currentRankCount++;
                }
                counts.add(currentRankCount);
                if (currentRankCount > highestRankCount || highestRankCount == 0) {
                    expertCard = card;
                    highestRankCount = currentRankCount;
                    expertCardValue = score.getCardPoint(expertCard);
                }
            }
            // If there is any equal posibilities, check their card values
            int currentCardValue = 0;
            for (int i=0; i<counts.size(); i++) {
                currentCardValue = score.getCardPoint(this.hand.get(i));
                if (counts.get(i) == highestRankCount) {
                    if (currentCardValue < expertCardValue) {
                        expertCard = this.hand.get(i);
                        expertCardValue = currentCardValue;
                    }
                }
            }
            selectedCard = expertCard;
        }
        return selectedCard;
    }
    
    @Override
    public String play(Scanner sc, Score score, ArrayList<String> boardCards) {
        String card = "";
        switch (this.level) {
            case "N":
                card = novicePlay();
                this.hand.remove(card);
                break;
            case "R":
                card = regularPlay(score, boardCards);
                this.hand.remove(card);
                break;
            case "E":
                card = expertPlay(score, boardCards);
                this.hand.remove(card);
                break;
        }
        System.out.println(this.nickname + " played " + card);
        return card;
    }
}
