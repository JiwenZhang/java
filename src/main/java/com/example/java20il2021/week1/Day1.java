package com.example.java20il2021.week1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *      class -> this -> new instance() ,  new instance()
 *      OOP
 *          polymorphism : overriding / overloading
 *          inheritance : extends / implements
 *          abstraction :
 *              abstract class
 *                  1. constructor
 *                  2.
 *              interface
 *                  final
 *                  default method () {
 *                      ..
 *                  }
 *          encapsulation
 *              access modifiers : private, default, protected, public
 *      SOLID
 *          single responsibility
 *          open-close
 *          liskov substution
 *          interface segregation
 *          dependency inversion
 *
 *
 */
interface A1 {
    void get();
}

interface A2 {
    void helper();
}

class Day1Parent1 {

    public Day1Parent1(int a) {
        System.out.println("parent constructor");
    }

    public void print() {
        System.out.println("this is parent class");
    }
}

class Day1Sub1 extends Day1Parent1 implements A1, A2{

    public Day1Sub1(int a) {
        super(a);
        System.out.println("sub constructor");
    }

    @Override
    public void get() {
        //A1
    }

    @Override
    public void helper() {
        //A2
    }

    public static void main(String[] args) {
//        A1 d = new Day1Sub1();
//        d.print();
//        d.get();
//        d.helper();
    }
}


/**
 *  *      object vs primitive
 */

class ObjectVSPrimitive {
    public static void helper(int b) {
        b = 10;
    }

    // b 0x77 [object 0x99]
    public static void helper(List<Integer> b) {
        b.add(3);
    }

    public static void main(String[] args) {
//        int a = 5;
//        helper1(a);
//        System.out.println(a);
        //a 0x88  [object xx99]
        List<Integer> a = Arrays.asList(1, 2, 3, 4);
        System.out.println(a);
        //helper(0x99)
        helper(a);
        System.out.println(a);
    }

    /**
     *  b -> a -> heap object  X
     *  a 0x88 [heap object 0x99]
     *  b 0x77 [heap object 0x99]
     *  b 0x77 [new ArrayList<>() memory address]
     */
}


/**
 *  *  *      static(class instance / object)
 *                  new Instance() -> load class object -> new instance
 *                  Instance.staticAttribute -> load class object -> ..
 */

class StaticExample {

    private int a = 5;

    private static void helper1() {

    }

    private void helper2() {
        this.a = 10; //non-static
    }

    public static void main(String[] args) {
        StaticExample.helper1(); //load StaticExample class object ->
        new StaticExample().helper2();

        StaticExample s1 = new StaticExample(); //s1.a == 5;
        StaticExample s2 = new StaticExample(); //s2.a == 5;
        s1.helper2(); //s1.a == 10;
        System.out.println(s1.a); //s1.a == 10
        System.out.println(s2.a); //s2.a == 5
    }
}


/**
 *  *  *  *      String immutable
 */

class ImmutableString {
    private static String reverseString(String input) {
        /*
            solution1 :
                time complexity O(N)
                space complexity O(N)
                char[] arr = input.toCharArray();
                for(int l, r; ..)
                     swap(arr, l++, r--);
                return new String(arr);
            solution2 :
                StringBuilder sb = new StringBuilder();
                for(..)
                    sb.append(input.charAt(i));

            solution3 :
                new StringBuilder(input).reverse().toString();

        */
        String ans = "";
        for(char ch: input.toCharArray()) {
            ans = ch + ans; //ans = "a" ,  "ba",  "cba"
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(reverseString("abc"));
        System.out.println(reverseString(null));

    }
}

/**
 *              Throwable
 *            /         \
 *          Error       Exception(IO..)
 *                        \
 *                       RuntimeException(null pointer, index out of bound)
 *
 *        Runtime extends RuntimeException
 *        Compile Exception Exception
 *        1. what?
 *        2. how ?
 *        3. why ?
 */

class ExceptionTEST {
    private static void helper() throws IOException{
        throw new IOException();
    }

    public static void main(String[] args) {
        try {
            helper();
        } catch (Exception ex) {

            //
        }
    }
}
/**
 *  final vs finally
 *  final :
 *      class
 *      method
 *      variable
 *          Student(name);
 *          final Student stu = new Student("Tom");
 *          stu.setName("Jerry");
 *          stu = new Student("Jerry"); X
 *
 *  finally
 *      try {
 *          System.exit(); / system crash
 *      } catch(xx) {
 *
 *      } finally {
 *          release resource(connection / lock)
 *      }
 *
 *      try {
 *
 *      } catch(xx) {
 *
 *      }
 *
 *      lock.lock();
 *      try {
 *
 *      } finally {
 *          lock.unlock();
 *      }
 */
class FinalFinallyExample {
    public static void main(String[] args) {
        try {
            throw new RuntimeException("xx");
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            throw ex;
        } catch (Throwable ex) {

        } finally {
            System.out.println("this is finally");
        }
    }
}

/**
 *  public void helper() {
 *      int a = 10;
 *      int b = 10;
 *      ..
 *      for() {
 *          if() {
 *
 *          }
 *      }
 *  }
 *
 *
 *  generic
 *  array vs ArrayList vs LinkedList
 *  HashMap vs TreeMap(comparable / comparator)
 *  HashSet vs TreeSet
 *  PriorityQueue
 *  Deque vs Stack vs Queue
 *
 */

class Day1Test {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3);

        //1. listIterator -> listIterator set
        //2. for (int idx.. ) list.set(idx)

        // concurrent modification exception (fail fast) modCount
    }
}


/**
 * immutable
 *
 */

//final class Day1Student {
//    private final String name;
//    private final Address address;
//    private final List<Integer> list;
//
//    public Day1Student(String name, Address address, List<Integer> list) {
//        this.name = name;
//        this.address = address;
//        //deep copy this.list = list;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public List<Integer> getList() {
//        return //copied list;
//    }
//}

class Address {
    private String street;
}