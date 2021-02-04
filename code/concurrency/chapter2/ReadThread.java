package code.concurrency.chapter2;

import javax.swing.text.StyledEditorKit;

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
