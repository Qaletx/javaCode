package io;
import java.io.*;
public class IOTest01 {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            /*
            * 可见文件的当前路径是从src外面开始的，而且记得文件名字得写全，包括后缀
            * 而/根目录是所在盘*/
//            必须用双引号
//         不能指定字符集
//         路径可以写中文
           fileInputStream = new FileInputStream("./src/io/test_file/test.txt");


//           read的第一种用法，且注意返回的是翻译后的int 读取一个，会抛出异常，即文件异常导致读取中断的异常
//            失败返回负一
           int letter = fileInputStream.read();
            System.out.println(letter);


//            第二种方法就是创个byte数组读多个，成功返回读取的个数，失败为-1，这样减少与硬盘的交互
//            再用new字符串的形式输出出字符串
//            byte[] multi = new byte[5];
//            int letter = fileInputStream.read(multi);
//            System.out.println(letter);
//            System.out.println(new String(multi));


//            以下为读取整个文件的方法，最近不到数组个数的话，只会覆盖数组前面几个元素
            byte[] bytes = new byte[2];
            int number;
            while((number = fileInputStream.read(bytes)) != -1)
            {
//                不能将数组整个转为String输出，必须指定个数
//                而且不建议用于中文的文件，因为在utf-8读取的中文字节数是不定的，可能这个中文要3个字节，而你只读到2个字节就
//                翻译为String，除非建个很大的byte数组一次性读出并转为String，或用utf-16，然后byte设为偶数个
                System.out.println(new String(bytes,0,number,"utf-8"));
            }

//           补充存在以下俩个方法，一个跳过几个,返回成功跳过的数量,不能为负，不会报错
//            long skip = fileInputStream.skip(2);
//            以下是还有几个字节可以读，一般用于循环或创一个同文章大小的byte数组
//            int available = fileInputStream.available();
//            System.out.println(skip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    }
}
