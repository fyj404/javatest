package Atomic;

import java.util.concurrent.atomic.AtomicReference;

class User {
    String name;
    public User(String name) { this.name = name; }
}

public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<User> atomicUser = new AtomicReference<>(new User("Alice"));

        // CAS 更新对象
        atomicUser.compareAndSet(atomicUser.get(), new User("Bob"));
        System.out.println("更新后的用户：" + atomicUser.get().name);
    }
}

