package code.concurrency.chapter1.wait;

import sun.misc.Unsafe;

import java.time.Duration;
import java.time.LocalTime;

/**
 * interupt结束进程
 */
public class WaitNotifyInterupt {

    static volatile int a;

    public static void main(String[] args) throws InterruptedException {

        ThreadSafeIntegerOne threadSafeIntegerOne = new ThreadSafeIntegerOne();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0;i < 10000; i ++){
                    threadSafeIntegerOne.set();
                }

            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0;i < 10000; i ++){
                    threadSafeIntegerOne.set();
                }

            }
        });

        LocalTime now = LocalTime.now();
        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
        LocalTime now1 = LocalTime.now();
        System.out.println(threadSafeIntegerOne.get());
        System.out.println("use time: " + Duration.between(now1,now).getNano()/1000);
    }
}
class ThreadSafeIntegerOne{
    private int value = 0;

    public synchronized int get(){
        return value;
    }
    public synchronized void set(){
        value ++;
    }
}

class ThreadSafeIntegerTwo{
    private volatile int value = 0;

    public int get(){
        return value;
    }
    public void set(){
        value ++;
    }
}