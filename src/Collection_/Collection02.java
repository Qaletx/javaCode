package Collection_;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Collection02 {
    public static void main(String[] args) {
//        了解到不同接口的方法后，它们的实现类就很简单了，
//        它们的实现类存的是类的地址，即使你传个1，它也会new Integer

//        List集合存储元素特点：有序可重复
//        有序：List集合中的元素有下标。
//        从0开始，以1递增，存进去的顺序什么取出的顺序就是什么。
//        可重复：存储一个1，还可以再存储1.
//
//        Set集合则是无序,没有下标

//        Map比Set多了value 但没有继承Iterable接口不能简单得用迭代器
/*
ArrayList集合：
    1、默认初始化容量10（底层先创建了一个长度为0的数组，当添加第一个元素的时候，初始化容量10。）
    2、集合底层是一个Object[]数组。
    3、构造方法：
        new ArrayList();
        new ArrayList(20);
        new ArrayList(Collection<? extends E> c)
    4、ArrayList集合的扩容：
        当添加最后一个满时，增长到原容量的1.5倍。
        ArrayList集合底层是数组，怎么优化？
            尽可能少的扩容。因为数组扩容效率比较低，建议在使用ArrayList集合
            的时候预估计元素的个数，给定一个初始化容量。
    5、数组优点：
        检索效率比较高。（每个元素占用空间大小相同，内存地址是连续的，知道首元素内存地址，
        然后知道下标，通过数学表达式计算出元素的内存地址，所以检索效率最高。）
    6、数组缺点：
        随机增删元素效率比较低。
        另外数组无法存储大数据量。（很难找到一块非常巨大的连续的内存空间。）
    7、向数组末尾添加元素，效率很高，不受影响。
    8、面试官经常问的一个问题？
        这么多的集合中，你用哪个集合最多？
            答：ArrayList集合。
            因为往数组末尾添加元素，效率不受影响。
            另外，我们检索/查找某个元素的操作比较多。

    7、ArrayList集合是非线程安全的。（不是线程安全的集合。）
 */


/*
LinkedList
构造方法:
LinkedList()
LinkedList(Collection<? extends E> c)
链表的优点：
    由于链表上的元素在空间存储上内存地址不连续。
    所以随机增删元素的时候不会有大量元素位移，因此随机增删效率较高。
    在以后的开发中，如果遇到随机增删集合中元素的业务比较多时，建议
    使用LinkedList。

链表的缺点：
    不能通过数学表达式计算被查找元素的内存地址，每一次查找都是从头
    节点开始遍历，直到找到为止。所以LinkedList集合检索/查找的效率
    较低，即使可以用get下标引用，但是顺序读取的，而不是随机读取的。

    ArrayList：把检索发挥到极致。（末尾添加元素效率还是很高的。）
    LinkedList：把随机增删发挥到极致。
    加元素都是往末尾添加，所以ArrayList用的比LinkedList多。

    自己独有的常用的方法(push与add一样插到头但是无返回值 addXXX offerXXX都是一样的,没有后缀就是前，添加的offer add没前缀是后)
    void push(E e)
    void addFirst(E e)
    void addLast(E e)
    boolean offer(E e)
    boolean offerFirst(E e)
    boolean offerLast(E e)
    E poll()        删除第一个并返回,表为空异常
    E pollFirst()   删除第一个并返回,如表为空返回NULL
    E pollLast()    删除最后一个并返回,如表为空返回NULL
    E removeFirst()
    E removeLast()
    boolean removeFirstOccurrence(Object o)  从前开始删除第一个匹配的
    boolean removeLastOccurrence(Object o)   从后开始删除第一个匹配的
    E peek()    返回但不删除第一个,表为空异常
    E peekFirst()   返回但不删除第一个,如表为空返回NULL
    E peekLast()     返回但不删除最后一个,如表为空返回NULL
    E getFirst()
    E getLast()
 */

/*
Vector：
构造方法:
Vector()
Vector(Collection<? extends E> c)
Vector(int initialCapacity)
Vector(int initialCapacity, int capacityIncrement)
capacityIncrement默认为2
    1、底层也是一个数组。
    2、初始化容量：10
    3、怎么扩容的？
        扩容之后是原容量的2倍。
        10--> 20 --> 40 --> 80
        而ArrayList集合扩容特点：
        ArrayList集合扩容是原容量1.5倍。

    4、Vector中所有的方法都是线程同步的，都带有synchronized关键字，
    是线程安全的。效率比较低，使用较少了。

    5、怎么将一个线程不安全的ArrayList集合转换成线程安全的呢？
        使用集合工具类：
            java.util.Collections;

            java.util.Collection 是集合接口。
            java.util.Collections 是集合工具类。
 */

//        Set中的实现类,与Map不同点就是只有一个Key无Value
/*
  HashSet
  构造方法:
  HashSet()
HashSet(Collection<? extends E> c)
HashSet(int initialCapacity)  建议为2的幂次
HashSet(int initialCapacity, float loadFactor)
 float loadFactor默认为0.75 当达到时容量夸大为俩倍

    HashSet集合底层是哈希表/散列表的数据结构。
    会通过调用自身重写Object的hashCode()获得散列值,并再通过哈希算法,转为对应数组下标,数组存的为LinkList,再通过重写Object的equal方法
    与对应的链表上的元素进行比较,也就是说对于自定义的类,务必重写这俩个方法,而且建议用多态限制HashSet的元素类型,防止抛异常
1、存储时顺序和取出的顺序不同。
2、不可重复。
3、放到HashSet集合中的元素实际上是放到HashMap集合的key部分了。

对于String Integer这些已经重写了
 */

/*
  TreeSet
  构造方法:
TreeSet()
TreeSet(Collection<? extends E> c)
TreeSet(Comparator<? super E> comparator)
TreeSet(SortedSet<E> s) 复制s并使用同个比较方法

1、TreeSet集合底层实际上是一个TreeMap
2、TreeMap集合底层是一个红黑树。
3、放到TreeSet集合中的元素，等同于放到TreeMap集合key部分了。
4、TreeSet集合中的元素：无序不可重复，但是可以按照元素的大小顺序自动排序,前提是全部元素一样类型,
否则会抛出异常
称为：可排序集合。

对于String,Integer这些已经 Comparable接口,对应的比较就是比较其值,而对于我们自己创的类,其属性可能很多,这样
jvm就不知道要如何通过比较创建红黑树,就会抛异常,那么必须通过以下


TreeSet集合中元素可排序的二种方式：使用比较器的方式。
最终的结论：
    放到TreeSet或者TreeMap集合key部分的元素要想做到排序,包括两种方式：
        第一种：放在集合中的元素实现java.lang.Comparable接口,并实现compareTo方法
    compareTo方法的返回值很重要：
    q.compareTo(Customer c)  q是准备插入的值,c是红黑树的节点,q分别于不同c对比,当q==c q>c,q<c
    可以分别返回0,>0,<0表现如下(在sort表现是>就交换)
        返回0表示相同，value会覆盖。
        返回>0，会继续在右子树上找。【10 - 9 = 1 ，1 > 0的说明左边这个数字比较大。所以在右子树上找。】 交换
        返回<0，会继续在左子树上找。
         public int compareTo(Customer c) { return this.time-c.time}

        第二种：在构造TreeSet或者TreeMap集合的时候给它传一个比较器对象,该比较器是实现Comparator<自定义类>中的一个类
        int compare(自定义类 o1, 自定义类 o2),原理如上,可以用匿名内部类

Comparable和Comparator怎么选择呢？
    当比较规则不会发生改变的时候，或者说当比较规则只有1个的时候，建议实现Comparable接口。
    如果比较规则有多个，并且需要多个比较规则之间频繁切换，建议使用Comparator接口,因为此并不用类直接实现compareTo方法

    Comparator接口的设计符合OCP原则。
 */

//Map,我们知道Map属于比较特别的Set,有Value, key和value都是引用数据类型,key和value都是存储对象的内存地址。它有俩种遍历方法
//        非常注意不能简单的直接用iter遍历,会报错
// 第一种 利用 Set<K> keySet() 获取Map集合所有的key,再用 V get(Object key) 通过key获取value
// 第二种 是利用Set<Map.Entry<K,V>> entrySet()将Map集合转换成Set集合,再遍历Set中的元素,因为Map.Entry<K,V>为多态,实际是Node类的对象
//      Node类有getKey getValue的实例方法
//   第二种效率更高,因为获取key和value都是直接从node对象中获取的属性值,而前者因为取的key为无序,所以需要再到Map中去找value
//
/*
HashMap:
构造方法
HashMap()
HashMap(int initialCapacity) 建议为2的幂次
HashMap(Map<? extends K,? extends V> m)
HashMap(int initialCapacity, float loadFactor)
 float loadFactor默认为0.75 当达到时容量夸大为俩倍+1
 与HashSet特性基本一致,不过此为Map,自有Map的特性
但特殊是 Hashtable的key和value都是不能为null的。
        HashMap集合的key和value都是可以为null的,但是要注意：HashMap集合的key null值只能有一个。
*/

/*Hashtable:
构造方法:Hashtable()
Hashtable(int initialCapacity)
Hashtable(int initialCapacity, float loadFactor)
Hashtable(Map<? extends K,? extends V> t)
 float loadFactor默认为0.75 当达到时容量夸大为俩倍
 非常普通的Map类,就是线程安全的而已比较特别,但不常用
*/

/*Properties:
构造方法:
Properties()
Properties(Properties defaults)  copy defaults创造新的对象
Properties非常常见,系统学习一下它常用的方法
    void load(InputStream inStream/Reader reader)
    Object setProperty(String key, String value)   返回value
    String getProperty(String key)  找不到估计会保错
    String getProperty(String key, String defaultValue)      找不到估计返回默认值
    void store(OutputStream out, String comments)  将键值对以字符流写入,后面的字符串为注释,一般写为""
    void store(Writer writer, String comments)
    void list(PrintStream out) 将键值对输出到标准流,一般填System.out进行调试数据,快速遍历,很好用
 */
/*
TreeMap
构造方法
TreeMap()
TreeMap(Map<? extends K,? extends V> m)
TreeMap(Comparator<? super K> comparator)
TreeMap(SortedMap<K,? extends V> m)
与TreeSet一致
*/
    }

}
