import java.awt.Rectangle;
import java.util.ArrayList;
public class Button {
    private String name;
    private Rectangle button;
    private int x;
    private int y;

    public Button(String name, int x, int y)
    {
        this.name = name;
        this.x =x;
        this.y=y;
        button = new Rectangle(x,y,200,400);
    }

    public String getName() {
        return name;
    }

    public Rectangle getButton() {
        return button;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static ArrayList<Button> getButtons(Background currentBG)
    {
        ArrayList<Button> pageButtons = new ArrayList<>();
        String backgroundName = currentBG.getName();
        if(backgroundName.contains("menu"))
        {
            pageButtons.add(new Button("Play",200,400));
            pageButtons.add(new Button("Help",200,500));
            pageButtons.add(new Button("Quit",200,600));
        }
        return pageButtons;
    }
}
