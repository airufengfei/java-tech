package code.concurrency.chapter6;

import java.util.concurrent.locks.LockSupport;

public class TestPark {

    public void testPark(){
        LockSupport.park(this);//1
    }

    public static void main(String[] args) {
        TestPark testPark = new TestPark();
//        LockSupport.park(1);
        testPark.testPark();

    }

}
