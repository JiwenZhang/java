package com.example.java20il2021.week1;

/**
 *  synchronized -> object {
 *      while() {
 *          wait() / notify()
 *      }
 *      ...
 *  }
 *    T2, T3, T4   ->  T1 (obj)
 *
 *    wait vs sleep ??
 */

class WaitNotifyEx {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
    }
}

/**
 *  synchronized
 *      1. critical section
 *      2. wait() -> one waiting list
 *      3. unfair lock
 *      4. try lock ?
 *
 *  volatile + CAS + LockSupport (park / unPark)
 *  impl1 ReentrantLock
 *  private volatile int status = 0;
 *  private volatile Thread lockHolder = null;
 *  public void lock() {
 *      if(compareAndSet(this, locked, 0, 1)) {
 *          lockHolder = Thread.currentThread();
 *          ..
 *      } else if(Thread.currentThread() != lockHolder) {
 *          //wait for locked = false;
 *          //LockSupport.park(Thread.currentThread())
 *          append current thread to waiting list
 *      } else {
 *          //current thread is the lock holder
 *          status++;
 *      }
 *  }
 *
 *  impl2 waiting list (AbstractQueuedSynchronizer)
 *     head  ->  Node(Thread1)  ->  Node(Thread2)  ->  Node(Thread3)
 *
 *  unlock() ->
 *      unPark(..)
 *      status--;
 *
 *
 *  ReentrantLock lock = new ReentrantLock();
 *  Condition waitingList1 = lock.newCondition();
 *  Condition waitingList2 = lock.newCondition()
 *  ------------------------------ ------------------------------ ------------------------------
 *  producer -> BlockingQueue -> consumer
 *  1. queue is full -> block producers
 *  2. queue is empty -> block consumers
 *  3. consumers poll() -> notify producers
 *  4. producers offer(E e) -> notify consumers
 *
 *  class MyBlockingQueue<E> implements ..{
 *      //constructor
 *      private int size = 0;
 *      private int length = 0;
 *      private int start = 0; //offer -> [start]
 *      private int end = 0; //poll -> [end]
 *      private final ReentrantLock lock = new ReentrantLock();
 *      private final Condition isEmpty = lock.newCondition();  // Node(T1) -> Node(T2)
 *      private final Condition isFull = lock.newCondition();  // Node(T3) -> Node(T4)
 *      private final Object[] queue;
 *
 *      public MyBlockingQueue() {
 *          length = 50;
 *          queue = new Object[length];
 *      }
 *
 *      //producer
 *      public void offer(E e) {
 *          lock.lock();
 *          try {
 *              while (size == length) {
 *                  isFull.await();
 *              }
 *              queue[end++] = e;
 *              end %= length;
 *              isEmpty.signal();
 *              size++;
 *          } finally {
 *              lock.unlock();
 *          }
 *      }
 *
 *      //consumer
 *      public E poll() {
 *          lock.lock();
 *          try {
 *              while(size == 0) {
 *                  isEmpty().await();
 *              }
 *              Object ans = queue[start++];
 *              start %= length;
 *              isFull.signal();
 *              size--;
 *              return (E)ans;
 *          } finally {
 *              lock.unlock();
 *          }
 *      }
 *  }
 *
 *      [][][][][][][][][][]
 */


/**
 *  ThreadPool
 *
 *          producers   -> ThreadPool {  [x][x][x][][][][][]  ->  consumers(Worker Thread)  }
 *
 *      Executors vs ExecutorService
 *      ThreadPoolExecutor
 *      1. fixed thread pool
 *      2. cached thread pool
 *      3. scheduled thread pool(delayed message)
 *
 *      ForkJoinPool
 *      4. fork join pool
 *
 *      Callable + Future
 *
 *  ConcurrentHashMap vs HashTable
 *  CopyOnWriteList
 *      CountDownLatch
 *      Semaphore
 *      CyclicBarrier
 *  Atomic Library
 *
 *
 * Next monday 2:30 pm CDT
 *
 *
 * eden -> s0 / s1
 */