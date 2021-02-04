链接：[https://airufengfei.github.io/java-tech/java/concurrency/chapter1/让出CPU执行权的yield方法](https://airufengfei.github.io/java-tech/java/concurrency/chapter1/让出CPU执行权的yield方法)

Thread 类中有一个静态的yield方法，当一个线程调用yield方法时，实际就是在暗示线程调度器当前线程请求让出自己的CPU使用，但是线程调度器可以无条件忽略这个暗示。我们知道操作系统是为每个线程分配一个时间片来占有 CPU 的，正常情况下当一个线程把分配给自己的时间片使用完后，线程调度器才会进行下一轮的线程调度，而当一个线程调用了 Thread 类的静态方法 yield 时，是在告诉线程调度器自己占有的时间片中还没有使用完的部分自己不想使用了，这暗示线程调度器现在就可以进行下一轮的线程调度。

当一个线程调用yield方法时，当前线程会让出CPU使用权，然后处于就绪状态，线程调度器会从线程就绪队列里面获取一个线程优先级最高的线程，当然也有可能会调度到刚刚让出CPU的那个线程来获取CPU执行权。下面举一个例子来加深对yield方法的理解。

```
/**
 * yield让出CPU执行权
 */
public class YieldTest implements Runnable {

    YieldTest(){
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        for (int i = 0;i < 5; i ++){
            if (i%5 == 0){
                System.out.println(Thread.currentThread() + "yield cpu...");

                //当前线程让出CPU执行权，放弃时间片，进行下一轮调度
//                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread() + "is over");
    }

    public static void main(String[] args) {
        new YieldTest();
        new YieldTest();
        new YieldTest();
    }
}
```
执行结果：(每次执行的结果都不一样)
```
Thread[Thread-2,5,main]yield cpu...
Thread[Thread-2,5,main]is over
Thread[Thread-0,5,main]yield cpu...
Thread[Thread-1,5,main]yield cpu...
Thread[Thread-0,5,main]is over
Thread[Thread-1,5,main]is over
```
如上代码开启了三个线程，每个线程的功能都一样，都是在 for 循环中执行 5 次打印 。运行多次后，每次执行的结果都不一样。解开Thread。yield()注释后再执行，结果如下：
```
Thread[Thread-2,5,main]yield cpu...
Thread[Thread-0,5,main]yield cpu...
Thread[Thread-1,5,main]yield cpu...
Thread[Thread-0,5,main]is over
Thread[Thread-2,5,main]is over
Thread[Thread-1,5,main]is over
```
从结果可知，Thread .yield（）方法生效了，三个线程分别在 i=O 时调用了 Thread.yield()方法，所以三个线程自己的两行输出没有在－起，因为输出了第一行后当前线程让出了CPU 执行权 。

**总结 ：** sleep 与 yield 方法的区别在于，当线程调用 sleep 方法时调用线程会被阻塞挂起指定的时间，在这期间线程调度器不会去调度该线程。而调用 yield 方法时，线程只是让出自己剩余的时间片，并没有被阻塞挂起，而是处于就绪状态，线程调度器下一次调度时就有可能调度到当前线程执行 。