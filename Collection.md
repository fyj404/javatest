


## 目录

```java
package com.example;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello from com.example!");
    }
}

```
javac -d bin src/Hello.java

-d bin：强制按照包结构输出 .class 文件到 bin/ 下。 

java -cp bin com.example.Hello

类路径 -cp bin：运行时从 bin 文件夹下按包结构查找类。


## CountDown Latch
CountdownLatch 是 Java 并发工具类，用于等待其他线程完成任务后再继续执行
设定一个计数值，线程完成任务后调用 countDown() 使计数器减 1。

调用 await() 方法的线程会阻塞，直到计数器为 0。

```java
import java.util.concurrent.*;

public class CountdownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 完成任务");
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println("所有任务已完成，继续主线程工作");
    }
}
```
线程同步：等待多个任务完成。

简单高效：适合一次性事件协作。


## Java Collection

Java Collection Framework（集合框架）是 Java 中用于存储和操作一组对象
的数据结构集合。它提供了通用的接口和类，
如 List、Set、Queue 和 Map，使数据操作更加高效和简洁。

### List（有序，可重复）

ArrayList：基于动态数组，查询快，增删慢。

LinkedList：基于双向链表，增删快，查询慢。

Vector：线程安全的动态数组（已较少使用）。
```java
List<String> list = new ArrayList<>();
list.add("Apple");
list.add("Banana");
System.out.println(list);
```
#### ArrayList 方法
add(E e) 添加元素

get(int index) 获取指定位置元素

set(int index, E e) 修改指定位置元素

remove(int index) 删除指定位置元素

size() 返回元素数量

contains(Object o) 检查是否包含元素

indexOf(Object o) 返回元素索引

#### LinkedList 方法


add(E e)添加元素

addFirst(E e)在头部添加元素

addLast(E e)在尾部添加元素

getFirst()获取头部元素

getLast()获取尾部元素

removeFirst()删除头部元素

removeLast()删除尾部元素

size()返回元素数量
### Set（无序，唯一）

HashSet：基于哈希表，元素无序且唯一。

LinkedHashSet：保留插入顺序。

TreeSet：基于红黑树，元素按自然排序。

```java
Set<String> set = new HashSet<>();
set.add("Apple");
set.add("Banana");
System.out.println(set);
```

### Queue（队列）

LinkedList（实现队列接口）：FIFO 结构。

PriorityQueue：元素按优先级排序。

```java
Queue<String> queue = new LinkedList<>();
queue.offer("A");
queue.offer("B");
System.out.println(queue.poll());
```
#### 支持的方法

add(E e) 添加元素，满时抛异常

offer(E e) 添加元素，满时返回 false

remove()  移除头部元素，空时抛异常

poll()  移除并返回头部元素，空时返回 null

element() 返回头部元素但不移除，空时抛异常

peek() 返回头部元素但不移除，空时返回 null
### Map（键值对映射）

HashMap：无序，键唯一。

LinkedHashMap：按插入顺序存储。

TreeMap：按键排序存储。
```java
Queue<String> queue = new LinkedList<>();
queue.offer("A");
queue.offer("B");
System.out.println(queue.poll());
```
### 支持的方法
put(K key, V value) 添加键值对

get(Object key) 获取对应值

remove(Object key) 移除键值对

containsKey(Object key) 判断是否包含键

containsValue(Object value) 判断是否包含值

keySet() 返回所有键集合

values() 返回所有值集合

entrySet() 返回所有键值对集合

## Java Concurrent Collection
BlockingQueue 是 Java 并发包 (java.util.concurrent) 中的队列接口，
支持线程安全的阻塞式操作。它在生产者-消费者模型中广泛使用，
通过阻塞机制协调多线程之间的数据传输。

### BlockingQueue 的主要特性

阻塞插入 (put())：当队列已满时，插入操作会被阻塞，直到有空间可用。

阻塞取出 (take())：当队列为空时，取出操作会被阻塞，直到有元素可用。

线程安全：内部使用锁或无锁机制实现多线程安全。

容量限制：可以设置固定容量，防止内存溢出。

### BlockingQueue 常用实现类

ArrayBlockingQueue  基于数组的有界阻塞队列，FIFO 顺序。

LinkedBlockingQueue 基于链表的阻塞队列，默认无界（可设容量）。

PriorityBlockingQueue 带优先级的无界队列，元素按优先级排序。

DelayQueue 元素带有延迟时间，延迟到期后才能取出。

SynchronousQueue 容量为 0，每次插入必须等待相应的取出操作。
### 相较于普通队列的额外方法
put(E e) 阻塞插入元素

take() 阻塞获取元素

offer(E e, time, unit) 超时等待插入

poll(time, unit) 超时等待获取

#### ArrayBlockingQueue

基于数组实现的有界阻塞队列。

先进先出 (FIFO) 顺序。

#### LinkedBlockingQueue

基于链表实现，默认无界 (可设容量限制)。


#### PriorityBlockingQueue

无界阻塞队列，元素按优先级排序，而非插入顺序。

需元素实现 Comparable 接口或提供 Comparator

#### DelayQueue
带延迟的无界阻塞队列，只有到期元素才能被取出。

元素需实现 Delayed 接口。

#### SynchronousQueue

容量为 0，每次插入必须等待取出操作，反之亦然

非常高效的点对点传输队列。

### ConcurrentMap
ConcurrentMap 是 java.util.concurrent 包中的接口，扩展了 Map 接口，
提供了多线程安全的键值存储容器。
与传统的同步集合（如 Hashtable、同步包装器 Collections.synchronizedMap()）
相比，ConcurrentMap 提供了更高效的并发操作，减少了锁竞争。

### ConcurrentMap 常用实现类

#### ConcurrentHashMap

高性能：基于分段锁机制（在 Java 8 中使用 CAS + 锁分离技术）。

线程安全：支持并发读取和部分并发写入。

不允许 null 键或 null 值。

```java
ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("apple", 3);
System.out.println(map.get("apple")); // 输出 3
```

### ConcurrentMap 接口新增方法
putIfAbsent()   若键不存在则插入

remove(key, value)  条件删除

replace(key, oldValue, newValue) 条件替换