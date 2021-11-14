package commomjar;

import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class ArrayUtilsJar {
    public static void main(String[] args) {
        int[] int01 = new int[]{2, 3, 4, 5, 3};
        Integer[] int02 = new Integer[]{6, 7, 8, 9};
        int[] int03 = new int[]{10,11,12,13};
        int[] int04 = null;

//        数据类型的转换

//        创建一个指定元素类型的数组，运用的泛型，非常好用，可以用于创造数组,但因为运用的泛型，所以不能创基本数组类型的数组
        Integer[] integers = ArrayUtils.toArray(1, 2, 3, 4);
//        注意会自动检测元素以便创造啥样类型的数组，但数据类型不统一就创造可序列化数组，这是我们不想看到的
        Serializable[] error = ArrayUtils.toArray(1, "32", 0.4);
//        创object数组
        Object[] objects = ArrayUtils.toArray();
//        创String类型的空数组
        String[] strings = ArrayUtils.<String>toArray();

//        将基本类型的数组复刻为一个对应包装类型的数组
        Integer[] integers1 = ArrayUtils.toObject(int01);

//        将包装类型的数组复刻为一个对应基本类型的数组
        int[] ints2 = ArrayUtils.toPrimitive(int02);




//        以下为增加，插入，合并，复制,删除方法等都是返回一个新的数组，除了reverse是改变原数组
//        尾部增加(好处是不会改变原数组内容)
        int[] addTest01 = ArrayUtils.add(int01,2);
        System.out.println(Arrays.toString(addTest01));
//        很显然也有加在头部的，但是可以用insert代替
        int[] ints3 = ArrayUtils.addFirst(int01, 4);

//       向指定下标插入一个元素,已经淘汰，因为有insert方法
        int[]  addTest02 = ArrayUtils.add(int01,2,3);
        System.out.println(Arrays.toString(addTest02));

//        插入,第一参数为所插的下标，第二个为数组，第三个为可变长数组，也是不改变原数组
        int[] insert = ArrayUtils.insert(1, int01, 2, 3, 4, 5);


//        合并俩个数组，并返回新的数组(其实后一个参数用得是可变长参数，也可以传m个相同类型的元素)
        int[] addTest03 = ArrayUtils.addAll(int01,int03);
        int[] addTest04 = ArrayUtils.addAll(int01,6,7,8,9,10);
        System.out.println(Arrays.toString(addTest03));
        System.out.println(Arrays.toString(addTest04));

//      删除元素也是返回一个新的数组,发现没有即返回新数组，从而达到复制数组的目的
//      通过索引删除一个元素
        int[] remove = ArrayUtils.remove(int01, 1);
//      通过元素删除一个元素
        int[] ints1 = ArrayUtils.removeElement(int01, 12);
//        remove后面加上个All就第二个参数是可变长数组
        System.out.println("达到复制数组的目的" + Arrays.toString(ints1));

//        复制的方法
        int[] clone = ArrayUtils.clone(int01);
        System.out.println(Arrays.toString(clone));

//        将null数组转为空数组
        int[] ints = ArrayUtils.nullToEmpty(int04);
        System.out.println(Arrays.toString(ints));

//




//       以下为获取的一些方法
//        获取长度(显然任何数组都是一个对象都可以length，但length既非属性又非方法，而是会被编译器进行优化的指令)，返回一个int
        int length = ArrayUtils.getLength(int01);
//        获得对应索引的值(可以使用，但没必要)，且不适用于元素是基本类型的
        Integer integer = ArrayUtils.get(int02, 2);
//        没有会返回默认值,该默认值类型同数组的元素类型
        Integer integer1 = ArrayUtils.get(int02, 10, 4);

//        获得指定元素的索引，从0开始找的，也可以指定开始索引，找不到返回-1,返回第一个找到的
//        注意不要和indexexof混了，后者返回的是个Bitset里面存的多个索引,但目前不知道如何取元素
        int index01 = ArrayUtils.indexOf(int01, 4);
        int index02 = ArrayUtils.indexOf(int01, 2, 1);
        BitSet bitSet = ArrayUtils.indexesOf(int01, 3);
        System.out.println(index01+" "+index02);
        System.out.println(bitSet);
//        很显然也有从后面找的
        int i = ArrayUtils.lastIndexOf(int01, 2);

//      检测数组中是否有该元素
        boolean contains = ArrayUtils.contains(int01, 2);

//       检测数组是否为空和检测数组是否非空
        boolean empty = ArrayUtils.isEmpty(int02);
        boolean notEmpty = ArrayUtils.isNotEmpty(int02);

//        检测俩个数组长度是否相当,注意俩个数组类型严格意义上要相同
        boolean sameLength = ArrayUtils.isSameLength(int01, int02);

//        判断数组是否自然升序，或按某种比较器排序而来的,即后面可以加个比较器
        boolean sorted = ArrayUtils.isSorted(int01);
        System.out.println(sorted);

//        截取数组(左闭右开，不能为负值，但不会报错)
        int[] subarray = ArrayUtils.subarray(int01, 0, 5);
        System.out.println(Arrays.toString(subarray));

//        获得String用于打印
//        与Arrays的toString不同在于后者参数用非null修饰不能传null，前者可以传null并且给了个默认String供表现,
//        如果没给默认值，就返回"{}"
        System.out.println(ArrayUtils.toString(null));
        System.out.println(ArrayUtils.toString(null,"我是空的"));

//        以下为会改变原数组数据的方法


//        非常好用的反转方法,
//        全部反转
        ArrayUtils.reverse(int01);
//        左闭右开反转（索引不能为负数，不然没效果，因为负值在0左边）
        ArrayUtils.reverse(int01,0,5);
        System.out.println(Arrays.toString(int01));

//        随机排序，也可以给一个随机对象
        ArrayUtils.shuffle(int01);
//        给定俩个索引交换元素，后面可以再给定一个整数，提供按长度俩俩交换
        ArrayUtils.swap(int01,1,2);
        ArrayUtils.swap(int01,1,2,1);

    }
}

