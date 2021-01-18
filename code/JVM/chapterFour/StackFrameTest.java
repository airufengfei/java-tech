package code.JVM.chapterFour;

public class StackFrameTest {
    public static void main(String[] args) {
        System.out.println("method1开始");
        method1();
        System.out.println("method1结束");
    }

    private static void method1() {
        System.out.println("method1开始");
        method2();
        System.out.println("method1结束");
    }

    private static int method2() {
        System.out.println("method1开始");
        int i = 1;
        int v = (int)method3();
        System.out.println("method1结束");
        return 1 + v;
    }

    private static double method3() {
        return 2.2;
    }

    public void testAddOperation(){
        byte i = 15;
        int j = 8;
        int k = i + j;
    }
}
