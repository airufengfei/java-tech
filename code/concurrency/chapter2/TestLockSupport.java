package code.concurrency.chapter2;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin park!");
//                while (!Thread.currentThread().isInterrupted()) {
                    LockSupport.park();

//                }
//                LockSupport.park();
                System.out.println("child thread unpark!");
            }
        });

        //启动子线程
        thread.start();

        Thread.sleep(1000);


        System.out.println("main thread begin unpark!");
        //中断子线程
//        thread.interrupt();

        LockSupport.unpark(thread);
    }
}
