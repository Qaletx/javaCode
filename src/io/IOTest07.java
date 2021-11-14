package io;

import com.sun.tools.javac.Main;

import java.io.*;

public class IOTest07 {
    public static void main(String[] args) {
//        数据输入输出流，用于加密，即把java的数据都存起来，数据的输入和输出应该配套使用，且注意前后输出的数据类型的顺序
//        是一个继承与FileInputStream得一个包装流类，意味着它也是个字节流
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream  = null;
        try {
            fileOutputStream = new FileOutputStream("./src/io/test_file/test_java_data");
            dataOutputStream = new DataOutputStream(fileOutputStream);

//            继承了父辈得能写入一个byte数组
            dataOutputStream.write(new byte[]{2,4,5,6});
//            基本数据类型都能写，但不能传包装类，包括String
            dataOutputStream.writeInt(2);
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeFloat((float) 1.4);
            dataOutputStream.writeDouble(1.5);
            dataOutputStream.writeLong(2);
            dataOutputStream.writeShort(3);
            dataOutputStream.writeChar('a');
//            特别的写多个字符,但只是多个字符，不是字符串
            dataOutputStream.writeChars("dasd");


            fileInputStream = new FileInputStream("./src/io/test_file/test_java_data");
            dataInputStream = new DataInputStream(fileInputStream);
//            同理继承了父类的方法
            byte[] bytes = new byte[4];
            dataInputStream.read(bytes);
            System.out.println(new String(bytes));
            System.out.println(dataInputStream.readInt());
            System.out.println(dataInputStream.readBoolean());
            System.out.println(dataInputStream.readFloat());
            System.out.println(dataInputStream.readDouble());
            System.out.println(dataInputStream.readLong());
            System.out.println(dataInputStream.readShort());
            System.out.println(dataInputStream.readChar());
            System.out.println(dataInputStream.readChar());

//            也有
//            dataInputStream.skip(1);
//            dataInputStream.available();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataOutputStream != null) {

                try {
                    dataOutputStream.flush();
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dataOutputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
