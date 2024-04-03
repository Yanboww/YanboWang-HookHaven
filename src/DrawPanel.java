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
    private Background currentBackground;
    private ArrayList<Button> currentButtons;

    public DrawPanel() {
        currentBackground = new Background("menu.jpg");
        this.addMouseListener(this);
        setFocusable(true);
        this.addKeyListener(this);
        currentButtons = Button.getButtons(currentBackground);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentBackground.getImage(), 0, 0, getWidth(), getHeight(), this);

    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();
        if (e.getButton() == 1) {
            for(Button clickedButtons : currentButtons)
            {
                if(clickedButtons.getButton().contains(clicked))
                {
                    String name = clickedButtons.getName();
                    if(name.equals("Play"))
                    {
                        currentBackground = new Background("Ocean");
                    }
                }
            }
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