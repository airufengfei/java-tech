package code.concurrency.chapter1.lock;

public class DaemonThreadTest {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){
                }
            }
        });

        thread.start();
        System.out.println("main thread is over");
    }
}
