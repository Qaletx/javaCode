package reflect;

import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import java.lang.reflect.Type;

import java.util.HashMap;

import java.util.List;
import java.util.Map;


//反射泛型,即如何获取泛型
//通过指定对应的Class对象，程序可以获得该类里面所有的Field，不管该Field使用private 方法public。获得Field对象后都可以使用getType()来获取其类型。
//
//Class<?> type = f.getType();//获得字段的类型
//
//但此方法只对普通Field有效,若该Field有泛型修饰,则不能准确得到该Field的泛型参数,如Map<String,Integer>;
//得到的Class仅为interface java.util.Map 并不包含其泛型,因为Class是Type的子类所以算是特别的
//为了获得指定Field的泛型类型，我们采用：
//getGenericType()方法,此获得的是Type的一个对象,该类是专门来处理类型的,并不是局限于Class的对象来获得其普通参数
//其实就是多了个Generic，获得方法参数得泛型同理，Type[] types = method.getGenericParameterTypes();只不过从Class对象变为Type得实现类对象

//我们知道接口的对象内存方面绝对是其实现类的对象,能使用ParameterizedType的方法,目前估计就是其实现类.其中Type类只有一个方法还被重写了,
//只能将之强转为它的子接口ParameterizedType因为此多了几个方法,然后此类有如下几个方法(其实现类对),且皆返回Type类型的对象,毕竟参数里面的泛型也可能会有泛型对吧
//Type getRawType()//返回被泛型限制的类型即java.util.Map
//Type[]  getActualTypeArguments()//返回泛型参数类型,不包含被限制的类型,因为泛型可能多个,所以是个数组
//Type getOwnerType() 很少用,返回的是该属性与该类同泛型的类的类型,如对Map.Entry<String, String>使用返回
//                    java.util.Map,因为Map接口就是Map.Entry的所有者,且泛型一致.
//String getTypeName()  当存在泛型时返回结果与单纯输出Type对象一样，而无泛型则是在toString上输出完整类名

//得到的Type可以强转为Class

public class ReflectTest05 <T>  {

    Map<T,Integer> map = null;
    Map.Entry<String, String> mapEntry = null;


    public static void main(String[] args) throws Exception {

        Class clazz = ReflectTest05.class;
        Field map = clazz.getDeclaredField("map");

        System.out.println(map.getType());

//        获得其Type
        Type genericType = map.getGenericType();
//        可见当还为确型时还是为泛型标志
//     java.util.Map<T, java.lang.Integer>
        System.out.println(genericType.getTypeName());
//      java.util.Map<T, java.lang.Integer>
        System.out.println(genericType);


//        强转其子接口，获得多个三个方法
        ParameterizedType genericType1 = (ParameterizedType) genericType;
//        非常注意当无泛型时和class的toString一样
//        interface java.util.Map
        System.out.println(genericType1.getRawType());
        Type[] actualTypeArguments = genericType1.getActualTypeArguments();
//        T
//        class java.lang.Integer
        for (Type actualTypeArgument : actualTypeArguments) {
            System.out.println(actualTypeArgument);
        }
//         null
        System.out.println(genericType1.getOwnerType());

        Field mapEntry = clazz.getDeclaredField("mapEntry");
        Type genericType2 = mapEntry.getGenericType();
        ParameterizedType genericType21 = (ParameterizedType) genericType2;
//        interface java.util.Map
        System.out.println(genericType21.getOwnerType());


//        不凡来获得一下方法中参数的泛型
        Method run = clazz.getDeclaredMethod("run", Map.Entry.class, Map.class);
        Type[] genericParameterTypes = run.getGenericParameterTypes();
        for (int i = 0; i < genericParameterTypes.length; i++) {
            ParameterizedType genericParameterType = (ParameterizedType) genericParameterTypes[i];
            System.out.println(genericParameterType.getRawType().getTypeName());
            Type[] actualTypeArguments1 = genericParameterType.getActualTypeArguments();
            for (Type type : actualTypeArguments1) {
                System.out.println(type.getTypeName());
            }

        }
//      获得类的泛型
//       不好意思，单纯一个泛型的类起初都是T 都是无意义，所以没有方法来获取

//      父类的泛型和接口都有非泛型类来继承或实现，此时泛型会指明，所以获取是有意义的

//        父类只有一个，所以只能获取一个
        Type genericSuperclass = ReflectTest05.class.getGenericSuperclass();

//        接口多个，所以是个数组
        Type[] genericInterfaces = ReflectTest05.class.getGenericInterfaces();

    }
    public static  void run(Map.Entry<String,Integer> mapEntry,Map<String, List> map){}


}

