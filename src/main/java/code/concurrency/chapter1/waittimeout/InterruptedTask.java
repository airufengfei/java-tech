package code.concurrency.chapter1.waittimeout;

public class InterruptedTask{

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //如果当前线程被中断则退出循环,没有中断获取的是false，非false为true
                while (!Thread.currentThread().interrupted()){
                    System.out.println("hello");

                }
                System.out.println("Thread.currentThread().isInterrupted():" +Thread.currentThread().isInterrupted());
            }
        });

        //启动子线程
        thread.start();

        Thread.sleep(10);
        System.out.println("thread is interrupt");
        thread.interrupt();
        //等待线程执行结束
        thread.join();
        System.out.println("main is over");
    }
}
