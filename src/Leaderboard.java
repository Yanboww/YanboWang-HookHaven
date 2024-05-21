import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

public class Leaderboard{
    private String currentName;
    private int points;
    private ArrayList<String> leaderboard;
    private ArrayList<Integer> pointLeaderboard;

    public Leaderboard()
    {
        leaderboard = new ArrayList<>();
        pointLeaderboard = new ArrayList<>();
        currentName = "someone";
        retrieveLeaderBoard();
    }

    public void setCurrentName(String name)
    {
        currentName = name;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public void retrieveLeaderBoard()
    {
        File f;
        try{
            f = new File("Data/leaderboard");
            Scanner s = new Scanner(f);
            while(s.hasNextLine() && leaderboard.size() < 10)
            {
                String line = s.nextLine();
                leaderboard.add(line.substring(0,line.indexOf(" ")));
                pointLeaderboard.add(Integer.parseInt(line.substring(line.lastIndexOf(" ")+1)));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("leaderboard not found");
        }
    }

    public void saveLeaderBoard()
    {
        File f;
        FileWriter fw;
        try{
            f = new File("Data/leaderboard");
            fw = new FileWriter(f);
            int count = 0;
           for(String rank : leaderboard)
           {
               if(count == leaderboard.size()-1) fw.write(rank + " -- " +pointLeaderboard.get(count));
               else fw.write(rank + " -- " + pointLeaderboard.get(count) +"\n");
               count++;
           }
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println("File Save Error Encountered");
        }
    }

    public void sortPoints()
    {
        SortUtilities.sortInt(pointLeaderboard);
    }

    public void updateLeaderBoard() {
        pointLeaderboard.add(points);
        sortPoints();
        int index = pointLeaderboard.lastIndexOf(points);
        leaderboard.add(index,currentName);
        if(leaderboard.indexOf(currentName) != leaderboard.lastIndexOf(currentName)){
            for(int i = leaderboard.size()-1;i>=0;i--)
            {
                if(leaderboard.get(i).contains(currentName))
                {
                    leaderboard.remove(i);
                    pointLeaderboard.remove(i);
                    break;
                }
            }
        }
        saveLeaderBoard();
    }

    public ArrayList<String> getLeaderboard()
    {
        ArrayList<String> returnLeader = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            if(i < leaderboard.size())
            {
                returnLeader.add(leaderboard.get(i) + " - " + pointLeaderboard.get(i));
            }
            else break;
        }
        return returnLeader;
    }


}
