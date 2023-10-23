package puzzlers.tasks;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortByAmount {

    /**
     * Get a string with values divided by a space, sort it by occurrences of substrings
     * @param input - actual string, for example AA BB CC DD
     * @return list of values sorted
     */
    public static List<String> sortByOccurrence(String input) {
        var values = input.split(" ");
        var data = new HashMap<String, Integer>();

        for (String value : values) {
            if (data.containsKey(value)) {
                data.put(value, data.get(value) + 1);
            } else {
                data.put(value, 1);
            }
        }

        return data.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }
}