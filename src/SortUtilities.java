import java.util.ArrayList;
public class SortUtilities {
    public static void sort(ArrayList<String> list) {
        String[] sortAid = new String[list.size()];
        sortProcess(list,sortAid,0,sortAid.length-1);
    }
    private static void sortProcess(ArrayList<String> list, String[] sortAid, int start, int end)
    {
        if(start<end)
        {
            int mid = (start+end)/2;
            sortProcess(list,sortAid,start,mid);
            sortProcess(list,sortAid,mid+1,end);
            merge(list,sortAid,start,mid,end);
        }
    }

    private static void merge(ArrayList<String> list, String[] sortAid, int start, int mid, int end)
    {
        int s = start;
        int m = mid+1;
        int i = start;
        while(s<= mid && m <= end )
        {
            if(list.get(s).compareTo(list.get(m))<=0)
            {
                sortAid[i] = list.get(s);
                s++;
            }
            else{
                sortAid[i] = list.get(m);
                m++;
            }
            i++;
        }

        while(s<=mid)
        {
            sortAid[i] = list.get(s);
            s++;
            i++;
        }

        while(m <= end)
        {
            sortAid[i] = list.get(m);
            m++;
            i++;
        }

        for(int index = start; index <= end; index++)
        {
            list.set(index,sortAid[index]);
        }
    }

    public static void removeNum(ArrayList<String> list)
    {
        for(int i = 0; i < list.size();i++)
        {
           String fish = list.get(i);
           int index = fish.indexOf(" ");
           if(index == -1) return;
           list.set(i,fish.substring(0, index));
        }
    }


}
