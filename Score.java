import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Score {
    private final char[] suits = {'S', 'C', 'H', 'D'};
    private final char[] ranks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
    private ArrayList<String> cardsValues; //valuable cards specified in points.txt

    public Score(String path) {
        this.cardsValues = new ArrayList<String>();
        this.setCardValues(readFile(path));
    }

    public static ArrayList<String> readFile(String path) {
        Scanner scanner;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            //insert txt path
            scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
            return lines;
        } catch (FileNotFoundException e) {
            //System.out.println("The file cannot be found");
        }
        return lines;
    }

    public void setCardValues(ArrayList<String> lines) {
        String card = "";
        String[] values = null;
        Collections.reverse(lines); // the lines taken from the file are reversed
        for (char suit: suits) {
            for (char rank: ranks) {
                this.cardsValues.add(""+suit+rank+" "+1); // the points of the cards are set to 1
            }
        }
        for (String line: lines) {
            values = line.split(" ");
            if (values[0].charAt(0) == '*' && values[0].charAt(1) == '*') { // ** expressions are set
                for (int i=0; i<this.cardsValues.size(); i++) {
                    card = this.cardsValues.get(i);
                    card = card.substring(0, 2) + " " + values[1];
                    this.cardsValues.set(i, card);
                }
            }
            else if (values[0].charAt(0) == '*' && values[0].charAt(1) != '*') { // *? expressions are set
                for (int i=0; i<this.cardsValues.size(); i++) {
                    card = this.cardsValues.get(i);
                    if (card.charAt(1) == values[0].charAt(1)) {
                        card = card.substring(0, 2) + " " + values[1];
                        this.cardsValues.set(i, card);
                    }
                }
            }
            else if (values[0].charAt(0) != '*' && values[0].charAt(1) == '*') { // ?* expressions are set
                for (int i=0; i<this.cardsValues.size(); i++) {
                    card = this.cardsValues.get(i);
                    if (card.charAt(0) == values[0].charAt(0)) {
                        card = card.substring(0, 2) + " " + values[1];
                        this.cardsValues.set(i, card);
                    }
                }
            }
            else {                                                                           // ?? expressions are set
                for (int i=0; i<this.cardsValues.size(); i++) {
                    card = this.cardsValues.get(i);
                    if (card.equals(values[0])) {
                        card = card.substring(0, 2) + " " + values[1];
                        this.cardsValues.set(i, card);
                    }
                }
            }
        }
    }

    public int getCardPoint(String card){
        String valuable_card = "";    //one of cards in cardvalues
        int card_value = 0;    //value of that card
        for(String i: this.cardsValues){
            valuable_card = i.split(" ")[0];
            card_value = Integer.parseInt(i.split(" ")[1]);
            if(card.equals(valuable_card)) return card_value;
        }
        return 1;
    }

    public static void printScores(ArrayList<Player> players) {
        // find the best score
        System.out.println("\nGame Over!");
        int bestScore = 0;
        Player bestPlayer = null;
        for (Player player: players) {
            System.out.println(player.getNickName() + "-" + player.getLevel() + ": " + player.getScore());
            if (player.getScore() > bestScore) {
                bestScore = player.getScore();
                bestPlayer = player;
            }
        }
        System.out.printf("\nThe Winner is %s - %d", bestPlayer.getNickName(), bestPlayer.getScore());
        int capacity = 10;
        String scoreFile = "highest_scores.txt";
        FileWriter writer = null;
        // save scores into scores array
        ArrayList<String> scores = readFile(scoreFile);
        // check if there is new high score and write all scores into file
        try {
            writer = new FileWriter(scoreFile);
            if (scores.size() > 0) {
                int highScore = Integer.parseInt(scores.get(0).split(" ")[1]);
                if (bestScore > highScore) {
                    // remove the old score from file
                    for (int i=0; i<scores.size(); i++) {
                        if (scores.get(i).startsWith(bestPlayer.getNickName()+"-"+bestPlayer.getLevel())) {
                            scores.remove(i);
                            break;
                        }
                    }
                    String text = bestPlayer.getNickName() + "-" + bestPlayer.getLevel() + " " + bestScore;
                    scores.add(0, text);
                    if (scores.size() > capacity) scores.remove(scores.size()-1); // If there are more than 10 scores, remove the last one
                }
            }
            else { // If there is no score in the file, add the score
                String text = bestPlayer.getNickName() + "-" + bestPlayer.getLevel() + " " + bestScore;
                scores.add(0, text);
            }
            for (int i=0; i<scores.size(); i++) {
                writer.write(scores.get(i) + "\n");
            }
            writer.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}