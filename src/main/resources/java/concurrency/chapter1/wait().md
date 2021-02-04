### 当一个线程调用一个共享变量的wait（）方法时，该调用线程会被阻塞挂起，直到发生下面几件事之一才会返回：
1. 其他线程调用了该共享对象的notify（）或者notifyAll（）方法；
3. 其他线程调用了该线程的interrupt（）方法，该线程抛出InterruptedException异常返回
4. 另外注意如果调用wait（）方法的线程没有事先获取该对象的监视器锁，则调用wait（）方法时调用线程会抛出IllegalMonitorStateException异常。

### 一个线程如何才能获取一个共享变量的监视器锁呢？
1. 执行synchronized同步代码块时，使用该共享变量作为参数
```
    synchronized(共享变量){
        //doSomething
    }
```
2. 调用该共享变量的方法，并且该方法使用了synchronized修饰

```
    synchronized void add (int a, int b){
        //doSomething
    }
```

3. 另外需要注意的是，一个线程可以从挂起状态变为可以运行状态（也就是被唤醒），即使该线程没有被其他线程调用notify（），notifyAll()方法进行通知，或者被中断，或者等待超时，这就是所谓的虚假唤醒。
 虽然虚假唤醒在应用实践中很少发生，但要防患于未然，做法就是不停地去测试该线程被唤醒的条件是否满足，不满足则继续等待，也就是说在一个循环中调用 wait（） 方法进行防范 。退出循环的条件是满足了唤醒该线程的条件 。
```
synchronized(obj){
    while(条件不满足){
        object.wait();
     }
}
```
如上代码是经典的调用共享变量 wait（）方法的实例，首先通过同步块获取 obj 上面的监视器锁，然后在 while 循环 内调用。同的 wait（） 方法。

下面从一个简单的生产者和消费者例子来加深理解。 如下面代码所示，其中 queue 为共享变量，生产者线程在调用 queue 的 wait（） 方法前，使用 synchronized 关键宇拿到了该共享变量queue 的监视器锁，所以调用 wait() 方法才不会抛出 lliega!MonitorStateException 异常。如果当前队列没有空闲容量则会调用 queued 的 wait（） 方法挂起当前线程，这里使用循环就是为了避免上面说的虚假唤醒问题。假如当前线程被虚假唤醒了，但是队列还是没有空余容量 ，那么当前线程还是会调用 wait（）方法把自己挂起 。

```

public class MyWait {


    private RunableTaskOne task;

    private static Integer MAX_SIZE = 10;

    //生产者
    public void productor(){

        synchronized (task){
            while (task.getSize() == MAX_SIZE){
                try {
                    //挂起当前线程，并释放通过同步块获取的queue上的锁，让消费者线程可以获取该所，然后获取队列里面的元素
                    task.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //空闲则生成元素，并通知消费者线程
            task.add(this);
            task.notify();
        }
    }

    public void customer(){
        synchronized (task){
            while (task.getSize() == 0){
                try {
                    //挂起当前线程，并释放通过同步快获取的queue上的所，让生产者可以获取该所，将生产元素放入队列。
                    task.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            task.take();
            task.notifyAll();
        }
    }

}

class RunableTaskOne implements Runnable {

    private Integer size ;

    private Object obj;
    //获取
    public Integer getSize(){
        return size;
    }
    //添加
    public void add(Object obj){
        this.obj = obj;
    }
    //消费
    public void take(){
        size --;
    }
    //生产
    @Override
    public void run() {
        size ++;
        System.out.println("并发编程");
    }

}
```
在如上代码中假如生产者线程A首先通过synchronized获取到了queue上的锁，那么后续所有企图生产元素的线程和消费线程将会在获取该监视器锁的地方被阻塞挂起。线程A获取锁后发现当前队列己满会调用queue.wait（）方法阻塞自己，然后释放获取的queue上的锁，这里考虑下为何要释放该锁？如果不释放，由于其他生产者线程和所有消费者线程都己经被阻塞挂起，而线程A也被挂起，这就处于了死锁状态。这里线程A挂起自己后释放共享变量上的锁，就是为了打破死锁必要条件之一的持有并等待原则。关于死锁后面的章节会讲。线程A释放锁后，其他生产者线程和所有消费者线程中会有一个线程获取queue上的锁进而进入同步块，这就打破了死锁状态。

另外需要注意的是，当前线程调用共享变量的wait（）方法后只会释放当前共享变量上的锁，如果当前线程还持有其他共享变量的锁，则这些锁是不会被释放的。下面来看一个例子。

```
public class MyWaitTwo {

    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //获取resourceA共享资源的监视器锁
                    synchronized (resourceA) {
                        System.out.println("threadA get resourceA lock");

                        //获取resourceB共享资源的监视器锁
                        synchronized (resourceB) {
                            System.out.println("threadA get resourceB lock");
                            //线程A阻塞，并释放获取到的resourceA的锁
                            System.out.println("threadA release resourceA lock");
                            resourceA.wait();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //线程休眠1s
                    Thread.sleep(1000);
                    //获取resourceA共享资源的监视器锁
                    synchronized (resourceA) {
                        System.out.println("threadB get resourceA lock");
                        System.out.println("threadB try get resourceB lock...");

                        //获取resourceB共享资源的监视器锁
                        synchronized (resourceB) {
                            System.out.println("threadB get resourceA lock");
                            //线程B阻塞，并释放获取到的resourceA的锁
                            System.out.println("threadB release resourceA lock");
                            resourceA.wait();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //启动两个线程
        threadA.start();
        threadB.start();

        //等待两个线程结束
        threadA.join();
        threadB.join();

        System.out.println("main over");

    }
}

```
输出结果：
```
C:\software\java8\bin\java.exe "-javaagent:C:\software\idea\IntelliJ IDEA 2019.3.3\lib\idea_rt.jar=51893:C:\software\idea\IntelliJ IDEA 2019.3.3\bin" -Dfile.encoding=UTF-8 -classpath C:\software\java8\jre\lib\charsets.jar;C:\software\java8\jre\lib\deploy.jar;C:\software\java8\jre\lib\ext\access-bridge-64.jar;C:\software\java8\jre\lib\ext\cldrdata.jar;C:\software\java8\jre\lib\ext\dnsns.jar;C:\software\java8\jre\lib\ext\jaccess.jar;C:\software\java8\jre\lib\ext\jfxrt.jar;C:\software\java8\jre\lib\ext\localedata.jar;C:\software\java8\jre\lib\ext\nashorn.jar;C:\software\java8\jre\lib\ext\sunec.jar;C:\software\java8\jre\lib\ext\sunjce_provider.jar;C:\software\java8\jre\lib\ext\sunmscapi.jar;C:\software\java8\jre\lib\ext\sunpkcs11.jar;C:\software\java8\jre\lib\ext\zipfs.jar;C:\software\java8\jre\lib\javaws.jar;C:\software\java8\jre\lib\jce.jar;C:\software\java8\jre\lib\jfr.jar;C:\software\java8\jre\lib\jfxswt.jar;C:\software\java8\jre\lib\jsse.jar;C:\software\java8\jre\lib\management-agent.jar;C:\software\java8\jre\lib\plugin.jar;C:\software\java8\jre\lib\resources.jar;C:\software\java8\jre\lib\rt.jar;D:\workspace\code\java-tech\out\production\java-tech code.concurrency.chapter1.MyWaitTwo
threadA get resourceA lock
threadA get resourceB lock
threadA release resourceA lock
threadB get resourceA lock
threadB try get resourceB lock...
```
上述代码执行过程：
1. 在main函数里面启动了线程A和线程B，为了让线程A先获取到锁，这里让线程B先休眠了ls，线程A先后获取到共享变量resourceA和共享变量resourceB上的锁，然后调用了resourceA的wait（）方法阻塞自己，阻塞自己后线程A释放掉获取的resourceA上的锁。
2. 线程B休眠结束后会首先尝试获取resourceA上的锁，如果当时线程A还没有调用wait（）方法释放该锁，那么线程B会被阻塞，当线程A释放了resourceA上的锁后，线程B就会获取到resourceA上的锁，然后尝试获取resourceB上的锁。由于线程A调用的是resourceA上的wait（）方法，所以线程A挂起自己后并没有释放获取到的resourceB上的锁，所以线程B尝试获取resourceB上的锁时会被阻塞。
3. 这就证明了当线程调用共享对象的wait（）方法时，当前线程只会释放当前共享对象的锁，当前线程持有的其他共享对象的监视器锁并不会被释放 。

再举例说明：当一个线程调用共享对象的wait（）方法被阻塞挂起后，如果其他线程中断了该线程，则该线程会抛出InterruptedException异常并返回。
```
/**
 * interupt结束进程
 */
public class WaitNotifyInterupt {
    static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("开始");
                    synchronized (obj){
                        System.out.println("开始执行等待");
                        obj.wait();
                        System.out.println("结束执行等待");
                    }
                    System.out.println("结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadA.start();

        Thread.sleep(1000);

        System.out.println("开始 interrupt threadA");
        //终端线程
        threadA.interrupt();
        System.out.println("结束 interrupt threadA");
    }
}

```
执行结果：
```
开始
开始执行等待
开始 interrupt threadA
结束 interrupt threadA
java.lang.InterruptedException
	at java.lang.Object.wait(Native Method)
	at java.lang.Object.wait(Object.java:502)
	at code.concurrency.chapter1.WaitNotifyInterupt$1.run(WaitNotifyInterupt.java:18)
	at java.lang.Thread.run(Thread.java:748)
```
在如上代码中，threadA调用共享对象obj的wait()方法后阻塞挂起了自己，然后主线程在休眠1s后终端了threadA线程，中断后threadA在obj.wait()处抛出java.lang.InterruptedException异常而返回并终止。



