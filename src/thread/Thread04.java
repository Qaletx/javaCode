package thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Thread04 {
    public static void main(String[] args) throws Exception {

        // 创建定时器对象，Timer中TimerThread属性就是一个线程类，会开辟一个线程，其中run会调用一个死循环
//        Timer timer = new Timer();
        Timer timer = new Timer(true);
        //守护线程的方式,假如没有设置此，则TimerThread会一直存在，则整个进程不会结束


        // 指定定时任务
//       第一参数为一个线程类的蓝图对象，run中调用得的死循环方法会不断地又开辟一个栈，而蓝图来自此
        //timer.schedule(定时任务, 第一次执行时间, 间隔多久执行一次);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        当时间已过，则会立即执行
        Date firstTime = sdf.parse("2021-10-21 13:42:55");
        timer.schedule(new LogTimerTask() , firstTime, 1000 * 10);

        //匿名内部类方式
//        timer.schedule(new TimerTask(){
//            @Override
//            public void run() {
//                // code....
//            }
//        } , firstTime, 1000 * 10);

        while(true)
        {
            Thread.sleep(10000);
            break;
        }
    }
}
//继承地TimerTask，其实间接插上接口Runnable
class LogTimerTask extends TimerTask {

    @Override
    public void run() {
        // 编写你需要执行的任务就行了。
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTime = sdf.format(new Date());
        System.out.println(strTime + ":成功完成了一次数据备份！");
    }
}
