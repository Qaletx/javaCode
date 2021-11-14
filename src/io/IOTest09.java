package io;

import java.io.*;

public class IOTest09 {
    public static void main(String[] args) throws FileNotFoundException {
//        讲一些关于File类,File类没有继承谁,它是为了方便管理文件和目录的,其实目录和文件都叫FIle
//        可以加入一些路径,路径可以不存在,所以不用抛异常,更不用close flush 因为它不需要读,更不需要写,虽然写会创造文件,但不会创造目录
//        很显然根目录也在src外面
        File file = new File("./src/io/test_file/test.txt");

//        ./src/io/test_file/test.txt

       /* if(!file.exists())
        {
//            全部以目录的形式创建
            file.mkdirs();
        }*/
   /*     if (!file.exists())
        {
//            特别的创建文件,如果所给的路径后面一直是没有的,显然jvm不知道要创目录还是文件,更不可能多个文件一起
//            自然需要抛出异常,所以只会创遇不到的第一个路径为文件,且后面必须没路径
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

//        获得其名字[最后的目录或文件(包括后缀),其实是根据你传进去的参数最后一个]
        System.out.println(file.getName());
//      相对路径，如果是从根路径为参数的，输出原来的，其实都输出原来的，也就是说并不是真正意义上的相对路径
        System.out.println(file.getPath());
//        如果是从根开始的，则输出绝对路径，其他输出原来的，也就是参数
        System.out.println(file.getAbsolutePath());
//         输出的是父路径即上一层的相对路径，也不是真正的相对路径
        System.out.println(file.getParent());
//        判断路径是否为绝对路径
        System.out.println(file.isAbsolute());
//        判断是否是文件,此要存在且是文件才为true
        System.out.println(file.isFile());
//        判断是否为目录,此要存在并且是目录才是true
        System.out.println(file.isDirectory());
//       文件的大小
        System.out.println(file.length());
//        文件的距离1970年到现在的最后一次修改时间的毫秒数
        System.out.println(file.lastModified());
//        获得该路径下所有子文件，每个子文件都有如上方法
        File[] files = file.listFiles();


//        File对象都可以传给刚才学的io当路径

//        补充一点
//       之前的字节流和字符流都不能传递字符集字符串，不便于中文的输出，java提供了俩个类实现字符流到字符集的变化，而且能加字符集
        FileInputStream fileInputStream = new FileInputStream("a");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        FileOutputStream fileOutputStream = new FileOutputStream("a");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
