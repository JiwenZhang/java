package com.example.java20il2021.week1;

/**
 *  heap
 *      [ eden area ][s0][s1] young generation (stw)
 *      [                   ] old generation (concurrent mark and sweep / mark and compact)
 *      permanent generation (before java 8) -> metaspace
 *
 *      concurrent mark and sweep
 *          1. initial mark (STW)
 *          2. concurrent mark
 *          3. final mark (STW)
 *          4. remove objects
 *      G1
 *
 *      gc (gc root) -> STW(stop the world)
 *
 *      out of memory error
 *          StringReference
 *          SoftReference
 *          WeakReference
 *          PhantomReference ->
 *
 *          memory leak (IO / static)
 *
 *          heap dump ->
 *
 *          generation size
 *
 *  stack
 *      stack over flow error
 *      thread stack push method frame
 */

class Day3 {
    public static void main(String[] args) {

    }
}

/**
 *  Thread
 *     start thread -> runnable -> running -> wait() -> waiting -> notify() -> runnable
 *
 *  1. new Thread(runnable)
 *  2. extends Thread class
 */

class ThreadTest1 {
    public static void main(String[] args) {
        Runnable r = () -> System.out.println(Thread.currentThread().getName());
        Thread t1 = new Thread(r);
        t1.start();
        System.out.println(Thread.currentThread().getName());
    }
}

/**
 *  volatile + CAS(atomic operation) + synchronized
 */

/**
 *  volatile
 *      1. thread safe (no)
 *
 *  CPU                 CPU
 *  L1                  L1
 *  L2                  L2
 *
 *       main memory
 *
 *      2. visibility -> read from main memory
 *      3. happen - before(fence) (prevent reordering)
 *          write read read write write (mfence = StoreLoad) [read]  -> time line
 *
 * i++
 *   read i from main memory
 *   update i + 1 = 2
 *              ->  thread read 1
 *   write i back to main memory
 *
 */

class VolatileTest {
    private int a = 0;
    private volatile int b = 0;
    /**
     *  T1 -> a = 5;
     *        b = 6;
     *
     *  T2 -> if(b == 6)
     *             a == 5 (true)
     */

    private static volatile boolean flag = false;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while(!flag) {
                //..
            }
            /**
             *  while(true) {
             *  }
             */
            System.out.println("thread 1");
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println(flag);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
/**
 *  new Instance()
 *      -> Allocate memory location
 *              ..thread  -> read default value
 *      -> initialize values
 */

/**
 *  CAS -> thread safe + atomic operation
 *
 *  (Object, reference, expected value, new value) -> return true / false
 *  if(Object.reference.equals(old value)) {
 *      Object.reference = new value
 *      return true
 *  } else {
 *      return false;
 *  }
 */

/**
 *  synchronized (pessimistic lock)
 *      1. critical section
 *      2. scope -> object
 */

class SynchronizedTest {
    /**
     *  t1 get synchronized lock / monitor(t2 sleep 1s)
     *   :  this is static print
     *  t1 sleep 5s
     *  1s later
     *  t2 call get() (block)
     *  4s later
     *   : finish print
     *  t2 get monitor
     *   : this is static get
     */
    private synchronized void print() throws InterruptedException{
        System.out.println("this is static print");
        Thread.sleep(5000);
        System.out.println("finish print");
    }

    private static synchronized void get() {
        System.out.println("this is static get");
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest st = new SynchronizedTest();
        Thread t1 = new Thread(() -> {
            try {
                st.print();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                synchronized (st) {
                    st.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}

/**
 *  ReentrantLock
 *  BlockingQueue
 *
 *  ThreadPool
 */