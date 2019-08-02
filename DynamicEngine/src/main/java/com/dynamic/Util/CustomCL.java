package com.dynamic.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author lxy
 * @version 1.0
 * @date 2019/8/1 17:27
 */
public class CustomCL extends ClassLoader {
    private static Logger logger = LoggerFactory.getLogger(CustomCL.class);
    private String basedir; // 需要该类加载器直接加载的类文件的基目录
    private HashSet dynaclazns; // 需要由该类加载器直接加载的类名

    public CustomCL(String basedir, String[] clazns) {
        super(null); // 指定父类加载器为 null,防止父加载器加载类（同名会冲突）
        this.basedir = basedir;
        dynaclazns = new HashSet();
        loadAllClass(clazns);
    }

    private void loadAllClass(String[] clazns) {
        if (clazns == null) return;
        Arrays.stream(clazns).forEach(str -> {
            loadDirectly(str);
            dynaclazns.add(str);
        });
    }

    private Class loadDirectly(String name) {
        Class cls = null;
        StringBuffer dirpath = new StringBuffer(basedir);
        String classname = name.replace('.', File.separatorChar) + ".class";
        dirpath.append(File.separator + classname);
        File classF = new File(dirpath.toString());
        try {
            cls = instantiateClass(name, new FileInputStream(classF), classF.length());
        } catch (FileNotFoundException e) {
            logger.error("文件不存在——" + e.toString());
        }
        return cls;
    }

    private Class instantiateClass(String name, InputStream fin, long len) {
        byte[] raw = new byte[(int) len];
        try {
            fin.read(raw);
            fin.close();
        } catch (IOException e) {
            logger.error("IO异常——" + e.toString());
        }
        return defineClass(name, raw, 0, raw.length);//接收以字节数组表示的类字节码，并把它转换成 Class 实例
    }

    @Override
    //完成类的显式加载
    public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class cls = null;
        cls = findLoadedClass(name);//查看是否存在已装入的类
        if (!this.dynaclazns.contains(name) && cls == null)
            cls = getSystemClassLoader().loadClass(name);
        if (cls == null)
            throw new ClassNotFoundException(name);
        if (resolve)
            resolveClass(cls);
        return cls;
    }
}
