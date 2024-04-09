import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Fish {
    private String name;
    private int pointGain;
    private String imageName;
    private BufferedImage image;
    private int width;
    private int height;
    private int speed;
    private Rectangle hitBox;
    private boolean recentSpawned;
    private int x;
    private int y;

    public Fish (String name, int pointGain, int height, int width, int speed)
    {

        this.name = name;
        this.pointGain = pointGain;
        imageName = "Image/"+name+".png";
        System.out.println(imageName);
        image = readImage();
        this.height = height;
        this.width = width;
        this.speed = speed;
        recentSpawned = true;
        x= 0;
        y = 0;
        hitBox = new Rectangle(x,y,width,height);
    }
    public String getName()
    {
        return name;
    }

    public int getPointGain()
    {
        return pointGain;
    }

    public void recentFalse()
    {
        recentSpawned = false;
    }

    public boolean isRecentSpawned()
    {
        return recentSpawned;
    }

    public void changeHitBox(int x, int y,int width, int height)
    {
        hitBox = new Rectangle(x,y,width/this.width,height/this.height);
        this.x = x;
        this.y = y;
        this.width = width/this.width;
        this.height = height/this.height;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage readImage()
    {
        try{
            return ImageIO.read(new File(imageName));
        }
        catch(IOException e)
        {
            System.out.println("e");
            return null;
        }
    }

    public static ArrayList<Fish> generateFishes(int time)
    {
        ArrayList<Fish> generatedFishes = new ArrayList<>();
        if(time%1000==0)
        {
            generatedFishes.add(new WalkerFish());
        }
        generatedFishes.add(new WalkerFish());
        return generatedFishes;
    }

    public void swim(int width)
    {
        x = width/speed;
    }



}
