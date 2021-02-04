package code.JVM.chapterOne;

public class ClassLoaderTest2 {
    public static void main(String[] args) {
        try {
            //通过class方式获取
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();

            //通过线程的方式获取
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            System.out.println(contextClassLoader);
            //通过classloader获取
            ClassLoader parent = classLoader.getSystemClassLoader().getParent();
            System.out.println(parent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
