package com.dynamic.Util;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lxy
 * @version 1.0
 * @date 2019/8/5 15:09
 */
public class ClassLoadUtils {

    /* 创建一个读写锁 */
    private final static ReadWriteLock rwlock = new ReentrantReadWriteLock();


    private ClassLoadUtils() {
    }

    private static class InstanceHolder {
        private static CustomCL instance = new CustomCL("", null);
        public static void doUpgrade(String baseDir, String loadClass) {
            instance = new CustomCL(baseDir, loadClass.split(","));
        }
    }

    // 收到升级控制实体的开始升级命令消息时，会触发该方法被调用
    public static void Upgrade(String baseDir, String loadClass) {
        rwlock.writeLock().lock();
        InstanceHolder.doUpgrade(baseDir, loadClass);
        rwlock.writeLock().unlock();
    }

    public static Class loadClass(String name) throws ClassNotFoundException {
        /* 上读锁 */
        rwlock.readLock().lock();
        Class cls = InstanceHolder.instance.loadClass(name, false);
        rwlock.readLock().unlock();
        return cls;
    }

}




