package reflect;


import java.util.Map;

public class ReflectTest01 {
//    我们之间的序列和反序列是用于本地存在的类文件，然后根据存着对象的文件进行拷贝
//    现在讲的反射则是直接对class文件进行操作，实现类似反编译的手法
//    java中有专门的一个Class类是文件class的映射，能通过该类以class文件为模板，做很多事
    public static void main(String[] args) {
//        首先要获得class文件的对象
//        第一种方法 Class.forname("完整类名")，不能导包后写简名
//        很显然包名的作用就是路径，便于类加载器去找后,放在静态代码区,会执行静态代码块,可用此来执行代码块的代码,而没有其他没必要的操作
//        缺点是加泛型比较麻烦,需要强转一次
//        Class<StudentReflectTest> aClass1 = (Class<StudentReflectTest>)Class.forName("reflect.StudentReflectTest");
//
        Class class01 = null;
        try {
            class01 = Class.forName("reflect.StudentReflectTest");
//           直接输出其实调用的是toString方法,返回得是class/interface 类全名  特别得是没带修饰符

            System.out.println(class01);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//      第二种方法，直接类.class,任何属性都有,即使基本类型也有,因为基本类型有包装类型,它会标记并转为包装类型,再用class属性
//        可见class是个类属性或叫静态方法，该属性是隐藏的，在源代码中见不到，返回的是一个Class对象的实例
//        特别的是因为只需要类中的一个一个静态属性,所以类加载器找到它,然后就返回它的静态属性而不会将之放在代码区
//        故不会执行静态代码块,需要导包或者写全名

        System.out.println(int.class);
        Class<Object> objectClass = Object.class;
        Class<StudentReflectTest> studentReflectTestClass = StudentReflectTest.class;
        System.out.println(studentReflectTestClass);

//        第三种方法则需要用到具体类的实例，利用具体类的实例中继承于Object中的getClass获得
//        对象.getClass()

        StudentReflectTest studentReflectTest = new StudentReflectTest();
        Class<? extends StudentReflectTest> aClass = studentReflectTest.getClass();

        System.out.println(aClass == studentReflectTestClass);

    }
}
