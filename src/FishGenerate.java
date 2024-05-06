import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class FishGenerate implements ActionListener{
    private Timer t;
    private static ArrayList<String> availableFishes;
    private static HashMap<String,Integer> pointValue;
    public static boolean spawnSpecial;
    int counter = 0;

    public FishGenerate(){
        availableFishes = new ArrayList<>();
        pointValue = new HashMap<>();
        t = new Timer(15000,this);
        generateAvailableFishes();
    }

    public ArrayList<Fish> generateFishes(int width, ArrayList<Fish> generatedFishes)
    {
        if(spawnSpecial)
        {
            if(counter == 2){
                generatedFishes.add(new WalkerFish());
                counter=0;
            }
            for(int i = 0; i < generatedFishes.size();i++)
            {
                if(generatedFishes.get(i).getName().equals("treasureChest"))
                {
                    generatedFishes.remove(i);
                    break;
                }
            }
            spawnSpecial = false;
        }
        for(int i = generatedFishes.size(); i < width/200+5;i++)
        {
            int dice = (int)(Math.random()*200);
            String[] fishInfo;
            if(dice<5)
            {
                fishInfo = availableFishes.get(3).split(" ");
                if(countFishInList(fishInfo[0],generatedFishes)>=2) fishInfo = availableFishes.get(1).split(" ");
            }
            else if(dice<10)
            {
                fishInfo = availableFishes.get(4).split(" ");
                if(countFishInList(fishInfo[0],generatedFishes)>=3) fishInfo = availableFishes.get(1).split(" ");
            }
            else if(dice<20)
            {
                fishInfo = availableFishes.get(2).split(" ");
                if(countFishInList(fishInfo[0],generatedFishes)>=5) fishInfo = availableFishes.get(1).split(" ");
            }
            else if(dice<80)
            {
                fishInfo = availableFishes.get(0).split(" ");
            }
            else{
                fishInfo = availableFishes.get(1).split(" ");
            }
            generatedFishes.add(new Fish(fishInfo[0],pointValue.get(fishInfo[0]) ,Integer.parseInt(fishInfo[1]),Integer.parseInt(fishInfo[2]),Integer.parseInt(fishInfo[3])));
        }
        return generatedFishes;
    }

    public void startTimer()
    {
        t.start();
    }
    public void actionPerformed(ActionEvent e) {
        spawnSpecial = true;
        counter++;
    }

    public void generateAvailableFishes()
    {
        availableFishes.add("bass 5 6 70");
        pointValue.put("bass",40);
        availableFishes.add("goldfish 6 7 100");
        pointValue.put("goldfish",30);
        availableFishes.add("stingray 3 4 50");
        pointValue.put("stingray",50);
        availableFishes.add("shark 4 2 40");
        pointValue.put("shark",80);
        availableFishes.add("treasureChest 15 15 0");
        pointValue.put("treasureChest",50);
    }

    private int countFishInList(String name, ArrayList<Fish> currentFishes)
    {
        int count = 0;
        for(Fish currentFish : currentFishes)
        {
            if(currentFish.getName().equals(name)) count++;
        }
        return count;
    }

}
