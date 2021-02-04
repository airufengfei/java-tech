package code.JVM.chapter8;

public class HeapSpaceInitial {
    public static void main(String[] args) {
        long l = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long l1 = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        System.out.println(l);
        System.out.println(l1);
    }
}
