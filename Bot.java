import java.util.ArrayList;

public class Bot extends Player {
    private int level;
    private ArrayList<String> memory;

    public Bot() {

    }
    
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
