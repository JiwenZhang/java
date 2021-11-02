package com.example.java20il2021.week3.aop.interceptor;

import com.example.java20il2021.week3.aop.MethodInvocation;

public interface MethodInterceptor {
    Object invoke(MethodInvocation mi) throws Throwable;
}
