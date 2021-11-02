package com.example.java20il2021.week3.aop.interceptor;

import com.example.java20il2021.week3.aop.MethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AfterThrowMethodInterceptor implements MethodInterceptor{

    private Object aspectObj;
    private Method aspectMethod;

    public AfterThrowMethodInterceptor(Object aspectObj, Method aspectMethod) {
        this.aspectObj = aspectObj;
        this.aspectMethod = aspectMethod;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable{
        Object result = null;
        try {
            result = mi.proceed();
        } catch (InvocationTargetException e) {
            System.out.println("aha! we find the Exception");
            e.printStackTrace();
            System.out.println("AfterThrow annotation will execute!");
            aspectMethod.setAccessible(true);
            aspectMethod.invoke(aspectObj);
        } finally {
            return result;
        }
    }
}
