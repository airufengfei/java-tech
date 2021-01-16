package code.JVM.chapterOne;

import sun.misc.Launcher;

import java.net.URL;

public class ClassLoaderTest1 {
    public static void main(String[] args) {
        System.out.println("************启动类加载器*********");
        //获取BootstrapClassLoader能够加载的api的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url: urLs) {
            System.out.println(url);
        }
        //从商民的路径中随意选择一个类，看一下它的类加载器类型
        System.out.println("*********扩展类加载器********");
        String property = System.getProperty("java.ext.dirs");
        for (String str : property.split(";")){
            System.out.println(str);
        }
    }
}
