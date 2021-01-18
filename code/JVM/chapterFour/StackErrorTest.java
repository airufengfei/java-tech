package code.JVM.chapterFour;

/**
 * 演示栈中的异常
 * 设置-Xss1024k
 */
public class StackErrorTest {
    private static int a = 0;
    public static void main(String[] args) {
        System.out.println(a++);
        main(args);
    }
}
