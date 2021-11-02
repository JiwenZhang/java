package com.example.java20il2021.week4;

import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CglibProxyDemo {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ArrayList.class); //set proxy class
        enhancer.setCallbacks(new Callback[]{new SubClass()}); //
        List<Integer> arr = (List)enhancer.create();
        arr.add(1);
        System.out.println();

        arr.remove(0);
    }
}
//cglib method interceptor
class SubClass implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("MethodInterceptor start...");
        System.out.println(method);

        method.setAccessible(true);
        Object res = methodProxy.invokeSuper(o, args);
        System.out.println("MethodInterceptor ending...");
        return res;
    }
}

interface FileGenerator { }
class PDF implements FileGenerator {}
class Word implements FileGenerator {}
class FileInvocationHandler {
    public static void main(String[] args) {
        FileGenerator pdf = new PDF();
        FileGenerator word = new Word();
        if(PDF.class.isAssignableFrom(word.getClass())) {
            System.out.println("is pdf");
        }
    }
}
