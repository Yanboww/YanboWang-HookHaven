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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
