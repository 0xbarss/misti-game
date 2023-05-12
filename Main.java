import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Number of Players, Points File, Name-Expertise, Verboseness Level
        boolean loop = true;
        Score score = null;
        String pointsPath;
        int humanCount = 0;
        int numberOfPlayers = 0;
        boolean verbosenessMode = false; // activate verboseness mode
        ArrayList<Player> players = null;
        try {
            numberOfPlayers = Integer.parseInt(args[0]);
            if (args.length != 3+numberOfPlayers*2) throw new Exception("Invalid argument count");
            pointsPath = args[1];
            if (!new File(pointsPath).isFile()) throw new Exception("The score file does not exist in the given path");
            verbosenessMode = Boolean.parseBoolean(args[args.length-1]);
            players = new ArrayList<Player>(numberOfPlayers);
            score = new Score(pointsPath);
            for (int i=2; i<args.length-1; i+=2) {
                if (!args[i+1].matches("H|N|R|E")) throw new Exception("Invalid player level");
                if (args[i+1].equals("H")) {
                    humanCount++;
                    players.add(new Human(args[i]));
                }
                else {
                    players.add(new Bot(args[i], args[i+1]));
                }
                if (humanCount > 1) throw new Exception("You cannot play with more than one human");
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            loop = false;
        }
        if (loop) {
            System.out.println("Welcome to the Mişti game ! You can play the game with 2-4 players. ");
            //System.out.println("Please enter the necessary argument in the given format: ");
            //System.out.println("java Main.java n pointFile.txt name1 H name2 N name3 R name4 E true");
            //System.out.println();
            //System.out.println("Main = Corresponding class name");
            //System.out.println("n = Number of players");
            //System.out.println("pointFile.txt = The name of point file");
            //System.out.println("Name            = Player name");
            //System.out.println("H,N,R,E          = Human , Novice , Regular , Expert");
            //System.out.println("true / false   = Verboseness mode on/off");
            String card = null;
            int count = 0;
            int handCount = 0;
            int moveCount = 0;
            String handString = "";
            boolean deal = true;
            Board board = new Board();
            Deck deck = new Deck();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                deal = true;
                for (Player player: players) {
                    // If players already have cards, do not deal
                    if (player.getHand().size() != 0) {
                        deal=false;
                        break;
                    }
                }
                if (deal) {
                    Player player = null;
                    moveCount = 0;
                    handCount++;
                    // In hand string, replace all Score_<player.nickName> with players' scores
                    for (int i=0; i<players.size(); i++) {
                        player = players.get(i);
                        handString = handString.replace("Score_"+player.getNickName(), String.valueOf(player.getScore()));
                    }
                    // If deck has no cards, finish the game
                    if (deck.getDeck().size() == 0) {
                        Score.printScores(players);
                        break;
                    }
                    // Print the hands
                    System.out.println(handString + "\n");
                    deck.dealCards(players, board);
                    // Create the template of hand string
                    handString = "\nHand %d: ";
                    handString = String.format(handString, handCount);
                    for (int i=0; i<players.size(); i++) {
                        player = players.get(i);
                        if (verbosenessMode) {
                            handString += player.getNickName() + ": " + player.getHand().toString() + " Score Score_" + player.getNickName() + "; ";
                        }
                        else {
                            handString += player.getNickName() + ": " + " Score Score_" + player.getNickName() + "; ";
                        }
                    }
                }
                for (Player player: players) {
                    count++;
                    System.out.println("\nBoard: " + board.getLastCard());
                    card = player.play(scanner, score, board.getBoardCards()); // Score does nothing if the player is human, but it had to be defined due to runtime polymorphism
                    board.addCard(card);
                    boolean won = board.checkCards(player, board, score);
                    if (count % numberOfPlayers == 1) {
                        moveCount++;
                        if (won) handString += "\n" + moveCount + ". " + card + "! ";
                        else handString += "\n" + moveCount + ". " + card + " ";
                    }
                    else {
                        if (won) handString += card + "! ";
                        else handString += card + " "; 
                    }
                }
            }
            scanner.close();
        }
    }
}