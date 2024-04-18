import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;


public class Fish{
    private String name;
    private int pointGain;
    private String imageName;
    private BufferedImage image;
    private int width;
    private int height;
    private int speed;
    private Rectangle hitBox;
    private boolean recentSpawned;
    private int x;
    private int y;
    private double mapPercent;
    private int dimensionX;
    private int dimensionY;
    public Fish (String name, int pointGain, int height, int width, int speed)
    {

        this.name = name;
        this.pointGain = pointGain;
        imageName = "Image/"+name+".png";
        System.out.println(imageName);
        image = readImage();
        this.height = height;
        this.width = width;
        this.speed = speed;
        recentSpawned = true;
        x= 0;
        y = 0;
        hitBox = new Rectangle(x,y,width,height);
        mapPercent = 1;
        dimensionX=500;
        dimensionY = 500;
    }
    public String getName()
    {
        return name;
    }

    public int getPointGain()
    {
        return pointGain;
    }

    public void recentFalse()
    {
        recentSpawned = false;
    }

    public boolean isRecentSpawned()
    {
        return recentSpawned;
    }

    public void changeHitBox(int x, int y,int width, int height)
    {
        hitBox = new Rectangle(x,y,width/this.width,height/this.height);
        this.x = x;
        this.y = y;
    }
    public void changeHitBox(int x, int y)
    {
        hitBox.translate(x,y);
    }
    public void setDimensionY(int dimensionY)
    {
        this.dimensionY = dimensionY;
    }
    public void setY(int height, int dimensionY)
    {
      if(y==0 || y >=dimensionY*0.83){
          if(name.equals("fish_walker1")) y = (height-height/50);
          else if(name.equals("treasureChest")) y = height+height/8;
          else{
              y = (int)(Math.random()*(height/2-height/6))+(height/2+height/8);
          }
      }
      if(dimensionY!=this.dimensionY)
      {
          y = y/this.dimensionY * dimensionY;
          this.dimensionY = dimensionY;
      }
    }

    public void setX(int x)
    {
        this.x = x;
    }


    public void setMapPercent(double v)
    {
        mapPercent = v;
    }

    public double getMapPercent()
    {
        return  mapPercent;
    }

    public void setDimensionX(int width)
    {
        dimensionX = width;
    }
    public int getDimensionX()
    {
        return dimensionX;
    }


    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
    public int getSpeed()
    {
        return speed;
    }

    public void swim(int width)
    {
       if(!name.equals("treasureChest"))
       {
           x-=width/speed;
           changeHitBox(x,y);
       }
       else{
           if(x==0){
               x = (int)(Math.random()*(width))+10;
           }
       }
    }



}
