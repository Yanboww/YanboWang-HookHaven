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
    private ArrayList<Fish> currentFishes;
    Timer t;
    private FishGenerate generator;

    public DrawPanel() {
        currentPage = new Page("menu");
        this.addMouseListener(this);
        setFocusable(true);
        this.addKeyListener(this);
        player = new Character(-1000);
        generator =  new FishGenerate();
        t = new Timer(100,this);
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
        t.start();
        generator.startTimer();
        if(currentFishes == null)
        {
            currentFishes = generator.generateFishes(getWidth());
        }
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), null);
        player.setY(getHeight()/2 + getHeight()/10);
        if(player.getDimensionX()!= getWidth())
        {
            player.setDimensionX(getWidth());
            player.setX((int)((player.getDimensionX()- player.getDimensionX()/10)*player.getPercentMap()));
        }
        player.setDimensionX(getWidth());
        g.fillRect(player.getX(),player.getY(),50,50);
        player.saveGame();
        for(Fish swim : currentFishes)
        {
            if(swim.isRecentSpawned())
            {
                swim.setDimensionX(getWidth());
                swim.recentFalse();
                swim.changeHitBox(getWidth()-getWidth()/10,getHeight()-getHeight()/6,getWidth(),getHeight());
            }
            else if(swim.getDimensionX()!=getWidth())
            {
                swim.setDimensionX(getWidth());
                swim.setX((int)((swim.getDimensionX()- swim.getDimensionX()/10)*swim.getMapPercent()));
                swim.changeHitBox(swim.getX(),swim.getY());
            }
            swim.setMapPercent((double)swim.getX()/(getWidth()-getWidth()/10));
            swim.setY(getHeight()-getHeight()/6,getHeight());
            swim.changeHitBox(swim.getX(),swim.getY());
            g.drawImage(swim.getImage(),swim.getX(),swim.getY(),getWidth()/swim.getWidth(),getHeight()/swim.getHeight(),null);
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
                    }
                }
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
        for(Fish swim : currentFishes)
        {
            swim.swim(getWidth());
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

}