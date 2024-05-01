import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Color;
import javax.swing.Timer;

class DrawPanel extends JPanel implements MouseListener, KeyListener,ActionListener{
    private Page currentPage;
    private Character player;
    private static ArrayList<Fish> currentFishes;
    Timer t;
    private FishGenerate generator;
    private GameTimer gameTime;

    public DrawPanel() {
        currentPage = new Page("menu");
        this.addMouseListener(this);
        setFocusable(true);
        this.addKeyListener(this);
        player = new Character(-1000);
        generator =  new FishGenerate();
        t = new Timer(100,this);
        currentFishes = new ArrayList<>();
        gameTime = new GameTimer();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       if(currentPage.getPageName().equals("menu"))
       {
          paintMenu(g);
       }
       else if (currentPage.getPageName().equals("Play"))
       {
           paintGame(g);
       }
    }

    public void paintMenu(Graphics g)
    {
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), null);
        int pageWidth = getWidth();
        int yOfRect = getHeight()/2;
        BufferedImage logo = readImage("Image/Logo.png");
        g.drawImage(logo,pageWidth/4,getHeight()/5,pageWidth/2,getHeight()/6,null);
        for(Button currentButtons : currentPage.getCurrentButtons())
        {
            g.setColor(new Color(0,0,0));
            g.fillRect(getWidth()/4,yOfRect,pageWidth/2+pageWidth/50,getHeight()/10+getHeight()/60);
            g.setColor(new Color(100,180,211));
            g.fillRect(getWidth()/4,yOfRect,pageWidth/2,getHeight()/10);
            currentButtons.setRec(getWidth()/4,yOfRect,pageWidth/2,getHeight()/10);
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/15));
            g.drawString(currentButtons.getName(),getWidth()/4+pageWidth/6,yOfRect+getHeight()/13);
            yOfRect += getHeight()/6;
        }
    }

    public void paintGame(Graphics g)
    {
        int[] timer = gameTime.getTimeRemaining();
        String time = timer[0] + ":" + timer[1] + timer[2];
        if(time.equals("0:00")) {
            player.saveGame();
            currentPage = new Page("menu");
            gameTime = new GameTimer();
            return;
        }
        t.start();
        if(currentFishes.isEmpty())
        {
            generator.generateFishes(getWidth(),currentFishes);
        }
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), null);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/20));
        g.drawString("Score:  " + player.getScore(),10,getHeight()/10+getHeight()/10);
        if (time.contains("0:0"))
        {
            g.setColor(new Color(200,0,0));
        }
        g.drawString("Time Remaining - " + time,10,getHeight()/10);
        player.setY(getHeight()/3+getHeight()/20);
        if(player.getDimensionX()!= getWidth())
        {
            player.setDimensionX(getWidth());
            player.setX((int)((player.getDimensionX()- player.getDimensionX()/10)*player.getPercentMap()));
        }
        player.setFishingLine(player.getX() + getWidth()/6-getWidth()/200+getWidth()/700,player.getY()+player.getY()/3,getWidth()/160-getWidth()/400, player.getFishingLineH());
        player.setDimensionX(getWidth());
        g.drawImage(player.getImage(),player.getX(),player.getY(),getWidth()/5,getHeight()/4,null);
        g.setColor(new Color(0, 0, 0));
        g.fillRect(player.getFishingLineX()-getWidth()/550,player.getFishingLineY(), player.getFishingLineW()+getWidth()/380, player.getFishingLineH());
        g.setColor(new Color(167, 172, 186));
        g.fillRect(player.getFishingLineX(),player.getFishingLineY(), player.getFishingLineW(), player.getFishingLineH());
        player.saveGame();
        for(Fish swim : currentFishes)
        {
            if(swim.isRecentSpawned())
            {
                swim.setDimensionX(getWidth());
                swim.setDimensionY(getHeight());
                swim.setY(getHeight()-getHeight()/6,getHeight());
                swim.recentFalse();
                if(!swim.getName().equals("treasureChest")){
                    swim.changeHitBox(getWidth()-getWidth()/10,getHeight()-getHeight()/6,getWidth(),getHeight());
                }
                else swim.swim(getWidth());

            }
            else if(swim.getDimensionX()!=getWidth())
            {
                swim.setDimensionX(getWidth());
                swim.setX((int)((swim.getDimensionX()- swim.getDimensionX()/10)*swim.getMapPercent()));
                swim.changeHitBox(swim.getX(),swim.getY());
            }
            swim.setMapPercent((double)swim.getX()/(getWidth()-getWidth()/10));
            swim.setY(getHeight()-getHeight()/6,getHeight());
            swim.changeHitBox(swim.getX(),swim.getY(),getWidth(),getHeight());
            g.drawImage(swim.getImage(),swim.getX(),swim.getY(),getWidth()/swim.getWidth(),getHeight()/swim.getHeight(),null);
            //see fish hit boxes
            //g.drawRect(swim.getX(),swim.getY(),getWidth()/swim.getWidth(),getHeight()/swim.getHeight());
        }
    }
    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();
        if (e.getButton() == 1) {
            if(currentPage.getPageName().equals("menu"))
            {
                for(Button currentButtons: currentPage.getCurrentButtons())
                {
                    Rectangle hitBox = currentButtons.getButton();
                    if(hitBox.contains(clicked))
                    {
                        currentPage = new Page(currentButtons.getName());
                        if(currentButtons.getName().equals("Play")){
                            gameTime.startGame();
                            generator.startTimer();
                        }
                    }
                }
            }
            else if(currentPage.getPageName().equals("Play"))
            {
                player.dropLine(getHeight());
                generator.generateFishes(getWidth(),currentFishes);
            }
        }

    }
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            player.moveLeft();
        }
        else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            player.moveRight();
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void keyTyped(KeyEvent e){ }
    public void keyReleased(KeyEvent e){}
    public void actionPerformed(ActionEvent e){
       if(currentPage.getPageName().equals("Play"))
       {
           for(int i = 0; i < currentFishes.size(); i++)
           {
               Fish f = currentFishes.get(i);
               f.swim(getWidth());
               if(f.getX()<getWidth()/-4)
               {
                   currentFishes.remove(i);
                   i--;
               }
           }
           generator.generateFishes(getWidth(),currentFishes);
       }
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

    public static ArrayList<Fish> getCurrentFishes()
    {
        return currentFishes;
    }

}