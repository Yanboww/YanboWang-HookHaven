import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class WalkerFish extends Fish implements ActionListener {
    private final BufferedImage image2;
    private final BufferedImage image3;
    private BufferedImage returnedImage;

    private int animationValue;
    private Timer t;
    private int increment;

    public WalkerFish()
    {
        super("walker_fish",1000,6,7,30);
        image2 = readImage(  "Image/fish_walker2.png");
        image3 = readImage("Image/fish_walker3.png");
        returnedImage = super.getImage();
        t = new Timer(200, this);
        animationValue = 1;
        increment = 1;
        t.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        if(animationValue == 1)
        {
            increment = 1;
            animationValue += increment;
            returnedImage = super.getImage();
        }
        else if (animationValue == 2)
        {
            animationValue += increment;
            returnedImage = image2;

        }
        else if(animationValue == 3)
        {
            increment = -1;
            animationValue +=increment;
            returnedImage = image3;
        }
    }

    public BufferedImage getImage() {
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
