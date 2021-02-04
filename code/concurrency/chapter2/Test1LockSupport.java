package code.concurrency.chapter2;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

public class Test1LockSupport {
    public static void main(String[] args) {
        System.out.println("a");
        LockSupport.unpark(Thread.currentThread());

        System.out.println("b");
        LockSupport.park();
        LockSupport.park();

        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

        System.out.println("park");
    }
}
