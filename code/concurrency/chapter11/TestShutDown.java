package code.concurrency.chapter11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestShutDown {

    static void asynExecuteOne(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("--async execute one ---");
            }
        });
    }

    static void asynExecuteTwo(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("-async execute two -");
            }
        });

    }

    public static void main(String[] args) {
        System.out.println("---sync execute ---");
        asynExecuteOne();
        asynExecuteTwo();
        System.out.println("over");
    }
}
