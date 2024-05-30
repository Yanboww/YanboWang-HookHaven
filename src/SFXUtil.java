import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class SFXUtil implements ActionListener {
    private final BufferedImage image1;
    private final BufferedImage image2;
    private final BufferedImage image3;
    private final BufferedImage image4;
    private BufferedImage returnedImage;

    private int animationValue;
    private Timer t;
    private boolean running;

    public SFXUtil(String name)
    {
        image1 = readImage("Image/"+name+"1.png");
        image2 = readImage("Image/"+name+"2.png");
        image3 = readImage("Image/"+name+"3.png");
        image4 = readImage("Image/"+name+"4.png");
        returnedImage = image1;
        t = new Timer(200, this);
        animationValue = 1;
        t.start();
        running = true;
    }

    public BufferedImage getImage() {
        return returnedImage;
    }
    public void actionPerformed(ActionEvent e)
    {
        if(animationValue == 1)
        {
            animationValue ++;
            returnedImage = image1;
        }
        else if (animationValue == 2)
        {
            animationValue ++;
            returnedImage = image2;

        }
        else if(animationValue == 3)
        {
            animationValue ++;
            returnedImage = image3;
        }
        else if(animationValue == 4)
        {
            returnedImage = image4;
            t.stop();
            running = false;
        }
    }

    public boolean isRunning(){return running;}

    public BufferedImage readImage(String imageName)
    {
        try{
            return ImageIO.read(new File(imageName));
        }
        catch(IOException e)
        {
            if(e.getClass().getName().equals("FileNotFoundException"))
            {
                readImage("Image/blankFrame.png");
            }
            return null;
        }
    }


}
