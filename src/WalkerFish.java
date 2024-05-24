import javax.imageio.ImageIO;
import javax.net.ssl.ManagerFactoryParameters;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class WalkerFish extends Fish implements ActionListener {
    private String imageName2;
    private String imageName3;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage returnedImage;

    private int animationValue;
    private int increment;
    Timer t;

    public WalkerFish()
    {
        super("walker_fish",1000,6,7,30);
        imageName2 =  "Images/fish_walker2.png";
        imageName3 =  "Images/fish_walker2.png";
        image2 = readImage(imageName2);
        image3 = readImage(imageName3);
        returnedImage = super.getImage();
        t = new Timer(1000,this);
        animationValue = 1;
        increment = 1;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(animationValue == 1)
        {
            animationValue = 2;
            returnedImage = super.getImage();
        }
        else if (animationValue == 2)
        {
            animationValue = 3;
            returnedImage = image2;
        }
        else if(animationValue == 3)
        {
            animationValue = 2;
            returnedImage = image3;
        }
    }

    public BufferedImage getImage() {
        System.out.println("a");
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
