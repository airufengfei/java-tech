链接：[https://airufengfei.github.io/java-tech/java/concurrency/chapter1/让线程睡眠的sleep方法](https://airufengfei.github.io/java-tech/java/concurrency/chapter1/让线程睡眠的sleep方法)

Thread 类 中有 一个静态的 sleep 方法，当 一个执行中的线程调用了 Thread 的 sleep 方法后，调用线程会暂时让出指定时间的执行权，也就是在这期间不参与 CPU 的调度，但是该线程所拥有的监视器资源，比如锁还是持有不让出的 。指定的睡眠时间到了后该函数会正常返回，线程就处于就绪状态，然后参与 CPU的调度，获取到CPU资源后就可以继续运行了。如果在睡眠期间其他线程调用了该线程的 interrupt（）方法中断了该线程，则该线程会在调用sleep方法的地方抛出 InterruptedException 异常而返回。

下面举个例子来说明，线程在睡眠时拥有的监视器资源不会被释放。
```
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SleepTest2 {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        //创建线程A
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                //获取独占锁
                lock.lock();
                try {
                    System.out.println("child threadA is sleep");
                    Thread.sleep(10000);
                    System.out.println("child threadB is in awaked");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("child threadB is in sleep");
                    Thread.sleep(10000);
                    System.out.println("child threadB is in awaked");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
```
执行结果如下：
```
child threadA is sleep
child threadB is in awaked
child threadB is in sleep
child threadB is in awaked
```
如上代码首先创建了一个独占锁，然后创建了两个线程，每个线程在内部先获取锁，然后睡眠，睡眠结束后会释放锁。首先，无论你执行多少遍上面的代码都是线程A先输出或者线程B先输出，不会出现线程A和线程B交叉输出的情况。从执行结果来看，线程A先获取了锁，那么线程A会先输出一行，然后调用sleep方法让自己睡眠10s，在线程A睡眠的这10s内那个独占锁lock还是线程A自己持有，线程B会一直阻塞直到线程A醒来后执行unlock释放锁。下面再来看一下，当一个线程处于睡眠状态时，如果另外一个线程中断了它，会不会在调用 sleep方法处抛出异常。

```
public class SleepTest3 {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("child thread is in sleep");
                    Thread.sleep(10000);
                    System.out.println("child thread is in awaked");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        Thread.sleep(2000);
        thread.interrupt();
    }
}
```
执行结果如下：
```
child thread is in sleep
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at code.concurrency.chapter1.waittimeout.SleepTest3$1.run(SleepTest3.java:12)
	at java.lang.Thread.run(Thread.java:748)
```
子线程在睡眠期间，主线程中断了它，所以子线程在调用sleep方法处抛出了InterruptedException异常。
