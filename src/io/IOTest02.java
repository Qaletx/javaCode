package io;
import java.io.*;

public class IOTest02 {
    public static void main(String[] args)
    {
        FileOutputStream fileOutputStream = null;
        try {
//            后面写true为追加
//            没有该文件则会新建
//         不能指定字符集
            fileOutputStream= new FileOutputStream("./src/io/test_file/test.txt",true);


            byte[] bytes = {43,53,23,23};
//             传一个byte数组
//            fileOutputStream.write(bytes);


//            可以指定从哪开始,多少个
//            fileOutputStream.write(bytes,0,2);
//            可以将一个字符串转为byte数组后写入
            String a = "我是来测试字符串的api中转换为byte数组后写入的";
//            正常传byte或转换为byte数组不写字符集，默认utf-8，写了则按对应的字符集写入
//            我估计转为byte数组指定字符集，会有标志，会指明是哪个字符集的
//            fileOutputStream.write(a.getBytes("UTF-16"));
            fileOutputStream.write(a.getBytes());



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
