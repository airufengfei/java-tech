package code.JVM.chapterOne;

import java.io.FileNotFoundException;

/**
 * 自定义类加载器
 */
public class CustomClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] result = getClassFromCustomPath(name);
            if (result == null){
                throw new FileNotFoundException();
            }else{
                return defineClass(name,result,0,result.length);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 以二进制的形式读取到方法中
     * @param name
     * @return
     */
    private byte[] getClassFromCustomPath(String name){
        //从自定义路径中加载指定类
        //如果指定路径的字节码文件进行了加密，则需要在这个地方进行解密操作
        return null;
    }

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            Class<?> clazz = Class.forName("one", true, customClassLoader);
            Object o = clazz.newInstance();
            System.out.println(o.getClass().getClassLoader());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
