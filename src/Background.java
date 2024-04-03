import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {
    private String name;
    private String imageName;
    private BufferedImage image;

    public Background(String name)
    {
        this.name = name;
        imageName = "Image/"+name+".jpg";
        this.image = readImage();
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
    }

    public BufferedImage getImage() {
        return image;
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
