package ngordnet.main;

import java.util.Comparator;
import java.util.HashMap;

public class MapComparator implements Comparator<HashMap<String, Integer>> {

    @Override
    public int compare(HashMap<String, Integer> o1, HashMap<String, Integer> o2) {
        int o1Comp;
        int o2Comp;
        if (o1.size() == 0) {
            o1Comp = 0;
        } else {
            o1Comp = o1.getOrDefault(0, 0);
        }
        if (o2.size() == 0) {
            o2Comp = 0;
        } else {
            o2Comp = o1.getOrDefault(0, 0);
        }
        if (o1Comp > o2Comp) {
            return 1;
        } else if (o1Comp < o2Comp) {
            return -1;
        } else {
            return 0;
        }
    }

}
