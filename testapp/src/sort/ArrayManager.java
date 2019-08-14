package sort;

import java.util.Random;

public class ArrayManager {
    private static Random mRandom = new Random();

    public static int[] createIntArray(int length, int bounce) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = mRandom.nextInt(bounce);
        }
        return array;
    }

    public static void printArray(int[] array, String splite) {
        System.out.println("--------------------------   printArray   ----------------------------------");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i != array.length - 1)
                System.out.print(splite);
        }
    }
}
