package datastruct.tree.huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class FileEntity {
    private String filePath;

    public FileEntity(String filePath) {
        this.filePath = filePath;
    }

    private HashMap<Byte, Integer> countByte() {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        HashMap<Byte, Integer> byteCountMap = new HashMap<>();
        FileInputStream fileInputStream = null;
        byte[] buff = new byte[1024];
        int len;
        try {
            fileInputStream = new FileInputStream(file);
            while ((len = fileInputStream.read(buff)) > 0) {
                for (int i = 0; i < len; i++) {
                    byteCountMap.put(buff[i], (byteCountMap.containsKey(buff[i]) ? byteCountMap.get(buff[i]) + 1 : 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteCountMap;
    }

    /**
     * 将字符串转成二进制字节的方法
     *
     * @param bString 待转换的字符串
     * @return 二进制字节
     */
    private static byte bit2byte(String bString) {
        byte result = 0;
        for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
            try {
                result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(bString);
                throw e;
            }
        }
        return result;
    }

    /**
     * 将二字节转成二进制的01字符串
     *
     * @param b 待转换的字节
     * @return 01字符串
     */
    private static String byte2bits(byte b) {
        int z = b;
        z |= 256;
        String str = Integer.toBinaryString(z);
        int len = str.length();
        return str.substring(len - 8, len);
    }

    public void file2HfmCode() {
        File f = new File(filePath);
        if (!f.exists())
            return;
        HashMap<Byte, String> byteHfmMap = HuffmanTree.treeToHuffmanCode(countByte());
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            System.out.println("正在压缩~~~");
            // 创建文件输入流
            fis = new FileInputStream(f);
            // 获取文件后缀前的名称
            String name = f.getName().substring(0, f.getName().indexOf("."));
            File outF = new File(f.getParent() + "\\" + name + "-压缩.zy");
            // 创建文件输出流
            fos = new FileOutputStream(outF);
            DataOutputStream dos = new DataOutputStream(fos);
            // 将哈夫曼编码读入到文件头部，并记录哈夫曼编码所占的大小
            Set<Byte> set = byteHfmMap.keySet();
            long hfmSize = 0;
            for (Byte bi : set) {
                // 先统计哈夫曼编码总共的所占的大小
                hfmSize += 1 + 4 + byteHfmMap.get(bi).length();
            }
            // 先将长度写入
            dos.writeLong(hfmSize);
            dos.flush();
            for (Byte bi : set) {
                // 写入哈夫曼编码对应的字节
                dos.writeByte(bi);
                // 先将字符串长度写入
                dos.writeInt(byteHfmMap.get(bi).length());
                // 写入哈夫曼字节的编码
                dos.writeBytes(byteHfmMap.get(bi));
                dos.flush();
            }
            // 保存一次读取文件的缓冲数组
            byte[] buf = new byte[1024];
            int size;
            byte b;
            // 保存哈弗吗编码的StringBuilder
            StringBuilder strBuilder = new StringBuilder();
            while ((size = fis.read(buf)) != -1) {
                // 循环每次读到的实际字节
                for (int i = 0; i < size; i++) {
                    // 获取字节
                    b = buf[i];
                    // 在字节哈夫曼映射中找到该值，获得其hfm编码
                    if (byteHfmMap.containsKey(b)) {
                        String hfmCode = byteHfmMap.get(b);
                        strBuilder.append(hfmCode);
                    }
                }
            }
            // 将保存的文件哈夫曼编码按8个一字节进行压缩
            int hfmLength = strBuilder.length();
            // 获取需要循环的次数
            int byteNumber = hfmLength / 8;
            // 不足8位的数
            int restNumber = hfmLength % 8;
            for (int i = 0; i < byteNumber; i++) {
                String str = strBuilder.substring(i * 8, (i + 1) * 8);
                byte by = bit2byte(str);
                fos.write(by);
                fos.flush();
            }
            int zeroNumber = 8 - restNumber;
            if (zeroNumber < 8) {
                String str = strBuilder.substring(hfmLength - restNumber);
                for (int i = 0; i < zeroNumber; i++) {
                    // 补0操作
                    str += "0";
                }
                byte by = bit2byte(str);
                fos.write(by);
                fos.flush();
            }
            // 将补0的长度也记录下来保存到文件末尾
            String zeroLenStr = Integer.toBinaryString(zeroNumber);
            // 将01串转成字节
            byte zeroB = bit2byte(zeroLenStr);
            fos.write(zeroB);
            fos.flush();
            System.out.println("压缩完毕~~~");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    fis = null;
                }
            }
            // 关闭流
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fos = null;
                }
            }
        }
    }

    /**
     * 将已经压缩的文件进行解压，把哈夫曼编码重新转成对应的字节文件
     * <p>
     * 保存字节的哈夫曼映射
     */
    public void hfmCode2File(String suff) {
        File f = new File(filePath);
        if (!f.exists())
            return;
        // 声明文件输出流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            System.out.println("正在解压~~~");
            // 创建文件输入流
            fis = new FileInputStream(f);
            // 获取文件后缀前的名称
            String name = f.getName().substring(0, f.getName().indexOf("."));
            // 创建文件输出流
            fos = new FileOutputStream(f.getParent() + "\\" + name + "-解压" + suff);
            DataInputStream dis = new DataInputStream(fis);
            long hfmSize = dis.readLong();
            // // 测试读取到的大小是否正确
            // System.out.println(hfmSize);
            // 用来保存从文件读到的哈夫曼编码map
            HashMap<Byte, String> byteHfmMap = new HashMap<>();
            for (int i = 0; i < hfmSize; ) {
                byte b = dis.readByte();
                int codeLength = dis.readInt();
                byte[] bys = new byte[codeLength];
                dis.read(bys);
                String code = new String(bys);
                byteHfmMap.put(b, code);
                i += 1 + 4 + codeLength;
            }
            // 保存一次读取文件的缓冲数组
            byte[] buf = new byte[1024];
            int size = 0;
            // 保存哈弗吗编码的StringBuilder
            StringBuilder strBuilder = new StringBuilder();
            // fis.skip(hfmSize);
            while ((size = fis.read(buf)) != -1) {
                // 循环每次读到的实际字节
                for (int i = 0; i < size; i++) {
                    // 获取字节
                    byte b = buf[i];
                    // 将其转成二进制01字符串
                    String strBin = byte2bits(b);
                    // System.out.printf("字节为：%d，对应的01串为：%s\n",b,strBin);
                    strBuilder.append(strBin);
                }
            }
            String strTotalCode = strBuilder.toString();
            // 获取字符串总长度
            int strLength = strTotalCode.length();
            // 截取出最后八个之外的
            String strFact1 = strTotalCode.substring(0, strLength - 8);
            // 获取最后八个，并且转成对应的字节
            String lastEight = strTotalCode.substring(strLength - 8);
            // 得到补0的位数
            byte zeroNumber = bit2byte(lastEight);
            // 将得到的fact1减去最后的0的位数
            String strFact2 = strFact1.substring(0, strFact1.length() - zeroNumber);
            // 循环字节哈夫曼映射中的每一个哈夫曼值，然后在所有01串种进行匹配
            Set<Byte> byteSet = byteHfmMap.keySet();
            int index = 0;
            // 从第0位开始
            String chs = strFact2.charAt(0) + "";
            while (index < strFact2.length()) {
                // 计数器，用来判断是否匹配到了
                int count = 0;
                for (Byte bi : byteSet) {
                    // 如果匹配到了，则跳出循环
                    if (chs.equals(byteHfmMap.get(bi))) {
                        fos.write(bi);
                        fos.flush();
                        break;
                    }
                    // 没有匹配到则计数器累加一次
                    count++;
                }
                // 如果计数器值大于或鱼等map，说明没有匹配到
                if (count >= byteSet.size()) {
                    index++;
                    chs += strFact2.charAt(index);
                } else {
                    // 匹配到了，则匹配下一个字符串
                    if (++index < strFact2.length()) {
                        chs = strFact2.charAt(index) + "";
                    }
                }
            }
            System.out.println("解压完毕~~~");
        } catch (Exception e) {
        } finally {
            // 关闭流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    fis = null;
                }
            }
            // 关闭流
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fos = null;
                }
            }
        }
    }


}
