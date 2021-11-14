package thread.concurrent;

/*wait() notify() notifyAll()方法
这些方法都是存在任何一个对象中，是个实例方法，因为它来自Object的类，
注意点
        1:以上方法都得写在synchronized中，否者出现异常
        2:以上方法的调用者必须就是锁对象，否者会抛异常
        3:wait会使当前线程进入阻塞状态，并释放该锁，也可以被interrupted()方法叫醒
        4:notify会将该锁对象下wait的对象的线程中随机一个叫醒，但不释放锁。利用的异常机制
        5:notifyAll会将该锁对象下wait的对象的全部线程叫醒，但不释放锁。利用的异常机制

*/
public class Concurrent02 {
    public static void main(String[] args) {
        StorageTest storageTest = new StorageTest(0);
        Consumer consumer = new Consumer(storageTest);
        Product product = new Product(storageTest);
        Thread thread01 = new Thread(consumer);
        Thread thread02 = new Thread(product);
        thread01.setName("商店出售了该商品");
        thread02.setName("工厂生产了该商品");
        thread01.start();
        thread02.start();
    }
}
class Consumer implements Runnable{
    private StorageTest storageTest;
    public Consumer() {
    }

    public Consumer(StorageTest storageTest) {
        this.storageTest = storageTest;
    }
    public void run(){
        while (true)
           {
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               synchronized (storageTest){
                    if(storageTest.getStorage() > 0)
                    {
                        storageTest.setStorage(0);
                        System.out.println(Thread.currentThread().getName()+"此时余量剩余"+storageTest.getStorage());
                        storageTest.notify();
                    }
                    else
                    {
                        try {
                            storageTest.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
           }
    }
}
class Product implements Runnable{
    private StorageTest storageTest;
    public Product(){

    }
    public Product(StorageTest storageTest){
        this.storageTest = storageTest;
    }
    public void run(){
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (storageTest){
                if(storageTest.getStorage() == 0){
                    storageTest.setStorage(1);
                    System.out.println(Thread.currentThread().getName()+"此时余量剩余"+storageTest.getStorage());
                    storageTest.notify();
                }
                else {
                    try {
                        storageTest.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
class StorageTest{
    private int storage;
    public StorageTest(){

    }
    public StorageTest(int storage)
    {
        this.storage = storage;
    }
    public void setStorage(int storage){
        this.storage = storage;
    }
    public int getStorage(){
        return this.storage;
    }

}