package code.concurrency.chapter1.wait;

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
