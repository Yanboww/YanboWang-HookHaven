import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GameTimer implements ActionListener {
    Timer gameTime;
    int[] timeRemaining;
    public GameTimer ()
    {
        gameTime = new Timer(1000,this);
        timeRemaining = new int[3];
        timeRemaining[0] = 1;
    }

    public void startGame()
    {
        gameTime.start();
    }
    public void pauseGame(){gameTime.stop();}

    public int[] getTimeRemaining()
    {
        return timeRemaining;
    }
    public void actionPerformed(ActionEvent e)
    {
        if(timeRemaining[2] == 0)
        {
            if(timeRemaining[1]==0)
            {
                if(timeRemaining[0]!=0)
                {
                    timeRemaining[0] = timeRemaining[0]-1;
                    timeRemaining[1] = 5;
                    timeRemaining[2] = 9;
                }
            }
            else{
                timeRemaining[1] = timeRemaining[1]-1;
                timeRemaining[2] = 9;
            }
        }
        else{
            timeRemaining[2] = timeRemaining[2]-1;
        }
    }
}
