package thread;

//了解下多线程的调用其他方法和传参问题
public class Thread02 {
    public static void main(String[] args) {
//调用其他方法，我们知道代码区是共享的，所以类都是共享的，自然可以随意调用，但不同run调用是在不同的栈中压入方法

//在多线程的异步开发模式下，数据的传递和返回和同步开发模式有很大的区别。
// 由于线程的运行和结束是不可预料的(需要抢夺时间片)，因此，在传递和返回数据时就无法象函数一样通过函数传参数和return语句来返回数据。

//        以下通过俩种方法实现传递参数
//        第一种就是new 线程蓝图对象时传初始化的参数，并将参数存在对象属性中，如果有很多可以使用集合，但这样也是挺不方便的
//        第二种就是在线程蓝图中创建一些存数据的对象属性，用setXXX方法设置或如果是public的可以直接赋值
//        ，其实跟第一种也差不多,一些静态属性自然可以用类直接赋值


    }
}