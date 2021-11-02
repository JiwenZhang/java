package com.example.java20il2021.week3.aop.interceptor;

import com.example.java20il2021.week3.aop.MethodInvocation;

import java.lang.reflect.Method;

public class AfterReturnMethodInterceptor implements MethodInterceptor{
    private Object aspectObj;
    private Method aspectMethod;

    public AfterReturnMethodInterceptor(Object aspectObj, Method aspectMethod) {
        this.aspectObj = aspectObj;
        this.aspectMethod = aspectMethod;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object result = mi.proceed();
        if (result != null) {
            System.out.println("aha! we find its result, which is " + result);
            System.out.println("AfterReturn annotation will execute!");
            aspectMethod.setAccessible(true);
            aspectMethod.invoke(aspectObj);
        } else {
            System.out.println("there is a NULL result");
            System.out.println("AfterReturn annotation wont execute!");
        }
        return result;
    }
}
