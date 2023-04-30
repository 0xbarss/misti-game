import java.util.ArrayList;

public class Score {
    private ArrayList<String> cardsValues = new ArrayList<String>(); //valuable cards specified in points.txt

    public static String[] readFile() {}
    public int  getCardPoint(String card){
        String valuable_card  = null;    //one of cards in cardvalues
        int card_value = 0;    //value of that card.
        for(String i: cardsValues){
            valuable_card = i.split(" ")[0];
            card_value = Integer.parseInt(i.split(" ")[1]);
            if(card.equals(valuable_card)) return card_value;  
        }
        return 1;
    }
    public static void printScores(ArrayList<Player> players) {}
    public static int calculateScore(ArrayList<String> cards) {}

}
