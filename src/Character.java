import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class Character {
    private int score;

    private int maxScore;
    private ArrayList<Fish> caughtFishTypes;

    private int x;
    private int y;

    private int dimensionX;

    public Character(int y)
    {
        score = 0;
        caughtFishTypes = new ArrayList<>();
        maxScore = retrievePlayerData();
        x = 0;
        this.y = y;
        dimensionX = 500;
    }

    public void addPoints(Fish f)
    {
        score += f.getPointGain();
        maxScore = Math.max(score,maxScore);
    }


    public void saveGame()
    {
        File f;
        FileWriter fw;
        try{
            f = new File("Data/save_file");
            fw = new FileWriter(f);
            fw.write(maxScore+"\n_____");
            if(!caughtFishTypes.isEmpty())
            {
                for(Fish caughtFish : caughtFishTypes)
                {
                    fw.write(caughtFish.getName());
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
                    caughtFishTypes.add(new Fish(s.nextLine(),0));
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveRight()
    {
        x+=dimensionX/10;
        if(x>=dimensionX-dimensionX/10) x = dimensionX-dimensionX/10;

    }

    public void moveLeft()
    {
        x-=dimensionX/10;
        if(x<0) x = 0;
    }
}
