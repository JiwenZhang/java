package com.example.java20il2021.week3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton
 */

//1. Class.forName("..")    2. new SingletonEagerLoading()  3. static info  4. class loader
class SingletonEagerLoading {
    private static final SingletonEagerLoading obj = new SingletonEagerLoading();
    private final Map<Integer, Integer> map = new ConcurrentHashMap<>();
    private SingletonEagerLoading() {

    }
    public static SingletonEagerLoading getObj() {
        return obj;
    }
}

//static getObj()
class SingletonLazyLoading {
    private  static volatile SingletonLazyLoading obj;
    private SingletonLazyLoading() {

    }
    public static SingletonLazyLoading getObj() {
        if(obj == null) {
            synchronized (SingletonLazyLoading.class) {
                if (obj == null) {
                    obj = new SingletonLazyLoading();
                }
            }
        }
        return obj;
    }
}

//reflection,  serializable , cloneable
enum SingletonEnum {
    INSTANCE;
    private final Map<Integer, Integer> map;
    SingletonEnum() {
        map = new ConcurrentHashMap<>();
    }
}

 /**
 * Factory
 */

class PropertyFactory {
    public static SingletonLazyLoading getProperty() {
        return SingletonLazyLoading.getObj();
    }

    public static Properties getProperties() {
        return new Properties();
    }
}
// interface Binterface {}
// class A {
//     public void execute() {
//         Binterface b = BFactory.getB(4);
//     }
// }
// class BImpl1 implements Binterface{ }
// class BImpl2 implements Binterface{ }
// class BImpl3 implements Binterface{ }
/**
 * * Builder
**/
class Employee {
    private final String name;
    private final int age;
    private final int height;
    private final int weight;
    private final String id;
    private final String address;
    private final int salary;



    public Employee(String name, int age, int height, int weight, String id, String address, int salary) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.id = id;
        this.address = address;
        this.salary = salary;
    }


    public static void main(String[] args) {
//        Employee emp = Employee.EmployeeBuilder.setAge(5).setHeight(5).setName("Tom").build();
        //Employee emp = new Employee().setAge(5);
    }
}

/**
* Strategy
*/
interface OperationStrategy<T, R> {
    R operate(T t);
}
interface AddStrategy<T, R> extends OperationStrategy<T, R>{ }
interface MinusStrategy<T, R> extends OperationStrategy<T, R>{ }

class CalculatorImp1<T> {
    private OperationStrategy<T, ?> op;

    public CalculatorImp1() {
    }

    public <R> R calculate(T val, OperationStrategy<T, R> op) {

        return op.operate(val);
    }

    <R> R calculate(T val) {
        return (R)this.op.operate(val);
    }

    void opSetter(OperationStrategy<T, ?> op) {
        this.op = op;
    }

    public static void main(String[] args) {
        OperationStrategy<Integer, Integer> s = (v1) -> v1 + 5;
        CalculatorImp1<Integer> c1 = new CalculatorImp1<>();
        Integer res = c1.calculate(5, s);
    }
}



/**
 * Bridge
 */
class CalculatorImp2<T> {
    private OperationStrategy<T, ?> op;

    public CalculatorImp2(OperationStrategy<T, ?> op) {
        this.op = op;
    }
    <R> R calculate(T val) {
        return (R) this.op.operate(val);
    }
}

/**
* Composition -> has-A (inheritance / aggregation : is-A)
*/

class Node<T> {
    Node<T>[] child;
}

/**
* Facade (gateway)
 *
 * add=Calculator
 * run=Car
 * eat=Pizza
 * ..
*/


/**
 *  Prototype
*/


/** Observer
*/
class Topic {
    private List<Observer> observerList = new ArrayList<>();

    public void subscribe(Observer o) {
        observerList.add(o);
    }

    public void send(String msg) {
        for(Observer observer: observerList) {
            observer.receive(msg);
        }
    }
}

class Observer {
    public boolean receive(String msg) {
        //..
        return true;
    }
}

/** Adapter
**/
//interface Plugin {
//    void execute();
//}
//interface USPlugin extends Plugin{}
//interface CNPlugin extends Plugin{}
//interface Computer{}
//class USComputer implements Computer{
//    public void plugin(USPlugin plugin) {
//        plugin.execute();
//    }
//}
//
//class CNComputer implements Computer{
//    public void plugin(CNPlugin plugin) {
//        plugin.execute();
//    }
//}
//
//class USPluginAdapter implements USPlugin {
//    private Computer computer;
//
//    public USPluginAdapter(Computer computer) {
//        this.computer = computer;
//    }
//
//    @Override
//    public void execute() {
//        //this.computer;
//    }
//}


/**
 * Decorator
*  Static Proxy / Dynamic Proxy
 * reflection
*/
