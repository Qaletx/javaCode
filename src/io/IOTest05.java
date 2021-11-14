package io;

import javax.xml.transform.Source;
import java.io.*;

public class IOTest05 {
    public static void main(String[] args) {
//          java提供了一个包装流已解决文件字符流与字节流不断访问硬盘写入写出的问题，即使有数组的支持也太慢了
//           字节缓冲输入的使用方法基本和字节输入流一致，但会提供8m的看不见的缓冲，使得你一read一下，就把8m的东西读入缓冲中了
        FileInputStream fileInputStream = null;
//        设在外面，因为只需要关闭包装流就行
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileInputStream = new FileInputStream("./src/io/test_file/test.txt");

//            必须在节点流完备后才进行包装
//            不可以指定字符集
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            int read = bufferedInputStream.read();

//           来个byte数组
            byte[] bytes = new byte[4];
            bufferedInputStream.read(bytes);
            System.out.println(new String(bytes));

//            还提供了mark()与reset() 前者是设置标志位，允许后面读几位，没超过，则在后面reset一下就会将文件针返回到此
//            特别的，因为设置了缓存，所以还有有个针在缓存中，但mark针对的很显然是文件的针，所以往往是不动的，所以即使超过了也没啥
//            而且，按理来说也可以在fielinputstream中用，但很显然不可以，是java的一个bug
            bufferedInputStream.mark(100);
            int length = 0;
            while((length = bufferedInputStream.read(bytes)) != -1)
            {
                System.out.println(new String(bytes,0,length));
            }

//          同理也有skip与availiable
           /* bufferedInputStream.skip(5);
            bufferedInputStream.available();*/
//            用reset回到标记点，如果无标记reset则java.io.IOException: Resetting to invalid mark
            bufferedInputStream.reset();
            System.out.println("---------------------------------------------");
            while((length = bufferedInputStream.read(bytes)) != -1)
            {
                System.out.println(new String(bytes,0,length));
            }

//            以下是字节缓冲输出流,基本用法和FileOutputStream一样，只不过有缓存，先将存到缓存中，毕竟内存对缓存操作，传输更快

            fileOutputStream = new FileOutputStream("./src/io/test_file/test.txt", true);
            //            不可以指定字符集
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(new byte[]{2,45,67,8});


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedInputStream != null) {
                try {
//                    关闭最外层就可以
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
