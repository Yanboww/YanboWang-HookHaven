import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fish {
    private String name;
    private int pointGain;
    private String imageName;
    private BufferedImage image;

    public static final ArrayList<Fish> availableFishes = new ArrayList<>();

    public Fish (String name, int pointGain)
    {
        this.name = name;
        this.pointGain = pointGain;
        imageName = "Images/"+name+".png";
        image = readImage();
    }
    public String getName()
    {
        return name;
    }

    public int getPointGain()
    {
        return pointGain;
    }


    public BufferedImage readImage()
    {
        try{
            return ImageIO.read(new File(imageName));
        }
        catch(IOException e)
        {
            return null;
        }
    }



}
