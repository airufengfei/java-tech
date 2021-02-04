package code.concurrency.chapter11;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TestMap {

    //(1)创建map，key为topic，value为设备列表
    static ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new ArrayList<>();
                list1.add("device1");
                list1.add("device2");

                List<String> oldList = map.putIfAbsent("topic1", list1);
//                map.put("topic1", list1);
                if (oldList != null){
                    oldList.addAll(list1);
                }
                System.out.println(JSONObject.valueToString(map));
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list2 = new ArrayList<>();
                list2.add("device11");
                list2.add("device22");
//                map.put("topic1", list2);
                List<String> oldList = map.putIfAbsent("topic1", list2);
                if (oldList != null){
                    oldList.addAll(list2);
                }
                System.out.println(JSONObject.valueToString(map));
            }
        });

        Thread threadThree = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list3 = new ArrayList<>();
                list3.add("device111");
                list3.add("device222");
//                map.put("topic3", list3);
                List<String> oldList = map.putIfAbsent("topic3", list3);
                if (oldList != null){
                    oldList.addAll(list3);
                }
                System.out.println(JSONObject.valueToString(map));
            }
        });

        threadOne.start();
        threadTwo.start();
        threadThree.start();
    }

}
