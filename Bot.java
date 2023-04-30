import java.util.ArrayList;

public class Bot extends Player {
    private int level; // | 0->Novice | 1-> Regular | 2->Expert |
    private ArrayList<String> memory;

    public Bot(int level) {
        super();
        this.level = level;
        this.memory = new ArrayList<String>();
    }

    public int getLevel() {return this.level;}
    
    private String novicePlay() {}
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
