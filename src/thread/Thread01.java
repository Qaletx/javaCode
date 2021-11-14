package thread;

import reflect.StudentReflectTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//java中的多线程和多进程，多协程，线程池，进程池，协程池概念自不用不讲，
// 进程一般是联系于堆，线程联系栈，协程有分无栈协程和有栈协程，它们都需要抢夺cpu的使用时间(时间片)和cpu占比，
// 待时间片结束后，便又进入就绪状态抢夺，继续往下执行。
//未进行开发项目时其实也用得比较少，先来了解下多线程
//当全部线程结束，才意味着进程的结束
public class Thread01 {
    public static void main(String[] args) {
//        多线程的三种实现方式
//        第一种:继承Thread类重写Thread中的run方法,构造一个线程的蓝图, 只要new一个该对象,调用start就开启新线程,就准许去抢夺时间片
//        也就是run一旦被就绪,与main同级,该类中可以添加一些共享对象,或叫共享数据,经不同线程共享数据


//        StudentReflectTest studentReflectTest = new StudentReflectTest(1,"小明","三年二班",99);
//        MyThread myThread1 = new MyThread(studentReflectTest);
//        MyThread myThread2 = new MyThread(studentReflectTest);
//        myThread1.setDaemon(false);
//        myThread1.start();
//        myThread2.start();
//        myThread1.setPriority(10);



//       第二种:实现Runnable接口的一个类,在new时注意将该类作为参数传给Thread对象即可,其实Thread也是实现Runnable接口,第一种就是间接实现该接口
//        而第二种就是自主实现该接口并交给Thread这个jdk写好的实现类作为初始化,其他的与第一种基本一样
        /*StudentReflectTest studentReflectTest = new StudentReflectTest(1, "小明", "三年二班", 99);
        RunnableTest runnableTest = new RunnableTest(studentReflectTest);
        Thread thread = new Thread(runnableTest);
        thread.start();

        或者采用匿名内部类,但如果不共享数据这样写就不错,不然太冗长

        Thread thread1 = new Thread(new RunnableTest(studentReflectTest){
            @Override
            public void run() {
                for(int i = 0; i < 100; i++){
                    System.out.println("t线程---> " + i);
                }
            }
        })
        */

//        第三种: 类似第二种实现方法，只不过要再来一个一层包装类FutureTask，且用FutureTask对象get，会有个返回值，当前线程会阻塞在那
//        且线程蓝图实现的接口不一样和方法不一样，返回值必须是个引用，可以用泛型
        MyThread2 myThread21 = new MyThread2();
        FutureTask<Integer> futureTask = new FutureTask(myThread21);
        Thread thread = new Thread(futureTask);
        try {
            thread.start();
            Integer o = futureTask.get();
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


//        补充:
//        1:当然不同的线程蓝图也可以共享一个或多个数据,毕竟每个线程蓝图的run只能写死
//        2:代码区和堆是共用的,意味着加载的类也是共同的,也就说主栈与main在哪个类没关系,在main中调用
//          其他方法,开辟局部变量都是在主栈中,在run中,则在其他栈中
    }

}

//第一种
class MyThread extends Thread
{
//    蓝图线程之间共享的数据
    private StudentReflectTest studentReflectTest;

//    共享数据初始化时传递的一个共享数据
    public MyThread(StudentReflectTest studentReflectTest){
        this.studentReflectTest = studentReflectTest;
    }
//    栈的一个主方法
    @Override
    public void run() {
        studentReflectTest.setClassNo("新班级");
    }
}

//第二种
class RunnableTest implements Runnable
{
//    线程蓝图的共享数据
    private StudentReflectTest studentReflectTest;

//    共享数据的初始化
    public RunnableTest(StudentReflectTest studentReflectTest)
    {
        this.studentReflectTest = studentReflectTest;
    }

//    栈的一个主方法
    @Override
    public void run() {
        studentReflectTest.setClassNo("新班级");
    }
}
//第三种
class MyThread2 implements Callable {
    public Integer call(){
        System.out.println("我是实现多线程的第三种方法");
        return 2;
    }

}

//补充:与main位置无关
class MyThread1 extends Thread {
    private String name;

    public MyThread1(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("hello " + name);
    }

    public static void main(String[] args) {
        Thread thread = new MyThread1("world");
        thread.start();
    }
}

