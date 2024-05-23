import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class Character implements ActionListener {
    private int score;

    private int maxScore;
    private ArrayList<String> caughtFishTypes;

    private int x;
    private int y;

    private int dimensionX;
    private double percentMap;
    private String imageName;
    private BufferedImage image;
    private BufferedImage hook;
    private Rectangle fishingLine;
    Timer t;
    private int screenHeight;
    private boolean isRetracting;
    private boolean caughtAlready;
    private int hookDropped;
    private int fishCaught;
    private int pointChange;
    private String name;

    public Character(int y)
    {
        name = "";
        score = 0;
        caughtFishTypes = new ArrayList<>();
        screenHeight = 500;
        maxScore = retrievePlayerData();
        x = 0;
        this.y = y;
        dimensionX = 500;
        percentMap = 1;
        imageName = "Image/character.png";
        hook = readImage("Image/hook.png");
        image = readImage();
        isRetracting = false;
        caughtAlready = false;
        fishingLine = new Rectangle(5,100,x + dimensionX/10,0);
        t = new Timer(100,this);
        hookDropped = 0;
        fishCaught = 0;
        pointChange = 0;
    }

    public void setName(String n)
    {
        name = n;
    }

    public String getName()
    {
        return name;
    }

    public void startAnimTimer()
    {
        t.start();
    }
    public void incrementHookDrop()
    {
        hookDropped++;
    }
    public int getHookDropped()
    {
        return hookDropped;
    }
    public int getFishCaught()
    {
        return fishCaught;
    }

    public void clearStat()
    {
        hookDropped = 0;
        fishCaught = 0;
        score = 0;

    }

    public void setFishingLine(int x, int y, int width, int length)
    {
        fishingLine = new Rectangle(x,y,width,length);
    }

    public int getFishingLineX()
    {
        return (int)fishingLine.getX();
    }
    public int getFishingLineY()
    {
        return (int)fishingLine.getY();
    }
    public int getFishingLineW()
    {
        return (int)fishingLine.getWidth();
    }
    public int getFishingLineH()
    {
        return (int)fishingLine.getHeight();
    }

    public int addPoints(Fish f)
    {
        int pointChange = f.getPointGain();
        score += pointChange;
        if(score <= 0){
            pointChange = (score - pointChange)*-1;
            score = 0;
        }
        maxScore = Math.max(score,maxScore);
        String name = f.getName();
        if(!caughtFishTypes.contains(name))
        {
            caughtFishTypes.add(name);
        }
        return pointChange;
    }
    public int getPointChange()
    {
        return pointChange;
    }

    public void resetPointChange()
    {
        pointChange = 0;
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
            fw.write(name + "\n");
            fw.write(Integer.toString(maxScore));
            if(!caughtFishTypes.isEmpty())
            {
                fw.write("\n++++");
                for(String caughtFish : caughtFishTypes)
                {
                    fw.write("\n" + caughtFish);
                }
                fw.close();
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
                if(start == 1){
                    number = Integer.parseInt(s.nextLine());
                    start++;
                    continue;
                }
                else if (start == 0)
                {
                    name = s.nextLine();
                    start++;
                    continue;
                }
                String currentLine = s.nextLine();
                if(!currentLine.contains("+"))
                {
                    caughtFishTypes.add(currentLine);
                }
            }
            return number;
        }
        catch(FileNotFoundException e)
        {
            return score;
        }
    }

    public void clearData()
    {
        caughtFishTypes.clear();
        maxScore = 0;
        score = 0;
        File f;
        FileWriter fw;
        try{
            f = new File("save_file");
            fw = new FileWriter(f);
            fw.write("");
        }
        catch(IOException e)
        {
            System.out.println("File is not found!");
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
    public BufferedImage getHook(){return hook;}
    public BufferedImage readImage()
    {
        try{
            return ImageIO.read(new File(imageName));
        }
        catch(IOException e)
        {
            System.out.println("error");
            return null;
        }
    }

    public BufferedImage readImage(String imageName)
    {
        try{
            return ImageIO.read(new File(imageName));
        }
        catch(IOException e)
        {
            System.out.println("error");
            return null;
        }
    }
    public void dropLine(int height)
    {
        screenHeight = height;
        t.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        if(screenHeight <= getFishingLineH()+getFishingLineY()){
            setFishingLine(getFishingLineX(),getFishingLineY(),getFishingLineW(),getFishingLineH()-dimensionX/20);
            isRetracting = true;
        }
        else if(isRetracting || caughtAlready)
        {
           if(getFishingLineY()+getFishingLineH()>getY()+getY()/3)setFishingLine(getFishingLineX(),getFishingLineY(),getFishingLineW(),getFishingLineH()-dimensionX/20);
           else {
               isRetracting = false;
               caughtAlready = false;
               incrementHookDrop();
               t.stop();
           }
        }
        else{
            setFishingLine(getFishingLineX(),getFishingLineY(),getFishingLineW(),getFishingLineH()+dimensionX/20);
        }
        Point polePoint = new Point(getFishingLineX(),getFishingLineY()+getFishingLineH());
        ArrayList<Fish> currentFishes = DrawPanel.getCurrentFishes();
        for(int i = 0; i < currentFishes.size(); i++)
        {
            Rectangle hitBox = currentFishes.get(i).getHitBox();
            if(hitBox.contains(polePoint) && !caughtAlready && !isRetracting){
                pointChange = addPoints(currentFishes.get(i));
                fishCaught++;
                System.out.println(getScore());
                System.out.println(getCaughtFishTypes());
                currentFishes.remove(i);
                i--;
                caughtAlready = true;
            }
        }
    }

}
