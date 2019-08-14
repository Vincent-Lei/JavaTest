import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DimenMaker {


    private static final String DIR_PATH = "C:/Users/Android/Desktop/android";
    private static final String PATH_XHDPI = "/values-xhdpi/dimens.xml";
    private static final String PATH_XXHDPI = "/values-xxhdpi/dimens.xml";
    private static final String PATH_360DP = "/values-sw360dp/dimens.xml";

    public static void main(String[] args) {
        File file = new File(DIR_PATH);
        if (!file.exists())
            file.mkdirs();

        List<DimenMaker> makerList = new ArrayList<DimenMaker>() {
            {
                add(new DimenMaker(DIR_PATH + PATH_XHDPI, 1.0f));
                add(new DimenMaker(DIR_PATH + PATH_XXHDPI, 1.5f));
                add(new DimenMaker(DIR_PATH + PATH_360DP, 1.34f));
            }
        };
        makerList.forEach(DimenMaker::start);

        for (int i = 8; i <= 60; i++) {
            final float ii = i * 1.0f;
            makerList.forEach(dimenMaker -> dimenMaker.writeTextSize(ii));

        }
        makerList.forEach(dimenMaker -> dimenMaker.writeDimen(0.5f));
        for (int i = 1; i <= 600; i++) {
            final float ii = i * 1.0f;
            makerList.forEach(dimenMaker -> dimenMaker.writeDimen(ii));

        }

        makerList.forEach(DimenMaker::stop);
    }

    /*
     *************************************************************************************************************************
     */

    private String filePath;
    private float scale;
    private FileOutputStream fos;
    private static byte[] LINE;
    private static byte[] RES_START;
    private static byte[] RES_END;

    static {
        try {
            RES_START = "<resources>".getBytes("utf-8");
            RES_END = "</resources>".getBytes("utf-8");
            LINE = "\n".getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private DimenMaker(String filePath, float scale) {
        this.filePath = filePath;
        this.scale = scale;
        init();
    }

    private void init() {
        File file = new File(filePath);
        File fileDir = file.getParentFile();
        if (!fileDir.exists())
            fileDir.mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            fos.write(RES_START);
            fos.write(LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            fos.write(RES_END);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTextSize(float dimen) {
        //     <dimen name="dp_12">15dp</dimen>
        int intDimen = (int) dimen;
        String tag = intDimen > 0 ? String.valueOf(intDimen) : String.valueOf(dimen);

        float floatValue = dimen * scale;
        int intValue = (int) floatValue;
        String value = getValue(intValue, floatValue);
        value = "<dimen name=\"text_size_" + tag + "\">" + value + "dp</dimen>";
        try {
            fos.write(value.getBytes("UTF-8"));
            fos.write(LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDimen(float dimen) {
        int intDimen = (int) dimen;
        String tag = intDimen > 0 ? String.valueOf(intDimen) : String.valueOf(dimen);

        float floatValue = dimen * scale;
        int intValue = (int) floatValue;
        String value = getValue(intValue, floatValue);
        value = "<dimen name=\"dp_" + tag + "\">" + value + "dp</dimen>";
        try {
            fos.write(value.getBytes("UTF-8"));
            fos.write(LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getValue(int intValue, float floatValue) {
        String value;
        if (intValue == 0)
            value = "0.5";
        else if (floatValue - intValue >= 0.49f)
            value = String.valueOf(intValue + 0.5f);
        else
            value = String.valueOf(intValue);
        return value;
    }
}
