import java.io.File;
import javax.sound.sampled.*;

public class SoundUtilities {
    private Clip clip;
    private boolean soundPlaying;
    private String soundFile;
    public SoundUtilities()
    {
        try{
            clip = AudioSystem.getClip();
        }
        catch(LineUnavailableException e)
        {
            System.out.println("Line unavailable");
        }
        soundPlaying = false;
        soundFile = "";
    }

    public void playSound(String soundFile) {
       if(!soundPlaying || !soundFile.equals(this.soundFile))
       {
           try{
               File f = new File("AudioFile/" + soundFile);
               AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
               clip.open(audioIn);
               clip.start();
               clip.loop(Clip.LOOP_CONTINUOUSLY);
               soundPlaying = true;
           }
           catch (Exception e)
           {
               if(!soundPlaying)
               {
                   System.out.println("sound error");
               }
           }
       }
    }

    public void playSoundEffect(String soundFile)
    {
        try{
            File f = new File("AudioFile/" + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch (Exception e)
        {
           System.out.println("exception");
           System.out.println(e);
        }
    }

    public void stopSound()
    {
        clip.stop();
        soundPlaying = false;
    }
}
