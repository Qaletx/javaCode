package reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectTest03 {


//  如何获取全部的Field属性?
    public static void main(String[] args) {
        Class<?> aClass = null;

        try {
            aClass = Class.forName("reflect.StudentReflectTest");
//          首先我们得来学一下啊get系列和getDeclared系列,前者只能获得自己的和继承父类得public元素而
//          后者可以获得的其本类和继承父类的全部元素

//          属性的get系列
//          根据属性名字获得属性,前提该属性得是public
            Field no = aClass.getField("no");
//          获得其全部public属性
            Field[] fields = aClass.getFields();
            System.out.println(fields.length);

//          属性的getDeclared系类,同上面,只不过多了个Declared
            Field name = aClass.getDeclaredField("name");
            Field[] declaredFields = aClass.getDeclaredFields();
            System.out.println(declaredFields.length);
//          toString输出的为其全名参数外加全名变量名
//           protected java.lang.String reflect.StudentReflectTest.name
            System.out.println(name);

//          获得属性的对象就可以对之进行操作,得到其名字,修饰符,类型

//          名字
            System.out.println(name.getName());

//          类型(返回的是class)
            System.out.println(no.getType());

//          修饰符,非常注意返回的是个对应修饰符的编号,因为源代码把不同修饰符的组合形成的进行编号,然后
//          用Modifier自己写的toString方法(不是Object的toString方法)进行翻译返回
            int modifiers = no.getModifiers();
            System.out.println(Modifier.toString(no.getModifiers()));

            System.out.println("---------------------------------------------------------------------------------");

//           其实同理得到class中的Method也是get系列个getDeclared系类
//            但需要知道取特定的,属性区分其实只需要给定名字就行,而方法有重构,意味着需要传名字和参数的class的对象才可以区分

//            get系列
            Method method = aClass.getMethod("testRe", int.class, int.class);
            Method[] methods = aClass.getMethods();

//            getDeclared系列
            Method testRe = aClass.getDeclaredMethod("testRe", String.class, String.class);
            Method[] declaredMethods = aClass.getDeclaredMethods();
//            其toString会将其方法除方法题全部输出,参数的类型全名外加自己的方法名也是全名
//            public void reflect.StudentReflectTest.testRe(java.lang.String,java.lang.String)
            System.out.println(testRe);
//          获得完就可以去了解它的名字,返回值类型,修饰符,参数(参数返回的是属性Parameter,用法类似Field,但getName无法正常表示)

//            名字
            System.out.println(method.getName());

//            返回值类型
            System.out.println(method.getReturnType().getName());

//            修饰符
            System.out.println(Modifier.toString(method.getModifiers()));

//            参数( 目前发现形参的名字无法正常表示,因为名字不重要,会以arg数字表示),所以也可以用以下方法只获得参数的类型Class数组
//            Class<?>[] parameterTypes = method.getParameterTypes();

            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
//                目前发现形参的名字无法正常表示,会以arg数字表示
                System.out.println(parameter.getType().getSimpleName()+" "+parameter.getName());
            }

            System.out.println("------------------------------------------------------------------------");

//            获得构造器,因为构造器名字和类名相同,所以取特点的它只需要传参数的class对象就行
//            其他基本上与上面相同,分get和getDeclared系列

//            get系类
            Constructor<?> constructor = aClass.getConstructor(int.class, String.class, String.class, long.class);
            Constructor<?>[] constructors = aClass.getConstructors();

//            getDeclared系列
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(int.class, String.class, String.class, long.class);
            Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
//         同理
            System.out.println(1+""+constructor);
//            同理得到这个构造器对象就可以知道其修饰符,名字和参数

//            名字(该名为类名一致,为完整名,但却没简单名得方法,所以建以直接输出类得简单名字与之代替)
            System.out.println(constructor.getName());

//            修饰符
            System.out.println(Modifier.toString(constructor.getModifiers()));

//            参数(与方法得一样)
            Parameter[] parameters1 = constructor.getParameters();
            for (Parameter parameter : parameters1) {
                System.out.println(parameter.getType().getName()+" "+parameter.getName());
            }


//            我们知道类有自己的修饰符和名字,和继承的父类,实现的接口,修饰的注释

//            名字
//            获得类的全名即完整名
            System.out.println(aClass.getName());
//            获得类的简单名字
            System.out.println(aClass.getSimpleName());

//            修饰符
            System.out.println(Modifier.toString(aClass.getModifiers()));

//            继承的父类
            Class<?> superclass = aClass.getSuperclass();
            System.out.println(superclass);

//            实现的接口
            Class<?>[] interfaces = aClass.getInterfaces();
            System.out.println(interfaces);

//            注解
//            注解有get系列和getDeclared系列,所传的参数为注解的一个class类实例,因为常常在开发中我们要拿到一个类的特定注解
//            特别的在Field Method Constructor皆有如下

//                      判断该对象上是否有该注解
            System.out.println(aClass.isAnnotationPresent(Override.class));

//            get系列
            Override annotation = aClass.getAnnotation(Override.class);
            Annotation[] annotations = aClass.getAnnotations();

//            getDeclared系列
            Override declaredAnnotation = aClass.getDeclaredAnnotation(Override.class);
            Annotation[] declaredAnnotations = aClass.getDeclaredAnnotations();
//          注意特别的，此获得的注解则是一个特定对象，有特性值
//            所以获得注解后就可以的通过该注解对象.变量名() 拿到变量的值


//            其他

//            非常注意只有Class的getName才能获得其全名,而其他不行,但如何得到Field对象得类型全名呢,可以先得到其类型的class再得
//             System.out.println(name.getType().getName());
//            而方法和属性得全名我们就不知道如何获取了，也没必要获取


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
