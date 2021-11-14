package thread.concurrent;

//我们之前讲过，在线程类蓝图里面可以写个共享数据，当很有可能会出现安全问题
/*
     1，什么时候数据在多线程并发的环境下会存在安全问题呢？
		三个条件：
			条件1：多线程并发。
			条件2：有共享数据。
			条件3：共享数据有修改的行为。

		满足以上3个条件之后，就会存在线程安全问题。

	2，怎么解决线程安全问题呢？
		当多线程并发的环境下，有共享数据，并且这个数据还会被修改，此时就存在
		 线程安全问题，怎么解决这个问题？
            在异步编程的基础上，使得某些代码块是同步执行的
                即线程排队执行。（不能并发）。
                用排队执行解决线程安全问题。
                这种机制被称为：线程同步机制。


		线程同步就是线程排队了，线程排队了就会牺牲一部分效率，没办法，数据安全
		第一位，只有数据安全了，我们才可以谈效率。数据不安全，没有效率的事儿。

哪些数据会存在共享问题？
3、Java中有三大变量？【重要的内容。】

	实例变量：在堆中。

	静态变量：在方法区。

	局部变量：在栈中。

	以上三大变量中：
		局部变量永远都不会存在线程安全问题。
		因为局部变量不共享。（一个线程一个栈。）
		局部变量在栈中。所以局部变量永远都不会共享。

	实例变量在堆中，堆只有1个。
	静态变量在方法区中，方法区只有1个。
	堆和方法区都是多线程共享的，所以可能存在线程安全问题。

	即局部变量+常量：不会有线程安全问题。
	成员变量：可能会有线程安全问题。
*/

//如何实现将某个代码块写为同步?
//就得用synchronized ()同步代码块语句了，里面括号的东西非常重要，是个标识，在java中任何属性都可以来充当这个标识，也就是锁
//该锁的挑选异常重要，写的是不同线程对象中共有的一部分东西，只要线程遇到该语句都会进去一个阻塞状态去，尝试去拿这把锁
//当没有这把锁时就始终处于阻塞状态，并不断得去拿

//synchronized ()语句可以写在哪？
//三处地方
//1，一般是写在共享类得的更改方法中，那么存在括号，synchronized ()，中括号可以任意写锁如this等
//2，充当修饰符写于实例方法上，但是这样锁的是this
//3，充当修饰符写于静态方法上，此时是锁的是类锁

//注意synchronized语句越少效率越高，而且所谓的锁只是一个标志而已，并不是被拿了，后其他线程就在其他没有synchronized就不能用该对象了，可以的

//其实jdk提供了我们很多线程安全的类，但我们要分场合进行使用，如果我们是打算增，删，改，而且需要多个线程去操作的话，我们就用线程安全问题
//如果使用局部变量的话：
//建议使用：StringBuilder。
//因为局部变量不存在线程安全问题。选择StringBuilder。
//StringBuffer效率比较低。

public class Concurrent01 {
        public static void main(String[] args){
            // 创建账户对象（只创建1个）
            Account act = new Account("act-001", 10000);
            // 创建两个线程
            Thread t1 = new AccountThread(act);
            Thread t2 = new AccountThread(act);

            // 设置name
            t1.setName("t1");
            t2.setName("t2");
            // 启动线程取款
            t1.start();
            t2.start();
        }
}