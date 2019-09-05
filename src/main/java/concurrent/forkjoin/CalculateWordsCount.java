package concurrent.forkjoin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class CalculateWordsCount extends RecursiveTask<Map<String, Integer>> {
    private List<String> sourceList;
    private int start;
    private int end;

    public CalculateWordsCount(List<String> sourceList, int start, int end) {
        this.sourceList = sourceList;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Map<String, Integer> compute() {
        if (end - start == 1) {
            return calculate(sourceList.get(start));
        }
        int middle = (start + end) / 2;
        CalculateWordsCount cwc1 = new CalculateWordsCount(sourceList, start, middle);
        cwc1.fork();
        CalculateWordsCount cwc2 = new CalculateWordsCount(sourceList, middle, end);
        return merge(cwc2.compute(), cwc1.join());
    }

    private Map<String, Integer> calculate(String str) {
        if (str == null || str.trim().length() == 0)
            return null;
        String[] array = str.split("\\s+");
        Map<String, Integer> map = new HashMap<>();
        Integer v;
        String key;
        for (int i = 0; i < array.length; i++) {
            key = array[i].toLowerCase().trim();
            v = map.get(key);
            map.put(key, v == null ? 1 : v + 1);
        }
        return map;
    }

    private Map<String, Integer> merge(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> result = new HashMap<>();
        if (m1 != null) {
            result.putAll(m1);
        }
        if (m2 != null) {
            m2.forEach((k, v) -> {
                Integer r = result.get(k);
                result.put(k, r == null ? 1 : r + v);
            });
        }
        return result;
    }

}
