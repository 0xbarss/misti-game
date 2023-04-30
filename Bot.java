import java.util.Random;
import java.util.ArrayList;

public class Bot extends Player {
    private int level; // | 1->Novice | 2-> Regular | 3->Expert |
    private ArrayList<String> memory;

    public Bot(int level) {
        super();
        this.level = level;
        this.memory = new ArrayList<String>();
    }

    public int getLevel() {return this.level;}
    
    private String novicePlay() {
        Random r = new Random(System.currentTimeMillis());
        int card_index = r.nextInt(this.hand.size());
        String card = this.hand.get(card_index);
        this.hand.remove(card_index);
        return card;
    }
    private String regularPlay() {}
    private String expertPlay() {}
    public String play() {
        String card = "";
        switch (this.level) {
            case 1:
                card = novicePlay();
                break;
            case 2:
                card = regularPlay();
                break;
            case 3:
                card = expertPlay();
                break;
        }
        return card;
    }
}
