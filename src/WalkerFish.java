import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WalkerFish extends Fish{
    private String imageName2;
    private String imageName3;
    private BufferedImage image2;
    private BufferedImage image3;

    public WalkerFish()
    {
        super("fish_walker1",200,6,7,10);
        imageName2 =  "Images/fish_walker2.png";
        imageName3 =  "Images/fish_walker2.png";
        image2 = readImage(imageName2);
        image3 = readImage(imageName3);
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


}
