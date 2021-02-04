package code.concurrency.chapter11;

public class TestArrayBlockingQueue {

    public static int count;

    public static void putUninterruptibly(String eventObject){
        boolean interrupted = false;
        try {
            while (true){
                try {
//                    System.out.println(count);
                    int a = 10/0;
                    break;
                } catch (Exception e){
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted){
                System.out.println("in");
                count = 1;
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                putUninterruptibly("a");
            }
        });

        threadOne.start();
        Thread.sleep(1000);
        System.out.println("sleep end");
        threadOne.wait();
        System.out.println("end");
//        putUninterruptibly("a");
    }
}
