import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener, KeyListener {


    private Rectangle button;

    private Rectangle replaceButton;
    private Background currentBackground;

    public DrawPanel() {
        replaceButton = new Rectangle(162, 280, 160, 26);
        button = new Rectangle(162, 240, 160, 26);
        currentBackground = new Background("menu");
        this.addMouseListener(this);
        setFocusable(true);
        this.addKeyListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentBackground.getImage(),0,0,null);
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();
        if (e.getButton() == 1) {
            System.out.println("SSSS");
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

}