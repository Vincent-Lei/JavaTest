package sort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertSort {
    /**
     * 直接插入排序的原理：先将原序列分为有序区和无序区，然后再经过比较和后移操作将无序区元素插入到有序区中。
     */

    public void directInsertSort(int[] sourceArray) {
        int temp, index;
        for (int i = 1; i < sourceArray.length; i++) {
            temp = sourceArray[i];
            for (index = i - 1; index >= 0; index--) {
                if (temp < sourceArray[index]) {
                    sourceArray[index + 1] = sourceArray[index];
                } else
                    break;
            }
            if (index + 1 != i)
                sourceArray[index + 1] = temp;
        }
    }

    public void binaryInsertSort(int[] sourceArray) {
        int temp;
        int l, r, middle;
        for (int i = 1; i < sourceArray.length; i++) {
            temp = sourceArray[i];
            l = 0;
            r = i - 1;
            while (l <= r) {
                middle = (l + r) / 2;
                if (sourceArray[middle] > temp) {
                    r = middle - 1;
                } else {
                    l = middle + 1;
                }
            }
            for (int j = i - 1; j >= l; j--) {
                sourceArray[j + 1] = sourceArray[j];
            }
            sourceArray[l] = temp;
        }
    }


    private void sort() {
        int length = 180000;
        int[] sourceArray = ArrayManager.createIntArray(length, 100);
        int[] sourceArray2 = new int[length];
        System.arraycopy(sourceArray, 0, sourceArray2, 0, length);
        long a = System.currentTimeMillis();
        directInsertSort(sourceArray);
        long b = System.currentTimeMillis();
        System.out.println("directInsertSort：" + (b - a));

        long a2 = System.currentTimeMillis();
        binaryInsertSort(sourceArray2);
        long b2 = System.currentTimeMillis();
        System.out.println("binaryInsertSort：" + (b2 - a2));
//        ArrayManager.printArray(sourceArray, ",");
    }

    public static void main(String[] args) {
//        new InsertSort().sort();
//        String reg = "(^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$)|(^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$)";
        String reg = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
        Pattern pattern = Pattern.compile(reg);
        String[]array = {"192.168.1.203","pop3.com","abc.abc","123","w534","qq.aa.com"};
        for (int i = 0; i < array.length; i++) {
            Matcher matcher = pattern.matcher(array[i]);
            if(matcher.find()){
                System.out.println("match success");
            }else
                System.out.println("match failed");
        }

    }
}
