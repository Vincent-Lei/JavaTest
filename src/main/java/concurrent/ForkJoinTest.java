package concurrent;

import concurrent.forkjoin.CalculateWordsCount;
import concurrent.forkjoin.Fibonacci;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinTest {
    public static void main(String[] args) {
        fibonacci(10);
        calculateWords();
    }

    private static void fibonacci(int n) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Fibonacci fibonacci = new Fibonacci(n);
        int result = forkJoinPool.invoke(fibonacci);
        System.out.println("n = " + n + ";fibonacci = " + result);
    }

    private static void calculateWords() {
        String path = ".\\src\\main\\java\\count_word.txt";
        List<String> sourceList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                sourceList.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (sourceList.isEmpty())
            return;
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        CalculateWordsCount calculateWordsCount = new CalculateWordsCount(sourceList, 0, sourceList.size());
        Map<String, Integer> result = forkJoinPool.invoke(calculateWordsCount);
        List<CountTemp> list = new ArrayList<>(result.size());
        result.forEach((k, v) -> {
            list.add(new CountTemp(k, v));
        });
        Collections.sort(list, (o1, o2) -> o2.value - o1.value);
//        list.forEach(it -> System.out.println(it.key + "：" + it.value));
        System.out.println("max count word = " + list.get(0).key + "：" + list.get(0).value);
    }

    private static class CountTemp {
        String key;
        Integer value;

        public CountTemp(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
}
