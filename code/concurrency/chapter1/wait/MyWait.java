package code.concurrency.chapter1.wait;


public class MyWait {


    private RunableTaskOne task;

    private static Integer MAX_SIZE = 10;

    //生产者
    public void productor(){

        synchronized (task){
            while (task.getSize() == MAX_SIZE){
                try {
                    //挂起当前线程，并释放通过同步块获取的queue上的锁，让消费者线程可以获取该所，然后获取队列里面的元素
                    task.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //空闲则生成元素，并通知消费者线程
            task.add(this);
            task.notify();
        }
    }

    public void customer(){
        synchronized (task){
            while (task.getSize() == 0){
                try {
                    //挂起当前线程，并释放通过同步快获取的queue上的所，让生产者可以获取该所，将生产元素放入队列。
                    task.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            task.take();
            task.notifyAll();
        }
    }

}

class RunableTaskOne implements Runnable {

    private Integer size ;

    private Object obj;
    //获取
    public Integer getSize(){
        return size;
    }
    //添加
    public void add(Object obj){
        this.obj = obj;
    }
    //消费
    public void take(){
        size --;
    }
    //生产
    @Override
    public void run() {
        size ++;
        System.out.println("并发编程");
    }

}