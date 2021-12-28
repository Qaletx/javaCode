package Collection_;


import java.util.*;

public class Collection01 {
    public static void main(String[] args) {
//        Map和List和Set属于同级的,也是集合
//        在学好Collection下的集合和Map的前提下，我们的了解下Collection与Map和Iterable,Set,List接口
//        Collection接口它继承Iterable接口，而后者，有以后非常常用的方法
//        Iterator<T> iterator()(返回对应迭代器)
//        Iterator接口中有俩个常用的方法next() hasNext();remove()共其他类实现
//        remove专门用于在遍历时删除集合元素,而Collection中的remove每次结构改变都有要遵循集合规定,重新获得迭代器
//        Collection中常用的方法有如下

/*
        boolean add(E o)    确保此 collection 包含指定的元素（添加操作）。
        boolean addAll(Collection<? extends E> c)    将指定 collection 中的所有元素都添加到此collection 中（可选操作）。
        boolean remove(Object o) 从此 collection 中移除指定元素的单个实例，如果存在的话（可选操作）。
        boolean removeAll(Collection<?> c) 移除此 collection 中那些也包含在指定 collection
        boolean retainAll(Collection<?> c) 移除c集合中不存在的元素
        void clear()    移除此 collection 中的所有元素（可选操作）。
        boolean contains(Object o) 如果此 collection 包含指定的元素，则返回 true。
        boolean containsAll(Collection<?> c) 如果此 collection 包含指定 collection 中的所有元素，则返回 true。
        boolean isEmpty() 如果此 collection 不包含元素，则返回 true。
        int size() 获取集合中元素的个数。
        Iterator<E> iterator() 返回在此 collection 的元素上进行迭代的迭代器。
        Object[] toArray() 返回包含此 collection 中所有元素的数组。
        <T> T[] toArray(T[] a) 返回包含此 collection 中所有元素的数组；返回数组的运行时类型与指定数组的运行时类型相同。

*/
//        Map常用方法如下
/*
        V put(K key, V value) 向Map集合中添加键值对，返回其value
        void  putAll(Map<? extends K,? extends V> m) 将指定 Map中的所有元素都添加到此Map中
        V remove(Object key) 通过key删除键值对
        void clear()    清空Map集合
        boolean containsKey(Object key) 判断Map中是否包含某个key
        boolean containsValue(Object value) 判断Map中是否包含某个value
        boolean isEmpty()   判断Map集合中元素个数是否为0
        V get(Object key) 通过key获取value
        int size() 获取Map集合中键值对的个数。
        Collection<V> values() 获取Map集合中所有的value，返回一个Collection
        Set<K> keySet() 获取Map集合所有的key（所有的键是一个set集合）
        Set<Map.Entry<K,V>> entrySet()将Map集合转换成Set集合


        Map.Entry<K,V>其实是Map中的一个内部接口
        在Map的实现类中，K与V都是封装在一个静态内部类中的(就像c中的结构体)，并实现Map.Entry<K,V>接口

        */


//      Set接口继承的是Collection接口，而且方法相同
//      HashTable多了几个方法,但无需理解,最主要记住它是线程安全的,Collection中只有vector线程安全

/*      List也是继承Collection接口，扩展了自己的一些方法
         void add(int index, Object element) 指定索引插入数据
         Object remove(int index)   指定索引删除数据，并返回该数据
         Object get(int index)    指定索引获得数据
         Object set(int index, Object element)    指定索引替换数据，并返回被替换的数据
         int indexOf(Object o)     从左到右获得指定第一个数据的下标  利用的equal方法
         int lastIndexOf(Object o)   从右到左获得指定第一个数据的下标
         List<E> subList(int fromIndex, int toIndex)   左闭右开返回一个子链

 */
/*
SortedSet则继承Set又有自己一些方法

E first() 返回第一个(最小的)元素
E last()  返回最后一个(最大的)元素
SortedSet<E> headSet(E toElement)   返回小于toElement(开区间)的视图
SortedSet<E> tailSet(E fromElement)  返回大于fromElement(开区间)的视图
SortedSet<E> subSet(E fromElement, E toElement)   返回处于fromElement与toElement（左闭右开）的视图


*/
/*SortedMap则继承Set又有自己一些方法跟SortedSet差不多,就是操作的是key
K firstKey()
K lastKey()
SortedMap<K,V> headMap(K toKey)
SortedMap<K,V> tailMap(K fromKey)
SortedMap<K,V> subMap(K fromKey, K toKey)


* */
//很显然,数组有Arrays工具类,集合也有Collections工具类,里面有很多静态方法,注意在如果集合内元素为自定义类时,
//调用remove等记得重写equal方法,否则比较的是地址
/*
boolean addAll(Collection<? super T> c, T... elements) 当Collection为空时异常,暂时不清楚啥时候返回false
void sort(List<T> list)  //自定义类自然而言是自定义的类接的Comparable
void sort(List<T> list, Comparator<? super T> c)
void shuffle(List<?> 1ist)   打乱顺序:打乱集合顺序。
int binarySearch(List<? extends T> list, T key)
int binarySearch(List<? extends T> list, T key, Comparator<? super T> c)
如果找到关键字，则返回值为关键字在数组中的位置索引，且索引从0开始2、如果没有找到关键字，
返回值为负的插入点值，所谓插入点值就是第一个比关键字大的元素在数组中的位置索引，而且这个位置索引从1开始(0个元素为-1)
boolean replaceAll(List<T> list, T oldVal, T newVal) 当list为空时异常,暂时不清楚啥时候返回false
void reverse(List<?> list)
void rotate(List<?> list, int distance)   移动列表中的元素，负数向左移动，正数向右移动,被覆盖的会移到头部,是元素之间的操作
disjoint(Collection<?> c1, Collection<?> c2) - 如果两个指定 collection 中没有相同的元素，则返回 true
void copy(List<? super T> dest, List<? extends T> src)    将src中的元素cope到scr,并且覆盖相应索引的元素
void fill(List<? super T> list, T obj)
void swap(List<?> list, int i, int j)
T max(Collection<? extends T> coll)
T max(Collection<? extends T> coll, Comparator<? super T> comp)
T min(Collection<? extends T> coll)
T min(Collection<? extends T> coll, Comparator<? super T> comp)
Collection<T> synchronizedCollection(Collection<T> c)
List<T> synchronizedList(List<T> list)
Set<T> synchronizedSet(Set<T> s)
SortedSet<T> synchronizedSortedSet(SortedSet<T> s)
Map<K,V> synchronizedMap(Map<K,V> m)
SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m)
emptyXxx()：返回一个空的不可变的集合对象
singletonXxx()：返回一个只包含指定对象的，不可变的集合对象。
unmodifiableXxx()：返回指定集合对象的不可变视图

*/
    }

}
