在项目实践中经常会遇到一个场景，就是需要等待某几件事情完成后才能继续往下执行 ， 比如多个线程加载资源 ， 需要等待多个线程全部加载完毕再汇总处理。 Thread 类中有一个 join 方法就可以做这个事情，前面介绍的等待通知方法是 Obj ect 类中的方法 ， 而 join方法则 是 Thread 类直接提供的 。 j oin 是无参且返回值为 void 的方法 。 下面来看一个简单的例子。
```
public class JoinTask {
    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("child threadOne over");
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("chlid threadTwo over!");
            }
        });

        //启动子线程
        threadOne.start();
        threadTwo.start();

        //等待子线程执行完毕，返回
        threadOne.join();
        threadTwo.join();

        System.out.println("all child thread over!");
    }
}
```
如上代码在主线程里面启动了两个子线程，然后分别调用了它们的join（）方法，那么主线程首先会在调用 threadOne.join（） 方法后被阻塞 ， 等待 threadOne 执行完毕后返回。threadOne 执行完毕后 threadOne.join（） 就会返回 ， 然后主线程调用 threadTwo.join（） 方法后再次被阻塞 ， 等待threadTwo执行完毕后返回。这里只是为了演示join方法的作用，在这种情况下使用后面会讲到的CountDownLatch是个不锚的选择 。

另外，线程 A 调用线程 B 的 join 方法后会被阻塞 ， 当其他线程调用了线程 A 的inte rrupt（）方法中断了线程 A 时，线程 A调用join方法会抛出 InterruptedException 异常而返回。 下 面通过一个例子来加深理解。
```
public class JoinTaskTwo {
    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threadOne begin run!");
                while (true) {

                }
            }
        });

        Thread mainThread = Thread.currentThread(); //获取的线程

        System.out.println("mainthread:"+mainThread);
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainThread.interrupt();
            }
        });

        threadOne.start();
        ThreadGroup threadGroup = threadOne.getThreadGroup();
        System.out.println("threadGroup:" + threadGroup);
        System.out.println("threadTwo:"+ threadTwo.getThreadGroup());
        threadTwo.start();

        try {
            threadOne.join();
        } catch (InterruptedException e) {
            System.err.println("main thread:" + e);
        }
    }
}
```
结果：其中threadOne、threadTwo和currentthread使用的线程都是main线程
```
mainthread:Thread[main,5,main]
threadGroup:java.lang.ThreadGroup[name=main,maxpri=10]
threadTwo:java.lang.ThreadGroup[name=main,maxpri=10]
threadOne begin run!
main thread:java.lang.InterruptedException
```
如上代码在 threadOne 线程里面执行死循环，主线程调用 threadOne 的 join 方法阻 塞 自己 等 待线程 threadOne 执行完毕，待 threadTwo 休眠 ls 后会调用主线程的interrupt （） 方法设置主线程的中断标志，从结果看在 主 线程中的 threadOne.join（）处会抛出 InterruptedException 异常 。 这里需要注意的是 ， 在 threadTwo 里面调用的是主线程的interrupt（） 方法，而不是线程thread One 的 。