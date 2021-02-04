package code.concurrency.chapter11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCopyEl {

    static Map<Integer, List<String>> serviceMap = new HashMap<>();

    static {
        serviceMap.put(111,new ArrayList<>());
        serviceMap.put(222,new ArrayList<>());
    }

    public static void main(String[] args) {
        Map<Integer, List<String>> appKeymap = new HashMap<>();

        List<String> oneList = new ArrayList<>();
        oneList.add("list_id1");
        appKeymap.put(111,oneList);

        List<String> twoList = new ArrayList<>();
        twoList.add("list_id2");
        appKeymap.put(222,twoList);

        List<String> msgList = new ArrayList<>();
        msgList.add("HELLO");

        

    }

}
