package code.concurrency.chapter1.wait;

public class MyThread extends Thread {

    @Override
    public void run() {
        //打印当前线程
        System.out.println(this);
        System.out.println("实现的多线程方法");
    }

    public static void main(String[] args) {
        //创建线程
        MyThread myThread = new MyThread();
        //开启线程
        myThread.start();
    }
}
