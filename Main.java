import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Number of Players, Points File, Name-Expertise, Verboseness Level
        boolean loop = true;
        Score score = null;
        String pointsPath;
        int numberOfPlayers;
        boolean verbosenessMode; // activate verboseness mode
        ArrayList<Player> players = null;
        try {
            numberOfPlayers = Integer.parseInt(args[0]);
            pointsPath = args[1];
            verbosenessMode = Boolean.parseBoolean(args[args.length-1]);
            players = new ArrayList<Player>(numberOfPlayers);
            score = new Score(pointsPath);
            for (int i=2; i<args.length-1; i+=2) {
                if (!args[i+1].matches("H|N|R|E")) throw new Exception("");
                if (args[i+1].equals("H")) {
                    players.add(new Human(args[i]));
                }
                else {
                    players.add(new Bot(args[i], args[i+1]));
                }
            }
        } 
        catch (Exception e) {
            System.err.println("Invalid input, please make sure the arguments have been written in the correct order!");
            loop = false;
        }

        boolean deal = true;
        Board board = new Board();
        Deck deck = new Deck();
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            deal = true;
            for (Player player: players) {
                if (player.getHand().size() != 0) {
                    deal=false;
                    break;
                }
            }
            if (deal) {
                if (deck.getDeck().size() == 0) {
                    System.out.println("Game Over!");
                    break;
                }
                deck.dealCards(players, board);
            }
            for (Player player: players) {
                board.addCard(player.play(scanner, score, board.getBoardCards())); // Score does nothing if the player is human, but it had to be defined due to runtime polymorphism
                board.checkCards(player, board, score);
            }
        }
    }
}