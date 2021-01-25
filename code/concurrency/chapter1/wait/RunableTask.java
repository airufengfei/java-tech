package code.concurrency.chapter1.wait;

public class RunableTask implements Runnable {
    @Override
    public void run() {
        System.out.println("并发编程");
    }

    public static void main(String[] args) {
        //创建线程实例
        RunableTask runableTask = new RunableTask();
        //执行两个相同的线程，使用同一个task逻辑
        new Thread(runableTask).start();
        new Thread(runableTask).start();

    }
}
