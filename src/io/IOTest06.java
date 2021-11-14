package io;

import javax.management.ImmutableDescriptor;
import java.io.*;

public class IOTest06 {
    public static void main(String[] args) {
//        同理还存在着对字符的缓存流,方法都差不多，但特别的就是可以读readline一行，不包括换行
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            fileReader = new FileReader("./src/io/test_file/test.txt");
//            不可以指定字符集
            bufferedReader =  new BufferedReader(fileReader);

            int read = bufferedReader.read();
            System.out.println(read);

            char[] chars = new char[5];
            read = bufferedReader.read(chars);
            System.out.println(new String(chars));

//            返回一行，没了放回null
            String a;
            while((a = bufferedReader.readLine()) != null){
                System.out.println(a);
            }

//          同理可以skip reset mark


            fileWriter = new FileWriter("./src/io/test_file/test.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);

//            同理大体方法差不多就是可以write直接传个字符串过去
            char[] chars1 = {'今', '天', '真', '辛', '苦'};
            bufferedWriter.write(chars1);

//            特别如下
            bufferedWriter.write(new String(chars1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
