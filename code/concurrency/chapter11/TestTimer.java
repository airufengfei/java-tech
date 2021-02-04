package code.concurrency.chapter11;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.LockSupport;

public class TestTimer {
    static Timer timer = new Timer();


    public static void main(String[] args) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("one task");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error");
            }
        },500);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("two task");

                    for (;;){
                        System.out.println("two task");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        },1000);

    }

}
