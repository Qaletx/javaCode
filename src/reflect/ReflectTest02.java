package reflect;

public class ReflectTest02 {
    public static void main(String[] args) {
//        得到了class对象后能搞干嘛？
//        如果这个类是一个普通的类,意味着我们已经获取到了这个类得蓝图，我们可以知道有什么属性，方法，构造器，继承了谁，实现了谁，还可以对
//        之进行new对象，然后正常调用属性与方法

//        首先我们先new一个对象试试水
//        class.newInstance() 可直接调用无参构造器new一个对象,但此方法已经淘汰,因为你没获得它得
//        无参构造器就可以new所以不安全,但便捷呀

        Class<?> aClass = null;
        try {
            aClass = Class.forName("reflect.StudentReflectTest");
            Object object1 = aClass.newInstance();
            System.out.println(object1);

//            可见class表示很多东西,可以是接口,可以是个enum的类,可以是个注解类

//            class中有实例方法可以判断是哪种
//              常用的如下

//            判断是不是一个接口
            System.out.println(aClass.isInterface());
//            判断是不是ENUM类
            System.out.println(aClass.isEnum());
//            判断是不是个注解类
            System.out.println(aClass.isAnnotation());
//            判断是不是个匿名类
            System.out.println(aClass.isAnonymousClass());
//            判断是不是内部类或成员类
            System.out.println(aClass.isMemberClass());
//            判断是不是Array类
            System.out.println(aClass.isArray());
//            判断是不是原始类型（boolean、char、byte、short、int、long、float、double）
            System.out.println(aClass.isPrimitive());


//          特别的此为判断该对象是不是该class类的实例
            System.out.println(aClass.isInstance(new StudentReflectTest()));
//            判断参数的类是不是该class类的镶套类
            System.out.println(aClass.isNestmateOf(new String().getClass()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
