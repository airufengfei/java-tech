Java 内存模型允许编译器和处理器对指令重排序以提高运行性能，并且 只 会对不存在数据依赖性的指令重排序。在单线程下重排序可以保证最终执行的结果与程序顺序执行的结果一致，但是在多线程下就会存在问题。

**举例：**
```
int a = 1; （1）
int b = 2;（2）
int c = a + b;（3）
```
在如上代码中，变量c的值依赖a和b的值，所以重排序后能够保证（3）的操作在（2）（1）之后，但是（1）（2）谁先执行就不一定了，这在单线程下不会存在问题，因为并不影响最终结果。

下面看一个多线程的例子：
```
public class ReadThread extends Thread {

    private static boolean ready = false;
    private static int num = 0;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            if (ready && num == 0){
                System.out.println(num + num);
            }
            System.out.println("read thread ...");
        }
    }

    public static class WriteThread extends Thread{
        @Override
        public void run() {
            num = 2;
            ready = true;
            System.out.println("writeThread set over ...");
        }
    }

    public static void main(String[] args) throws InterruptedException {


//        while (num != 2 && !ready){
            WriteThread writeThread = new WriteThread();
            writeThread.start();
            ReadThread readThread = new ReadThread();
            readThread.start();
            Thread.sleep(10);
            readThread.interrupt();
            System.out.println("main exit");
//            writeThread.join();
//            readThread.join();
//            num = 0;
//            ready = false;
//        }
//        System.out.println("");
        
    }

}
```

如上代码在不考虑内存可见性问题的情况下一定会输出4？答案是不一定，由于代码(1)(2)(3)(4)之间不存在依赖关系,所以写线程的代码(3)(4)可能被重排序为先执行(4)再执行(3)，那么执行（ 4 ）后 ，读线程可能已经执行了 (1 ）操作，并且在（ 3 ）执行前开始执行（ 2 ）操作 ，这时候输出结果为0 而不是4。

**注意：**上述代码经过测试不会出现为0的情况，只有在将ready参数判断去掉之后才会出现上述描述的情况。但是会多打印几次“read thread ...”。

重排序在多线程下会导致非预期的程序执行结果，而使用volatile修饰ready就可以避免重排序和内存可见性问题。

写 volatile变量时，可以确保volatile写之前的操作不会被编译器重排序到volatile写之后 。读 volatile变量时，可以确保 volatile 读之后的操作不会被编译器重排序到 volatile读之前 。