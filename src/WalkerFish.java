import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WalkerFish extends Fish{
    private String imageName2;
    private String imageName3;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage returnedImage;
    private int animationValue;
    private int increment;

    public WalkerFish()
    {
        super("walker_fish",1000,6,7,30);
        imageName2 =  "Images/fish_walker2.png";
        imageName3 =  "Images/fish_walker2.png";
        image2 = readImage(imageName2);
        image3 = readImage(imageName3);
        returnedImage = super.getImage();
        animationValue = 1;
        increment = 1;
    }

    public BufferedImage getImage()
    {
       return returnedImage;
    }

    public BufferedImage readImage(String imageName)
    {
        try{
            return ImageIO.read(new File(imageName));
        }
        catch(IOException e)
        {
            return null;
        }
    }

    public void swim(int width)
    {
        super.setX(super.getX()-width/super.getSpeed());
        changeHitBox(super.getX(),super.getY());

    }

}
