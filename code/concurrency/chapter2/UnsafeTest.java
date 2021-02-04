package code.concurrency.chapter2;

import sun.misc.Unsafe;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

public class UnsafeTest {

    static final Unsafe unsafe = Unsafe.getUnsafe();
    //记录比那辆state在类UnsafeTest中的偏移值
    static final long stateOffset;

    private volatile long state = 0;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnsafeTest unsafeTest = new UnsafeTest();
        boolean b = unsafe.compareAndSwapLong(unsafeTest, stateOffset, 0, 1);
        System.out.println(b);
        AtomicInteger a = new AtomicInteger();
        a.set(1);
        System.out.println(a.get());
        AtomicLong at = new AtomicLong();

    }
}
