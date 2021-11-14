package io;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOTest10 {
    public static void main(String msg[]) {
        StudentObjectIOTest studentObjectIOTest = new StudentObjectIOTest(12,"小明");
        ObjectOutputStream objectOutputStream = null;

        ObjectInputStream objectInputStream = null;
//        可见也是一个包装流，为字节流
        try {
             objectOutputStream = new ObjectOutputStream(new FileOutputStream("./src/io/test_file/testObject"));
             objectOutputStream.writeObject(studentObjectIOTest);

             objectInputStream  = new ObjectInputStream(new FileInputStream("./src/io/test_file/testObject"));
//             返回的是个object类，而不能直接用里面的方法和属性，得依靠重写得toString方法
//             当然如果你知道对象是啥，也可以导入类的包，然后强转为它，这样就能正常使用，且可以打破transient
//             Object student = objectInputStream.readObject();
//            System.out.println(student);
//false可见它是以文件中得对象为参考，然后重新new了个对象
//            System.out.println(student==studentObjectIOTest);


//            很显然打破了transient
            StudentObjectIOTest studentObjectIOTest1 = (StudentObjectIOTest) objectInputStream.readObject();
            System.out.println(studentObjectIOTest.getName());

//           因为list也是一个类，所以可以用它实现一次性实例化多个类
            List<StudentObjectIOTest> lists = new ArrayList<>();
            lists.add(new StudentObjectIOTest(1,"小刚"));
            lists.add(new StudentObjectIOTest(2,"小红"));
            lists.add(new StudentObjectIOTest(3,"小金"));
            lists.add(new StudentObjectIOTest(4,"小铁"));
            objectOutputStream.writeObject(lists);

//            List<Object> lists1 = (List<Object>) objectInputStream.readObject();
//
//            for (Object objectIOTest : lists1) {
//                System.out.println(objectIOTest);
//            }

//            显然这也不会打破游离，毕竟是存在list中的，属于类似节点流
            List<StudentObjectIOTest> lists1 = (List<StudentObjectIOTest>) objectInputStream.readObject();

            for (StudentObjectIOTest objectIOTest : lists1) {
                System.out.println(objectIOTest);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (studentObjectIOTest != null) {
                try {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
