package code.concurrency.chapter11;


public class TestNoNameThread {


    ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {



        ThreadLocal<String> init = ThreadLocal.withInitial(() -> {
            System.out.println("init");
            return "null";
        });

        init.set("a");

        String s = init.get();

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("保存订单的线程");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new NullPointerException();
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("保存收货地址的线程");
            }
        });

        threadOne.start();
        threadTwo.start();
    }
}
