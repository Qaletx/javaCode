package io;

import java.io.FileWriter;
import java.io.IOException;

public class IOTest04 {
    public static void main(String[] args) {
        FileWriter fileWriter = null;
        try {
            //         不能指定字符集
           fileWriter = new FileWriter("./src/io/test_file/test.txt",true);

//           整个数组读入
           char[] test = {'我','感','到','慌'};
           fileWriter.write(test);

//          指定起始位置与长度
            fileWriter.write(test,1,3);

//            同样可以用String
            fileWriter.write("我现在感觉好了一些".toCharArray());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
