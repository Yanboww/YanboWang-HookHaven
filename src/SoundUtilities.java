import java.io.File;
import java.io.FileNotFoundException;
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
           catch(FileNotFoundException e)
           {
              try{
                  File f = new File("AudioFile/pause.wav");
                  AudioInputStream audioIn = AudioSystem.getAudioInputStream(f);
                  clip.open(audioIn);
                  clip.start();
                  clip.loop(Clip.LOOP_CONTINUOUSLY);
                  soundPlaying = true;
              }
              catch(Exception error2)
              {
                 if(!soundPlaying)
                 {
                     System.out.println("something is seriously wrong with the sound here");
                 }
              }
           }
           catch (Exception e)
           {
               if(!soundPlaying)
               {
                   clip.start();
                   clip.loop(Clip.LOOP_CONTINUOUSLY);
                   soundPlaying = true;
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
        clip.loop(-1);
        clip.stop();
        soundPlaying = false;
    }
}
