package com.example.java20il2021.week3;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  template
 */

class Car {
    //..
}
class BMWCar extends Car{}
class MercedesCar extends Car{}

/**
 * iterator
 *      hasNext()
 *      remove()
 *      next()
 */


/**
 *  decorator / static proxy
 */
interface FileGenerator {
    void write();
}
@Component
final class PDFFileGenerator implements FileGenerator {
    private int a = 5;
    public final void write() {
        System.out.println("this is PDF write function");
    }
    public void print() {
        System.out.println(a);
    }

}
class PDFXFileGenerator implements FileGenerator {
    private PDFFileGenerator gen;
    public PDFXFileGenerator(PDFFileGenerator gen) {
        this.gen = gen;
    }

    @Override
    public void write() {
        //..
        gen.write();
        //..
    }
}

/**
 *  dynamic proxy
 */
class DynamicProxyTest1 {
    public static void main(String[] args) {
        FileGenerator generator = (FileGenerator) Proxy.newProxyInstance(
                DynamicProxyTest1.class.getClassLoader(),
                new Class[]{FileGenerator.class},
                new FileGeneratorInvocationHandler(new PDFFileGenerator())
        );
//        generator.write();
//
        Class<?> clazz = PDFFileGenerator.class;
    }
}
class FileGeneratorInvocationHandler implements InvocationHandler {
    private Object obj;

    public FileGeneratorInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before execution");
        Object res = method.invoke(obj, args);
        System.out.println("after execution");
        return res;
    }
}


/**
 *   reflection
 */
class ReflectionTest {
    public static void main(String[] args) throws Exception{
        Class<?> clazz = PDFFileGenerator.class;
        PDFFileGenerator generator = (PDFFileGenerator)clazz.getDeclaredConstructor().newInstance();
        generator.print();
        Field f = clazz.getDeclaredField("a");
        f.setAccessible(true);
        f.set(generator, 10);
        generator.print();
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Inject {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Component {}

@Component
interface EmployeeService {}
@Component
class EmployeeSS1 implements EmployeeService {
    @Override
    public String toString() {
        return "EmployeeSS1{}]n";
    }
}
@Component
class EmployeeSS2 implements EmployeeService {
    @Override
    public String toString() {
        return "EmployeeSS2{}\n";
    }
}
@Component
class EmployeeSS3 implements EmployeeService {
    @Override
    public String toString() {
        return "EmployeeSS3{}\n";
    }
}

@Component
class Calculation {
    @Inject
    private EmployeeService employeeSS1;
    @Inject
    private EmployeeSS2 employeeSS2;
    @Inject
    private EmployeeSS3 employeeSS3;
    @Inject
    private FileGenerator fileGenerator;

    public void print() {
        fileGenerator.write();
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "employeeSS1=" + employeeSS1 +
                ", employeeSS2=" + employeeSS2 +
                ", employeeSS3=" + employeeSS3 +
                ", fileGenerator=" + fileGenerator +
                "} \n";
    }
}

class InstanceContainer {
    private final Map<Class<?>, Object> instanceMap = new ConcurrentHashMap<>();

    public void start() throws Exception{
        List<Class<?>> classes = scan();
        initializeInstance(classes);
        FileGenerator generator = (FileGenerator) Proxy.newProxyInstance(
                DynamicProxyTest1.class.getClassLoader(),
                new Class[]{FileGenerator.class},
                new FileGeneratorInvocationHandler(new PDFFileGenerator())
        );
        insertComponent(FileGenerator.class, generator);
        doInject(classes);
    }

    private List<Class<?>> scan() throws Exception{
        List<Class<?>> list = new ArrayList<>();
        list.add(EmployeeSS1.class);
        list.add(EmployeeSS2.class);
        list.add(EmployeeSS3.class);
        list.add(Calculation.class);
        return list;
    }

    private void insertComponent(Class<?> clazz, Object obj) {
        this.instanceMap.put(clazz, obj);
    }

    private void initializeInstance(List<Class<?>> components) throws Exception{
        for(Class<?> clazz: components) {
            Object obj = clazz.getDeclaredConstructor().newInstance();
            instanceMap.put(clazz, obj);
        }
    }

    private void doInject(List<Class<?>> components) throws Exception{
        for(Class<?> clazz: components) {
            Field[] fields = clazz.getDeclaredFields();
            Object objHolder = instanceMap.get(clazz);
            for(Field f: fields) {
                Class<?> type = f.getType();
                for(Annotation annotation: f.getDeclaredAnnotations()) {
                    if(annotation.annotationType() == Inject.class) {
                        Object exposedObj = instanceMap.get(type);
                        f.setAccessible(true);
                        f.set(objHolder, exposedObj);
                    }
                }
            }
        }
    }

    public Map<Class<?>, Object> getInstanceMap() {
        return this.instanceMap;
    }
}

class ComponentTest {
    public static void main(String[] args) throws Exception{
        InstanceContainer instanceContainer = new InstanceContainer();
        instanceContainer.start();
        Map<Class<?>, Object> map = instanceContainer.getInstanceMap();
        System.out.println(map);
//        ((Calculation)map.get(Calculation.class)).print();
    }
}

/**
 * 1:30 CDT / 2:30 EDT / 11:30 PDT
 */