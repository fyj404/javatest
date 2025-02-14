package ConcorrMap;

import java.util.concurrent.*;

public class ConcurrentMapExample {
    public static void main(String[] args) {
        // 创建一个线程安全的 ConcurrentMap
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

        // putIfAbsent：如果key不存在，则插入元素
        map.putIfAbsent("A", 10);
        map.putIfAbsent("B", 20);
        map.putIfAbsent("A", 30);  // A 已存在，不会修改

        System.out.println("Map after putIfAbsent: " + map);

        // remove：只有在key和value匹配时才删除
        map.remove("A", 10);  // 删除成功
        map.remove("B", 30);  // 不会删除，因为值不匹配

        System.out.println("Map after remove: " + map);

        // replace：如果key存在且值匹配，替换value
        map.replace("B", 20, 40);  // 替换成功

        System.out.println("Map after replace: " + map);

        // computeIfAbsent：如果key不存在，通过计算插入新值
        map.computeIfAbsent("C", key -> 50);
        map.computeIfAbsent("C", key -> 40);
        System.out.println("Map after computeIfAbsent: " + map);
    }
}
