package com.example.java20il2021.week3.aop;

import com.example.java20il2021.week3.aop.advice.*;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Proxy;

/**
 *      interface EmployeeService {
 *          get()
 *          print()
 *          ..
 *      }
 *
 *      class EmployeeServiceAspect {
 *          @Before
 *          public void beforeFunc() {
 *              ..
 *          }
 *
 *          @After
 *          public void afterFunc() {
 *
 *          }
 *      }
 *   1. Proxy.newInstance
 *      InvocationHandler {
 *          reflection scan EmployeeServiceAspect
 *              mi : methods
 *                  check annotations
 *
 *      }
 *  2. Interceptor
 *          BeforeInterceptor
 *          AfterInterceptor
 *
 *    proxy.get() / print() -> BeforeInterceptor -> original method -> AfterInterceptor
 *    List<Interceptor> interceptors
 *          for loop interceptors
 *
 */
public class AOPExample {
    public static void main(String[] args) {
        EmployeeService es = (EmployeeService) Proxy.newProxyInstance(
                AOPExample.class.getClassLoader(),
                new Class[]{EmployeeService.class},
                new JdkAOPInvocationHandler(new EmployeeServiceImpl1(), new EmployeeAspect())
        );
        //int val = es.get();
        //System.out.println(val);
        // es.print();
        es.test();
    }
}

interface EmployeeService {
    int get();
    void print();
    void test();
}

class EmployeeServiceImpl1 implements EmployeeService {
    @Override
    public int get() {
        System.out.println("this is get()");
        return 5;
    }

    @Override
    public void print() {
        System.out.println("print");
    }

    @Override
    public void test() {
        throw new NullPointerException("*** Customized Exception ***");
    }
}

class EmployeeAspect {
    /*
    @After
    public void after1Fun() {
        System.out.println("this is after1111");
    }
    @After
    public void after2Fun() {
        System.out.println("this is after2222");
    }
    @Before
    public void before1Fun() {
        System.out.println("this is before1111");
    }
    @After
    public void after3Fun() {
        System.out.println("this is after3333");
    }
    @Before
    public void before2Fun() {
        System.out.println("this is before2222");
    }
    */

    @AfterReturn
    public void afterReturn() throws Throwable{
        System.out.println("*** AfterReturn Instructions ***");
    }

    @AfterThrow
    public void afterThrow() throws Throwable{
        System.out.println("*** AfterThrow Instructions ***");
    }

    /*
    @Around
    public Object around1Fun(MethodInvocation mi) throws Throwable{
        System.out.println("-- -- --- this is around1111 before -------");
        Object res = mi.proceed();
        System.out.println("-- -- --- this is around1111 after -------");
        return res;
    }

    @Around
    public Object around2Fun(MethodInvocation mi) throws Throwable{
        System.out.println("-- -- --- this is around2222 before -------");
        Object res = mi.proceed();
        System.out.println("-- -- --- this is around2222 after -------");
        return res;
    }
     */
}


/**
 *   option1:   @after / @around / @before
 *   option2:   @PointCut(EmployeeService.class),
 *              @AfterReturn(triggered when we get result)
 *              @AfterThrow(triggered when we get exception)
 *
 *
 *   week4
 *   next Monday: jdbc + hibernate + spring data jpa
 *   next Wednesday: spring + spring boot
 *   next Thursday: network 7 layers + tcp + http + restful api
 *   next Friday: SpringMVC rest api implementation
 *
 *   week5
 *   microservice introduction -> leader / follower -> Raft
 *   CAP vs BASE
 *   data partitioning
 *   microservice: spring cloud
 *   global transaction, global unique id, distribution lock, logging
 *   cache cluster (cache-aside, write through, read through)
 *   message queue (kafka, rabbitmq, sqs) + CDC + idempotent service / duplicate message
 *
 *   week6
 *   aws
 *   jenkins pipeline
 *   daily work + agile scrum
 *   review interview questions
 *
 *   ...
 */

class Person {
    private final String name;
    private int age;
    public Person(String name, PersonBuilder personBuilder) {
        this.name = name;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }
}

class PersonBuilder {
    private String name;

    public PersonBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Person build() {
        return new Person(this.name, this);
    }
}