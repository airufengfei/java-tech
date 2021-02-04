package code.concurrency.chapter11;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

public class TestSimpleDateFormate {
//    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static ThreadLocal<DateFormat> safesdf = new ThreadLocal<DateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            System.out.println(Thread.currentThread() + " new ");
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static void main(String[] args) {
        //创建多个线程并启动
        for (int i = 0; i < 10; i ++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (safesdf){
                            System.out.println(safesdf.get().parse("2017-12-13 15:17:27"));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } finally {
                        safesdf.remove();
                    }
                }
            });
            thread.start();
        }
    }


}
