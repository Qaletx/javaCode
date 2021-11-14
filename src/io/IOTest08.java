package io;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class IOTest08 {
    public static void main(String[] args) {
        try {
//            特别地该类可以直接传个路径，也可以传一个字节节点流，但后者可以写true追加，
//            而标准输出流是不用close的，即使当包装流时也不用,也不用flush
//            PrintStream printStream = new PrintStream("./src/io/test_file/test.txt");
            FileOutputStream fileOutputStream = new FileOutputStream("./src/io/test_file/test.txt",true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.println("asdassd");
            printStream.printf("%d,%d",1,2);
            printStream.format("我是认真的\n");
//            可见一旦设置了就sout总是朝这个文件了
            IOTest08.test();
//            以下不行,不支持传这个参数,必须用包装流
//            System.setOut(new PrintStream(FileDescriptor.out);


//            将之重新设置回来,其中FileDescriptor.out表示标准输出流的屏幕文件
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println("我也是来测试的");

//            或者在设置之前保存,之后设置回来

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void test(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./src/io/test_file/test.txt", true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            System.setOut(printStream);
            System.out.println("我是来测试的");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
