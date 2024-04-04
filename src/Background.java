import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Background {
    private String name;
    private String imageName;
    private BufferedImage image;

    public Background(String name)
    {
        this.name = name;
        imageName = getFileName(name);
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

    public String getFileName(String name)
    {
        String fileName;
        try{
            fileName = "Image/"+name +".jpg";
            File f = new File(fileName);
            if(!f.exists()) throw new FileNotFoundException();
        }
        catch (FileNotFoundException e)
        {
            fileName = "Image/"+name +".png";
        }
        System.out.println(fileName);
        return fileName;
    }
}
