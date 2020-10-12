package com.atguigu.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * <p>
 * 读的时候不锁 写的时候不锁
 */


class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    public ReadWriteLock readWriteLock =new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();



        try {
            System.out.println(Thread.currentThread().getName() + "\t 开始写入"+key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成"+key);
        } catch (Exception e){

        } finally {
            readWriteLock.writeLock().unlock();
        }


    }

    public void get(String key) {



        readWriteLock.readLock().lock();



        try {
            System.out.println(Thread.currentThread().getName() + "\t 读取诗句");
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成");

        } catch (Exception e){

        } finally {
            readWriteLock.readLock().unlock();
        }





    }

}

public class ReadWriteLockDemo {
    public static void main(String[] args) {

        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int tem = i;
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                myCache.put(tem + "", tem + "");
            }, "A").start();
        }


        for (int i = 0; i < 5; i++) {
            final int tem = i;
            new Thread(() -> {
                myCache.get(tem + "");
            }, "A").start();
        }

    }
}
