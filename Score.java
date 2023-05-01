import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Score {
    private ArrayList<String> cardsValues; //valuable cards specified in points.txt

    public Score(String path) {
        this.cardsValues = new ArrayList<String>();
        this.setCardValues(readFile(path));
    }


    public static ArrayList<String> readFile(String path) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            //insert txt path
            Scanner scanner = new Scanner(new File("points.txt"));
            int numLines = 0;
            while (scanner.hasNextLine()) {
                numLines++;
                scanner.nextLine();
            }
            scanner.close();
            //insert txt path
            scanner = new Scanner(new File("points.txt"));
            int index = 0;
            while (scanner.hasNextLine()) {
                lines.add(Arrays.toString(scanner.nextLine().split("\n")));
                index++;
            }
            scanner.close();
            //Print the points
            for (String line : lines) {
                System.out.println(line);
            }
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }


    public void setCardValues(ArrayList<String> points)   {


    }
    
    public int getCardPoint(String card){
        String valuable_card = "";    //one of cards in cardvalues
        int card_value = 0;    //value of that card
        for(String i: this.cardsValues){  
            valuable_card = i.split(" ")[0];
            card_value = Integer.parseInt(i.split(" ")[1]);
            if(card.equals(valuable_card)) return card_value;  
        }

    }

    public static void printScores(ArrayList<Player> players) {}
    public static int calculateScore(ArrayList<String> cards) {}

}
