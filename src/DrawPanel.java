import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
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

class DrawPanel extends JPanel implements MouseListener, KeyListener {
    private Page currentPage;

    public DrawPanel() {
        currentPage = new Page("menu");
        this.addMouseListener(this);
        setFocusable(true);
        this.addKeyListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentPage.getCurrentBackground().getImage(), 0, 0, getWidth(), getHeight(), null);
       if(currentPage.getPageName().equals("menu"))
       {
           int pageWidth = getWidth();
           int yOfRect = getHeight()/2;
           BufferedImage logo = readImage("Image/Logo.png");
           g.drawImage(logo,pageWidth/4,yOfRect-100,pageWidth/4,getHeight()/6,null);
           for(Button currentButtons : currentPage.getCurrentButtons())
           {
               g.setColor(new Color(0,0,0));
               g.fillRect(getWidth()/4,yOfRect,pageWidth/2+pageWidth/50,getHeight()/10+getHeight()/60);
               g.setColor(new Color(100,180,211));
               g.fillRect(getWidth()/4,yOfRect,pageWidth/2,getHeight()/10);
               g.setColor(new Color(255,255,255));
               g.setFont(new Font("Monospaced", Font.BOLD, getWidth()/15));
               g.drawString(currentButtons.getName(),getWidth()/4+pageWidth/6,yOfRect+getHeight()/13);
               yOfRect += getHeight()/6;
           }
       }
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();
        if (e.getButton() == 1) {

        }

    }
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
        {

        }
        else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
        {

        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void keyTyped(KeyEvent e){ }
    public void keyReleased(KeyEvent e){}

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