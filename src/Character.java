import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class Character {
    private int score;

    private int maxScore;
    private ArrayList<String> caughtFishTypes;

    private int x;
    private int y;

    private int dimensionX;
    private double percentMap;
    private String imageName;
    private BufferedImage image;

    public Character(int y)
    {
        score = 0;
        caughtFishTypes = new ArrayList<>();
        maxScore = retrievePlayerData();
        x = 0;
        this.y = y;
        dimensionX = 500;
        percentMap = 1;
        imageName = "Image/character.png";
        image = readImage();
    }

    public void addPoints(Fish f)
    {
        score += f.getPointGain();
        maxScore = Math.max(score,maxScore);
        String name = f.getName();
        if(!caughtFishTypes.contains(name))
        {
            caughtFishTypes.add(name);
        }
    }

    public int getScore() {
        return score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public ArrayList<String> getCaughtFishTypes() {
        return caughtFishTypes;
    }

    public void saveGame()
    {
        File f;
        FileWriter fw;
        try{
            f = new File("Data/save_file");
            fw = new FileWriter(f);
            if(!caughtFishTypes.isEmpty())
            {
                fw.write(maxScore+"\n_____");
                for(String caughtFish : caughtFishTypes)
                {
                    fw.write(caughtFish);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("File Save Error Encountered");
        }
    }

    private int retrievePlayerData()
    {
        File f;
        try{
            f = new File("Data/save_file");
            Scanner s = new Scanner(f);
            int start = 0;
            int number =0;
            while(s.hasNextLine())
            {
                if(start == 0) number = Integer.parseInt(s.nextLine());
                String currentLine = s.nextLine();
                if(!currentLine.contains("_"))
                {
                    caughtFishTypes.add(s.nextLine());
                }
                start++;
            }
            return number;
        }
        catch(FileNotFoundException e)
        {
            return score;
        }
    }

    public void setDimensionX(int dimensionX) {
        this.dimensionX = dimensionX;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    public double getPercentMap() {
        return percentMap;
    }

    public void setPercentMap(double percentMap) {
        this.percentMap = percentMap;
    }

    public void moveRight()
    {
        x+=dimensionX/40;
        if(x>=dimensionX-dimensionX/10) x = dimensionX-dimensionX/10;
        setPercentMap((double)x/getDimensionX());

    }

    public void moveLeft()
    {
        x-=dimensionX/40;
        if(x<0) x = 0;
        setPercentMap((double)x/getDimensionX());
    }
    public BufferedImage getImage()
    {
        return image;
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
}
