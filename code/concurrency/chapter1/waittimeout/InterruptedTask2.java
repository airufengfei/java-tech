package code.concurrency.chapter1.waittimeout;

public class InterruptedTask2 {

    public static void main(String[] args) throws InterruptedException {

       Runnable runnable = () -> {
         while (true){

         }
       };

       //启动线程
        Thread threadOne = new Thread(runnable);
        threadOne.start();

       //设置中断标志
        threadOne.interrupt();

        //获取中断标志
        System.out.println("isInterrupted:" + threadOne.isInterrupted());

        //获取中断标志并重设
        System.out.println("isInterrupted:" + threadOne.interrupted());

        //获取中断标志并重设
        System.out.println("isInterrupted:" + threadOne.isInterrupted());

        //获取中断标志
        System.out.println("isInterrupted:" + threadOne.isInterrupted());

        threadOne.join();

        System.out.println("main thread is over");
    }
}
