package com.zjx.thread.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description 原子更新引用
 * @Author Carson Cheng
 * @Date 2019/3/13 17:26
 * @Version V1.0
 **/
public class AtomicReferenceTest {

    public static AtomicReference<User> atomicUserRef = new AtomicReference<User>();

    public static void main(String[] args) {
        User User = new User("conan", 15);
        atomicUserRef.set(User);
        User updateUser = new User("Shinichi", 17);
        atomicUserRef.compareAndSet(User, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}
