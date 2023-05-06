import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Number of Players, Points File, Name-Expertise, Verboseness Level
        boolean loop = true;
        Score score;
        String pointsPath;
        int numberOfPlayers;
        boolean verbosenessMode; // activate verboseness mode
        ArrayList<Player> players;
        String[] playerInfo;
        try {
            numberOfPlayers = Integer.parseInt(args[0]);
            pointsPath = args[1];
            verbosenessMode = Boolean.parseBoolean(args[args.length-1]);
            players = new ArrayList<Player>(numberOfPlayers);
            score = new Score(pointsPath);
            for (int i=2; i<2+numberOfPlayers; i++) {
                playerInfo = args[i].split("-");
                if (playerInfo[1].equalsIgnoreCase("Human")) {
                    players.add(new Human(playerInfo[0]));
                }
                else {
                    players.add(new Bot(playerInfo[0], Integer.parseInt(playerInfo[1])));
                }
            }
        } 
        catch (Exception e) {
            System.err.println("Invalid input, please make sure the arguments have been written in the correct order!");
            loop = false;
        }

        while (loop) {

        }
    }
}