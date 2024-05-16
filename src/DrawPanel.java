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
    private String prevPage;

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
       else if(currentPage.getPageName().equals("Play"))
       {
           paintGameOption(g);
       }
       else if (currentPage.getPageName().equals("PlayGame"))
       {
           paintGame(g);
       }
       else if(currentPage.getPageName().equals("game!"))
       {
           paintFinish(g);
       }
       else if(currentPage.getPageName().equals("Help"))
       {
           //player.clearData();
           paintHelp(g);
       }
       else if(currentPage.getPageName().equals("Quit"))
       {
           player.saveGame();
           System.exit(0);
       }
       else if(currentPage.getPageName().equals("pause"))
       {
           paintPause(g);
       }
       else if(currentPage.getPageName().equals("index"))
       {
           paintIndex(g);
       }
    }

    public void paintMenu(Graphics g)
    {
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), this);
        int pageWidth = getWidth();
        int yOfRect = getHeight()/2;
        BufferedImage logo = readImage("Image/Logo.png");
        g.drawImage(logo,pageWidth/4,getHeight()/5,pageWidth/2,getHeight()/6,this);
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
            currentPage = new Page("game!");
            gameTime = new GameTimer();
            generator.endTimer();
            currentFishes.clear();
            generator.generateAvailableFishes();
            return;
        }
        t.start();
        if(currentFishes.isEmpty())
        {
            generator.generateFishes(getWidth(),currentFishes);
        }
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/20));
        g.drawString("Time Remaining - " + time,10,getHeight()/10);
        g.drawString("High Score:  " + player.getMaxScore(),10,getHeight()/10+getHeight()/10);
        g.drawString("Score:  " + player.getScore(),10,getHeight()/10+2*getHeight()/10);
        g.drawImage(readImage("Image/List.png"),getWidth()-getWidth()/10,getHeight()/10,getWidth()/15,getHeight()/15,this);
        currentPage.getCurrentButtons().get(0).setRec(getWidth()-getWidth()/10,getHeight()/10,getWidth()/15,getHeight()/15);
        if (time.contains("0:0"))
        {
            g.setColor(new Color(250,0,0));
            g.drawString("Time Remaining - " + time,10,getHeight()/10);
        }
        player.setY(getHeight()/3+getHeight()/20);
        if(player.getDimensionX()!= getWidth())
        {
            player.setDimensionX(getWidth());
            player.setX((int)((player.getDimensionX()- player.getDimensionX()/10)*player.getPercentMap()));
        }
        player.setFishingLine(player.getX() + getWidth()/6-getWidth()/200+getWidth()/700,player.getY()+player.getY()/3-getHeight()/80,getWidth()/160-getWidth()/400, player.getFishingLineH());
        player.setDimensionX(getWidth());
        g.drawImage(player.getImage(),player.getX(),player.getY(),getWidth()/5,getHeight()/4,this);
        g.setColor(new Color(0, 0, 0));
        g.fillRect(player.getFishingLineX()-getWidth()/550,player.getFishingLineY(), player.getFishingLineW()+getWidth()/380, player.getFishingLineH());
        g.setColor(new Color(167, 172, 186));
        g.fillRect(player.getFishingLineX(),player.getFishingLineY(), player.getFishingLineW(), player.getFishingLineH());
        if(player.getFishingLineH()>getHeight()/10)
        {
            g.drawImage(player.getHook(),player.getFishingLineX()-getWidth()/60,player.getFishingLineY()+player.getFishingLineH(),getWidth()/40,getHeight()/40,this);
        }
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
            g.drawImage(swim.getImage(),swim.getX(),swim.getY(),getWidth()/swim.getWidth(),getHeight()/swim.getHeight(),this);
            //see fish hit boxes
            //g.drawRect(swim.getX(),swim.getY(),getWidth()/swim.getWidth(),getHeight()/swim.getHeight());
        }
    }
    public void paintFinish(Graphics g)
    {
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(255,255,255));
        g.fillRect(getWidth()/4,getHeight()/4-getHeight()/35,getWidth()/2+getWidth()/18,getHeight()/2+getHeight()/17);
        g.setColor(new Color(100,180,255));
        g.fillRect(getWidth()/4+getWidth()/40,getHeight()/4,getWidth()/2,getHeight()/2);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/20));
        g.drawString("Score: " + player.getScore(), getWidth()/4+getWidth()/30,getHeight()/4+getHeight()/16);
        g.drawString("High Score:" + player.getMaxScore(), getWidth()/4+getWidth()/30,getHeight()/4+getHeight()/16+getHeight()/12);
        g.drawString("Hooks Dropped:" + player.getHookDropped(), getWidth()/4+getWidth()/30,getHeight()/4+getHeight()/16+2*getHeight()/12);
        g.drawString("Fish Caught:" + player.getFishCaught(), getWidth()/4+getWidth()/30,getHeight()/4+getHeight()/16+3*getHeight()/12);
        g.drawString("Accuracy:" + (int)((double)player.getFishCaught()/ player.getHookDropped()*100) + "%", getWidth()/4+getWidth()/30,getHeight()/4+getHeight()/16+4*getHeight()/12);
        g.drawImage(readImage("Image/Floppy-Drive.png"),getWidth()/4+4*getWidth()/30,getHeight()/4+getHeight()/16+4*getHeight()/11,getWidth()/20,getHeight()/20,this);
        g.drawImage(readImage("Image/Redo.png"),getWidth()/4+7*getWidth()/30,getHeight()/4+getHeight()/16+4*getHeight()/11,getWidth()/15,getHeight()/15,this);
        g.drawImage(readImage("Image/Exit.png"),getWidth()/4+10*getWidth()/30,getHeight()/4+getHeight()/16+4*getHeight()/11,getWidth()/20,getHeight()/20,this);
        int buttonFactor = 4;
        for(Button currentButtons : currentPage.getCurrentButtons())
        {
            currentButtons.setRec(getWidth()/4+buttonFactor*getWidth()/30,getHeight()/4+getHeight()/16+4*getHeight()/11,getWidth()/22,getHeight()/15);
            buttonFactor +=3;
        }
    }
    public void paintHelp(Graphics g)
    {
        g.setColor(new Color(100,180,255));
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(readImage("Image/Left-Arrow.png"),0,0,getWidth()/16,getHeight()/14,this);
        currentPage.getCurrentButtons().get(0).setRec(0,0,getWidth()/16,getHeight()/14);
        g.drawImage(player.getImage(),getWidth()/3,0,getWidth()/4,getHeight()/5,this);
        g.drawImage(readImage("Image/Mouse-Left.png"),0,getHeight()/4+getHeight()/20,getWidth()/10,getHeight()/8,this);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/20));
        g.drawString("Left Click on the Mouse will",getWidth()/9,getHeight()/4+getHeight()/20);
        g.drawString("drop the fishing line",getWidth()/9,getHeight()/4+2*getHeight()/18);
        g.drawString("directly below the rod.",getWidth()/9,getHeight()/4+3*getHeight()/16);
        g.drawImage(readImage("Image/aKey.png"),getHeight()/26*-1,getHeight()/4+5*getHeight()/16,getWidth()/5,getHeight()/6,this);
        g.drawString("Move fisherman left",getWidth()/9+getWidth()/30,getHeight()/4+8*getHeight()/18);
        g.drawImage(readImage("Image/dKey.png"),getHeight()/26*-1,getHeight()/4+9*getHeight()/16,getWidth()/5,getHeight()/6,this);
        g.drawString("Move fisherman right",getWidth()/9+getWidth()/30,getHeight()/4+12*getHeight()/18);

    }

    public void paintGameOption(Graphics g)
    {
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), this);
        g.fillRect(getWidth()/10,getHeight()/4,getWidth()-getWidth()/5,getHeight()/2);
        g.setColor(new Color(255,255,255));
        g.fillRect(getWidth()/10 + getWidth()/8,getHeight()/4+getHeight()/28,(getWidth()-getWidth()/5)/3,getHeight()/2 - getHeight()/14);
        g.fillRect(4*getWidth()/10 + getWidth()/8,getHeight()/4+getHeight()/28,(getWidth()-getWidth()/5)/3,getHeight()/2 - getHeight()/14);
        g.setColor(new Color(100,180,255));
        g.fillRect(getWidth()/10 + getWidth()/8,getHeight()/4+getHeight()/20,(getWidth()-getWidth()/5)/3,getHeight()/2 - getHeight()/10);
        currentPage.getCurrentButtons().get(0).setRec(getWidth()/10 + getWidth()/8,getHeight()/4+getHeight()/20,(getWidth()-getWidth()/5)/3,getHeight()/2 - getHeight()/10);
        g.fillRect(4*getWidth()/10 + getWidth()/8,getHeight()/4+getHeight()/20,(getWidth()-getWidth()/5)/3,getHeight()/2 - getHeight()/10);
        currentPage.getCurrentButtons().get(1).setRec(4*getWidth()/10 + getWidth()/8,getHeight()/4+getHeight()/20,(getWidth()-getWidth()/5)/3,getHeight()/2 - getHeight()/10);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/20));
        g.drawString("New Game",getWidth()/10 + getWidth()/7,getHeight()/4+getHeight()/4);
        g.drawString("Load Game",4*getWidth()/10 + getWidth()/8,getHeight()/4+getHeight()/4);

    }
    public void paintPause(Graphics g)
    {
        int[] timer = gameTime.getTimeRemaining();
        String time = timer[0] + ":" + timer[1] + timer[2];
        g.drawImage(readImage("Image/PlayGame.png"), 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/20));
        g.drawString("Time Remaining - " + time,10,getHeight()/10);
        g.drawString("High Score:  " + player.getMaxScore(),10,getHeight()/10+getHeight()/10);
        g.drawString("Score:  " + player.getScore(),10,getHeight()/10+2*getHeight()/10);
        g.drawImage(readImage("Image/List.png"),getWidth()-getWidth()/10,getHeight()/10,getWidth()/15,getHeight()/15,this);
        currentPage.getCurrentButtons().get(0).setRec(getWidth()-getWidth()/10,getHeight()/10,getWidth()/15,getHeight()/15);
        if (time.contains("0:0"))
        {
            g.setColor(new Color(250,0,0));
            g.drawString("Time Remaining - " + time,10,getHeight()/10);
        }
        if(player.getDimensionX()!= getWidth())
        {
            player.setDimensionX(getWidth());
            player.setX((int)((player.getDimensionX()- player.getDimensionX()/10)*player.getPercentMap()));
        }
        player.setFishingLine(player.getX() + getWidth()/6-getWidth()/200+getWidth()/700,player.getY()+player.getY()/3-getHeight()/80,getWidth()/160-getWidth()/400, player.getFishingLineH());
        player.setDimensionX(getWidth());
        g.drawImage(player.getImage(),player.getX(),player.getY(),getWidth()/5,getHeight()/4,this);
        g.setColor(new Color(0, 0, 0));
        g.fillRect(player.getFishingLineX()-getWidth()/550,player.getFishingLineY(), player.getFishingLineW()+getWidth()/380, player.getFishingLineH());
        g.setColor(new Color(167, 172, 186));
        g.fillRect(player.getFishingLineX(),player.getFishingLineY(), player.getFishingLineW(), player.getFishingLineH());
        if(player.getFishingLineH()>getHeight()/10)
        {
            g.drawImage(player.getHook(),player.getFishingLineX()-getWidth()/60,player.getFishingLineY()+player.getFishingLineH(),getWidth()/40,getHeight()/40,this);
        }
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
            g.drawImage(swim.getImage(),swim.getX(),swim.getY(),getWidth()/swim.getWidth(),getHeight()/swim.getHeight(),this);
            //see fish hit boxes
            //g.drawRect(swim.getX(),swim.getY(),getWidth()/swim.getWidth(),getHeight()/swim.getHeight());
        }
        g.setColor(new Color(255,255,255));
        g.fillRect(getWidth()/4,getHeight()/4-getHeight()/35,getWidth()/2+getWidth()/18,getHeight()/2+getHeight()/17);
        g.setColor(new Color(100,180,255));
        g.fillRect(getWidth()/4+getWidth()/40,getHeight()/4,getWidth()/2,getHeight()/2);
        g.setColor(new Color(255,255,255));
        g.fillRect(getWidth()/4+getWidth()/30+getWidth()/14,getHeight()/4+getHeight()/20,getWidth()/3+getWidth()/42,getHeight()/15+getHeight()/50);
        g.fillRect(getWidth()/4+getWidth()/30+getWidth()/14,getHeight()/4+4*getHeight()/20,getWidth()/3+getWidth()/42,getHeight()/15+getHeight()/50);
        g.fillRect(getWidth()/4+getWidth()/30+getWidth()/14,getHeight()/4+7*getHeight()/20,getWidth()/3+getWidth()/42,getHeight()/15+getHeight()/50);
        g.setColor(new Color(0,200,0));
        g.fillRect(getWidth()/4+getWidth()/30+getWidth()/12,getHeight()/4+getHeight()/16,getWidth()/3,getHeight()/15);
        currentPage.getCurrentButtons().get(0).setRec(getWidth()/4+getWidth()/30+getWidth()/12,getHeight()/4+getHeight()/16,getWidth()/3,getHeight()/15);
        g.setColor(new Color(196, 181, 35));
        g.fillRect(getWidth()/4+getWidth()/30+getWidth()/12,getHeight()/4+3*getHeight()/16+getHeight()/45,getWidth()/3,getHeight()/15);
        currentPage.getCurrentButtons().get(1).setRec(getWidth()/4+getWidth()/30+getWidth()/12,getHeight()/4+3*getHeight()/16+getHeight()/45,getWidth()/3,getHeight()/15);
        g.setColor(new Color(196, 48, 35));
        g.fillRect(getWidth()/4+getWidth()/30+getWidth()/12,getHeight()/4+5*getHeight()/16+getHeight()/21,getWidth()/3,getHeight()/15);
        currentPage.getCurrentButtons().get(2).setRec(getWidth()/4+getWidth()/30+getWidth()/12,getHeight()/4+5*getHeight()/16+getHeight()/21,getWidth()/3,getHeight()/15);
        g.setColor(new Color(255,255,255));
        g.drawImage(readImage("Image/PlayButton.png"),getWidth()/4+getWidth()/30,getHeight()/4+getHeight()/16,getWidth()/15,getHeight()/15,this);
        g.drawImage(readImage("Image/Floppy-Drive.png"),getWidth()/4+getWidth()/30,getHeight()/4+3*getHeight()/16+getHeight()/30,getWidth()/15,getHeight()/15,this);
        g.drawImage(readImage("Image/Home.png"),getWidth()/4+getWidth()/30,getHeight()/4+6*getHeight()/16-getHeight()/40,getWidth()/15,getHeight()/15,this);
        g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/20));
        g.drawString("Unpause", getWidth()/4+getWidth()/30+getWidth()/7+ getWidth()/100,getHeight()/4+getHeight()/9+getHeight()/120);
        g.drawString("Check Index", getWidth()/4+getWidth()/30+getWidth()/9-getWidth()/40,getHeight()/4+2*getHeight()/9+getHeight()/26);
        g.drawString("Save & Exit", getWidth()/4+getWidth()/30+getWidth()/9-getWidth()/40,getHeight()/4+3*getHeight()/9+getHeight()/12);

    }
    public void paintIndex(Graphics g)
    {
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(readImage("Image/Left-Arrow.png"),getWidth()/20,getHeight()/10,getWidth()/16,getHeight()/14,this);
        currentPage.getCurrentButtons().get(0).setRec(getWidth()/20,getHeight()/10,getWidth()/16,getHeight()/14);
        ArrayList<String> availableFishes = FishGenerate.getAvailableFishes();
        Sort.sort(availableFishes);
        Sort.removeNum(availableFishes);



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
                    }
                }
            }
            else if(currentPage.getPageName().equals("PlayGame"))
            {
                boolean pressed = false;
                for(Button currentButtons : currentPage.getCurrentButtons())
                {
                    Rectangle hitBox = currentButtons.getButton();
                    if(hitBox.contains(clicked))
                    {
                        pressed = true;
                        if(currentButtons.getName().equals("pause")){
                            currentPage = new Page("pause");
                            gameTime.pauseGame();
                        }
                    }
                }
               if(!pressed){
                   player.dropLine(getHeight());
                   generator.generateFishes(getWidth(),currentFishes);
               }
            }
            else if(currentPage.getPageName().equals("game!"))
            {
                for(Button currentButtons : currentPage.getCurrentButtons())
                {
                    if(currentButtons.getButton().contains(clicked))
                    {
                        player.clearStat();
                        if(currentButtons.getName().equals("exit"))
                        {
                            currentPage = new Page("menu");
                        }
                        else if(currentButtons.getName().equals("replay"))
                        {
                            currentPage = new Page("Play");
                        }
                        else{
                            prevPage = "game!";
                            currentPage = new Page("index");
                        }
                    }
                }
            }
            else if(currentPage.getPageName().equals("Help"))
            {
                for(Button currentButtons : currentPage.getCurrentButtons())
                {
                    if(currentButtons.getButton().contains(clicked))
                    {
                        if(currentButtons.getName().equals("exit"))
                        {
                            currentPage = new Page("menu");
                        }
                    }
                }
            }
            else if (currentPage.getPageName().equals("Play"))
            {
                for(Button currentButton : currentPage.getCurrentButtons())
                {
                    if(currentButton.getButton().contains(clicked))
                    {
                        if(currentButton.getName().equals("Clear"))
                        {
                            player.clearData();
                        }
                        currentPage = new Page("PlayGame");
                        gameTime = new GameTimer();
                        gameTime.startGame();
                        generator.startTimer();
                    }
                }
            }
            else if (currentPage.getPageName().equals("pause"))
            {
                for(Button currentButton : currentPage.getCurrentButtons())
                {
                    if(currentButton.getButton().contains(clicked))
                    {
                        if(currentButton.getName().equals("continue"))
                        {
                            currentPage = new Page("PlayGame");
                            gameTime.startGame();
                            generator.startTimer();
                        }
                        else if(currentButton.getName().equals("return"))
                        {
                            currentPage = new Page("game!");
                        }
                        else {
                            prevPage = "pause";
                            currentPage = new Page("index");
                        }
                    }
                }
            }
            else if(currentPage.getPageName().equals("index"))
            {
                for(Button currentButton : currentPage.getCurrentButtons())
                {
                    if(currentButton.getButton().contains(clicked))
                    {
                        if(currentButton.getName().equals("return"))
                        {
                            currentPage = new Page(prevPage);
                        }
                    }
                }
            }
        }

    }

    public void keyPressed(KeyEvent e)
    {
        if(currentPage.getPageName().equals("PlayGame"))
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
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void keyTyped(KeyEvent e){ }
    public void keyReleased(KeyEvent e){}
    public void actionPerformed(ActionEvent e){
       if(currentPage.getPageName().equals("PlayGame"))
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