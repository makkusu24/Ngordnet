package ngordnet.wordnet;

import java.util.Comparator;

public class MyComparator implements Comparator<String> {
    public int compare(String a, String b) {
        String aNoUnderscore = a.replaceAll("_", "");
        String bNoUnderscore = b.replaceAll("_", "");
        int result = aNoUnderscore.compareTo(bNoUnderscore);
        if (result == 0) {
            result = a.compareTo(b);
        }
        return result;
    }
}
