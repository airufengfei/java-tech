package code.concurrency.chapter1.ThreadLocal;

import java.io.PrintStream;

public class ThreadLocalTest {

    static ThreadLocal<String> localVariables = new ThreadLocal<>();

    public static void print(String str){
        //打印当前线程的值
        System.out.println(str + ":" +  localVariables);
        //移除线程内的值
        localVariables.remove();
    }

    public static void main(String[] args) {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariables.set("ThreadOne value");
                print("threadOne");
                System.out.println("threadONe remove after :" + localVariables.get());
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariables.set("threadTwo value");
                print("threadTwo");
                System.out.println("threadTwo remove after : " + localVariables.get());
            }
        });

        threadOne.start();
        threadTwo.start();

    }

}
