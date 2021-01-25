package code.concurrency.chapter1.waittimeout;

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

        Thread mainThread = Thread.currentThread();

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
