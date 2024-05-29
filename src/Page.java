import java.util.ArrayList;
public class Page {
    private Background currentBackground;
    private ArrayList<Button> currentButtons;
    private String pageName;
    private String bgmName;
    private SoundUtilities bgm;
    private boolean muted;
    private boolean mutedBefore;
    public Page(String name)
    {
        pageName = name;
        currentBackground = new Background(pageName);
        currentButtons = getButtons();
        bgmName = name + ".wav";
        if(name.equals("PlayGame"))
        {
            int random = (int)(Math.random()*3)+1;
            if(random == 2) bgmName = name+"2.wav";
            else if (random == 3) bgmName = name+"3.wav";
        }
        bgm = new SoundUtilities();
        muted = false;
        mutedBefore = false;
    }
    public ArrayList<Button> getButtons()
    {
        ArrayList<Button> pageButtons = new ArrayList<>();
        if(pageName.equals("menu"))
        {
            pageButtons.add(new Button("Play",200,400));
            pageButtons.add(new Button("Help",200,500));
            pageButtons.add(new Button("Quit",200,600));
        }
        else if(pageName.equals("game!"))
        {
            pageButtons.add(new Button("check FishDex",200,500));
            pageButtons.add(new Button("replay",200,500));
            pageButtons.add(new Button("exit",200,500));
        }
        else if(pageName.equals("Help"))
        {
            pageButtons.add(new Button("exit",200,500));
        }
        else if(pageName.equals("Play"))
        {
            pageButtons.add(new Button("Clear",200,500));
            pageButtons.add(new Button("Don't Clear",200,500));
        }
        else if(pageName.equals("PlayGame"))
        {
            pageButtons.add(new Button("pause",200,500));
        }
        else if(pageName.equals("pause"))
        {
            pageButtons.add(new Button("continue",200,500));
            pageButtons.add(new Button("check",200,500));
            pageButtons.add(new Button("return",200,500));
        }
        else if(pageName.equals("index"))
        {
            pageButtons.add(new Button("return",200,500));
        }
        else if(pageName.equals("leaderboard"))
        {
            pageButtons.add(new Button("return",200,500));
        }
        else if(pageName.equals("newGame"))
        {
            pageButtons.add(new Button("return",200,500));
        }
        pageButtons.add(new Button("muted",200,500));
        return pageButtons;
    }

    public Background getCurrentBackground() {
        return currentBackground;
    }

    public ArrayList<Button> getCurrentButtons() {
        return currentButtons;
    }

    public String getPageName() {
        return pageName;
    }

    public String getBgmName(){return bgmName;}
    public void startBgm()
    {
        if(muted && !mutedBefore)
        {
            stopBgm();
            mutedBefore = true;
        }
        else if(!muted) bgm.playSound(bgmName);

    }
    public void stopBgm()
    {
        bgm.stopSound();
    }
    public boolean isMuted(){return muted;}
    public void setMuted(){
        muted = !muted;
        mutedBefore = false;
    }
}
