package code.concurrency.chapter1.waittimeout;

public class InterruptedTask1 {
    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("threadOne begin sleep for 2000 seconds");
                    Thread.sleep(200000);
                    System.out.println("threadOne awaking");
                } catch (InterruptedException e) {
                    System.out.println("threadOne is interrupted while sleeping");
                    return;
                }
                System.out.println("threadOne-leaving normally");
            }
        });

        threadOne.start();

        //确保线程进入休眠
        Thread.sleep(1000);

        //打断子线程的休眠，让子线程冲sleep函数返回
        threadOne.interrupt();

        //等待子线程执行完毕
        threadOne.join();

        System.out.println("main thread is over");
    }
}
