package code.concurrency.chapter11;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    static class LocalVariable{
        private long[] a = new long[1024*1024];
    }

    //(1)
    final static ThreadPoolExecutor pooleExecutor = new ThreadPoolExecutor(5,5,1, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());
    //(2)
    final static ThreadLocal<LocalVariable> localvariable = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        //(3)
        for (int i = 0; i < 50; i ++){
            pooleExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    //(4)
                    localvariable.set(new LocalVariable());
                    //(5)
                    System.out.println("use local variable");
                    //localVariable.remove();
                }
            });
            Thread.sleep(1000);
        }

        //(6)
        System.out.println("pool execute over");

    }
}
