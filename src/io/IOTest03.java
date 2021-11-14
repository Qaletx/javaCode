package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class IOTest03 {
    public static void main(String[] args) {
        FileReader fileReader = null;
        try {
            //         不能传递字符集

             fileReader = new FileReader("./src/io/test_file/test.txt");
//            可见读取得为俩个字节返回的是一个数据本身
//            失败为-1

            int read1 = fileReader.read();
            System.out.println(read1);
//            可以强转为char
            System.out.println((char)read1);

//            同样可以传个数组
//            目前理解因该是将UTF-8解析后翻译成UTF-16BE存在char中
            char[] chars = new char[11];
            int read = fileReader.read(chars);
            System.out.println(new String(chars));

//            以下为最终版
            int length = 0;
            while((length = fileReader.read(chars)) != -1 )
            {
                System.out.println(new String(chars,0,length));
            }
//            跳过一个字符
            long skip = fileReader.skip(2);
//          无available()，可见available只对字节流有效
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
