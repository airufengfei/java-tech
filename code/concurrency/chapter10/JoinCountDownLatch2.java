package code.concurrency.chapter10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JoinCountDownLatch2 {
    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
                System.out.println("threadOne over");
            }
        });

        Runnable run = () ->{
          try {
              Thread.sleep(2000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              countDownLatch.countDown();
          }
            System.out.println("threadTwo over");
        };

        executorService.submit(run);

        System.out.println("wait all thread over");

        countDownLatch.await();
        System.out.println("all thread over");

        executorService.shutdownNow();
    }
}
