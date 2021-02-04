package code.concurrency.chapter3;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.StampedLock;

public class Point {
    private double x, y;

    private final StampedLock sl = new StampedLock();

    //排它锁
    void move(double deltaX,double deltaY){
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        }finally {
            sl.unlockWrite(stamp);
        }
    }
    //乐观读锁（tryOptimisticRead）
    double distanceFromOrigin(){
        //尝试获取乐观读锁
        long stamp = sl.tryOptimisticRead();
        //将全部变量复制到方法体栈内
        double currentX = x, currentY = y;
        //检查在（1）处获取了读锁戳记后，锁有没被其他线程排他性抢占
        if (!sl.validate(stamp)){
            stamp = sl.readLock();
        }
        try {
            // 将全部变量复制到方法体栈内
            currentX = x;
            currentY = y;
        }finally {
            sl.unlockRead(stamp);
        }
        return Math.sqrt(currentX*currentX + currentY* currentY);
    }

    //使用悲观锁获取读锁，并尝试转换为写锁
    void moveIfaTOrigin(double newX, double newY){
        //这里可以使用乐观读锁替换
        long stamp = sl.readLock();
        try {
            //如果当前点在原点则移动
            while (x == 0.0 && y == 0.0){
                //尝试将获取的读锁升级为写锁
                long ws = sl.tryConvertToWriteLock(stamp);
                //升级成功，则更新戳记，并设置坐标值，然后退出循环
                if (ws != 0L){
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                }else {
                    //读锁升级写锁失败则释放读锁，显示获取独占写锁，然后循环重试
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {

            sl.unlockRead(stamp);
        }
    }
}
