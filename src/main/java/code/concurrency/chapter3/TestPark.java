package code.concurrency.chapter3;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class TestPark {

    static ReentrantLock lock = new ReentrantLock();
    public void testPark(){
        LockSupport.park(this);//1
    }

    public static void main(String[] args) {

        lock.tryLock();
//        TestPark testPark = new TestPark();
//        testPark.testPark();

        System.out.println("a");
    }

}
