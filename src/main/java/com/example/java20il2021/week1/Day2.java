package com.example.java20il2021.week1;

import java.util.*;

public class Day2 {
}
/**
 *  Collection
 *      1. objects
 *      2. list, set, map
 *
 *
 */

class CollectionExample {
    public static void main(String[] args) {
        /*List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add("str");
        Integer val = list1.get(0);
        List<> list2 = new ArrayList<>();
        list2.add("abc");
        list2.add(123l);
        Object str = list2.get(0);*/
    }
}

/**
 *   *  array vs ArrayList vs LinkedList
 *      ArrayList
 *          [1, 2, 3, 4]
 *          insert(0, 5)
 *          [5, 1, 2, 3, 4]
 *      LinkedList
 *          Node(1) <-> Node(2) <-> Node(3)..
 *      Array
 *
 */

class MyListTestDay2 {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 4};
        int[] arr2 = {1, 2, 3, 4};
        int[] arr3 = new int[Integer.MAX_VALUE];
    }
}

/**
 *  *  HashMap vs TreeMap(comparable / comparator)
 *      1. put(K, V)
 *      2. putVal(hash(key's hashcode) -> hash value, key, value, false, true);
 *      3. initialize array
 *      4. verify arr[i = (n - 1) & hash] first node == null ?
 *             new node : search LinkedList / RedBlackTree
 *      5. oldKey == newKey || oldKey.equals(newKey)
 *              if same -> update value -> return
 *              if not  -> look up key in  LinkedList / RedBlackTree
 *      6. if new key
 *          -> new node (LinkedList -> RedBlackTree)
 *          -> resize hashmap
 *      N -> all nodes
 *      M -> arr[i] node count -> log(m) / O(m)
 *      arr[i] -> O(1)
 *      --------------------------------------------------------
 *      Student s1 = new Student(1, "Tom");
 *      Student s2 = new Student(1, "Tom");
 *      s1 == s2 -> return false
 *      s1.equals(s2) -> (this == obj) -> false
 *      ..
 *      override equals -> s1.equals(s2) -> true
 *
 */
class HashMapExample {
    private static class Student {
        int id;
        String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return id == student.id && Objects.equals(name, student.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
//            return 5;
        }
    }
    public static void main(String[] args) {
        Student s1 = new Student(1, "Tom");
        Student s2 = new Student(1, "Tom");
        Map<Student, Integer> stuMap = new HashMap<>();
        //case1 :
//        stuMap.put(s1, 5);
////        stuMap.put(s2, 10);
//        s1.name = "Jerry";
//        System.out.println(stuMap.get(s2));
    }
}

/**
 *  comparable vs comparator
 */

class TreeMapExample {
    private static class Student {
        int id;

        public Student(int id) {
            this.id = id;
        }
    }
//    public static void main(String[] args) {
//        Student s1 = new Student(1);
//        Student s2 = new Student(1);
//        // Comparable<? super K> k = (Comparable<? super K>) key;
//        TreeMap<Student, Integer> map = new TreeMap<>((x1, x2) -> x1.id - x2.id);
//        map.put(s1, 5);
//        System.out.println(map.get(s2));
//    }
    public static void main(String[] args) {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 4);
        for(int a: arr) {
            arr.set(0, a);
        }
        System.out.println(arr);
    }
}




/**
 *  *  HashSet vs TreeSet vs LinkedHashSet
 *  HashMap: key -> unique
 */


/**
 *  *  PriorityQueue(comparator)
 *      offer / poll
 *
 *  *  Deque vs Stack vs Queue
 */

class StringInteger {
    public static void main(String[] args) {
        //constant string pool
        String s1 = "abc";
        String s2 = "abc";
        String s3 = new String("abc");
        String s4 = s3.intern();
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s4);

        //constant integer pool
        Integer v1 = -129;
        Integer v2 = -129;
        System.out.println(v1.equals(v2));

        Class<?> clazz = Integer.class;
        clazz = String.class;
    }
}

/**
 *    jvm
 *    heap (generation, gc(minor gc, major gc, full gc = minor + major))
 *    strong vs soft vs weak vs phantom reference
 *    vs
 *    stack
 *
 *    concurrent - modification - exception
 *    multi-threading..
 */

class MyLinkedList<S> {
    private Node<S> first;

    private static class Node<S> {

    }
}

/**
 *  1
 *      2
 *          3
 *              4
 *     ----------
 *         2
 *      1       3
 *                  4
 *      ------
 *          2
 *       1      3
 *            5   4
 *
 *
 *
 *      int[] -> [x][x][x] -> arr[4] -> O(1)
 *
 *      List<Integer>  size = N
 *      for(int i = 0; i < list.size(); i++) {
 *          //print
 *      }
 *      O(N)
 *
 *      [ , 10], target
 *
 *
 *  LFU
 *  insert(1, 1, 2,3, 4, 5)  max size = 2
 *  [{1: 1}] -> [{1 : 2}], [{1:2}, {2:1}]
 *  TreeMap<Frequency, Node>
 *  Map<Value, Node>
 *
 *  Trapping water
 */