package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest04 {
    public static void main(String[] args) {
//        之前讲了,将class实例进行解剖析,现在来讲如何利用class来new对象后进设置和查询属性,调用方法,
//        静态属性和方法则对象参数给为null即可


        Class<?> aClass = null;
        try {

            aClass = Class.forName("reflect.StudentReflectTest");

//        首先看怎么new对象之前我们通过直接Class.newInstance不好,因为没有获得其无参构造,现在来试一下

//        获得无参构造器new对象
            Constructor<?> constructor = aClass.getConstructor();
            Object o = constructor.newInstance();
            System.out.println(o);

//         获得有参构造器new对象
            Constructor<?> constructor1 = aClass.getConstructor(int.class, String.class, String.class, long.class);
            Object o1 = constructor1.newInstance(3, "小绿", "三年二班", 45);
            System.out.println(o1);

//           实例的属性的设置和属性的获得
//           要素一就是之前建立的对象，要素二就是通过类实例找到的属性实例
//            属性实例.set(对象实例，值)       属性实例.get(对象实例)

//          设置对象的特定属性

//            最好用getDeclared去获得属性，因为它什么修饰符都能获取，但private(其他不会)虽然能获取但无法设置值，更无法获取
            Field score = aClass.getDeclaredField("score");

            score.setAccessible(true);
//            会抛出异常,但加上上面的一句话就会可以访问，这也是反射的厉害之处
            score.set(o1,90);
            System.out.println(o1);

//            获得对象的特定属性的值
            System.out.println(score.get(o1));

//            方法的调用也是非常简单,同理需要要素-为类的实例对象，要素二为方法对象，再传参就行
//            同理获得的private无法调用
//            方法对象.invoke(对象实例，参数1，参数2)
            Method declaredMethod = aClass.getDeclaredMethod("didThings");
            declaredMethod.setAccessible(true);
//            抛出异常，得在之前打破封装
            declaredMethod.invoke(o1);

//            特别的静态类我们也得先获得其方法实例才能调用它

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
