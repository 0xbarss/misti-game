import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player {
    private String nickname;

    public Human(String nickname) {
        super();
        this.nickname = nickname;
    }

    public String getNickName() {return this.nickname;}

    @Override
    public String play(Score score, ArrayList<String> boardCards) {
        int input = 0;
        String selectedCard = "";
        Scanner sc = new Scanner(System.in);
        // Ask the user to enter a card
        this.printHand();
        while (true) {
            System.out.print("Enter a card: ");
            try {
                input = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
                continue;
            }
            if (input > 0 && input <= this.hand.size()) {break;}
            else {System.out.println("The card does not exist");}
        }
        sc.close();
        // Remove the card from hand and play it
        selectedCard = this.hand.remove(input-1);
        return selectedCard;
    }
}
