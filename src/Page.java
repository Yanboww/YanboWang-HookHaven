import java.util.ArrayList;
public class Page {
    private Background currentBackground;
    private ArrayList<Button> currentButtons;
    private String pageName;

    public Page(String name)
    {
        pageName = name;
        currentBackground = new Background(pageName);
        currentButtons = getButtons();
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
}
