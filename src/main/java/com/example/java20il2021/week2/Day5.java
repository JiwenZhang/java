package com.example.java20il2021.week2;

/**
 *  Oracle live sql
 *  Tuesday : query : select / where / sub query / aggregation function / rank(), dense_rank() / union, except, intersect
 *  Wednesday : group by + join / transaction
 *  Thursday : index / execution plan / oracle architecture
 *  Friday : normalization / table design
 *
 *  ThreadPool
 *
 *          producers   -> ThreadPool {  [x][x][x][][][][][]  ->  consumers(Worker Thread)  }
 *
 *      Executors vs ExecutorService
 *      ThreadPoolExecutor (core pool size, max pool size, alive time, timeunit, blocking queue)
 *      1. fixed thread pool = core pool size == max pool size
 *      2. cached thread pool = core pool size < max pool size
 *         processor number + 1
 *         task time = cpu time + io time
 *         processor number *  1 / (task time - io time) + 1
 *
 *         task = 0.2s cpu time + 0.8s io time
 *         1 / 0.2s cpu time = 5 threads
 *
 *      3. scheduled thread pool(delayed message)
 *
 *     len = 60 = [Node][][][]..[][]   60s
 *                      k
 *     len = 60 = [Node][][]  ..[][]   60min
 *                  j
 *     len = 24 = [Node][]..[] 24h
 *                      i
 *     len = 365 = [][] days
 *
 *      ForkJoinPool
 *      4. fork join pool(stealing tasks)
 *          ThreadPool {  [x][x][x][][][][][]  ->  consumers(Worker Thread[][][][][])  }
 *  ************************************
 *  ConcurrentHashMap vs Hashtable
 *  CopyOnWriteList
 *      CountDownLatch(50) ->
 *              countDown() -> 50 - 1 -> 0
 *  Semaphore
 *  CyclicBarrier
 *  Atomic Library
 *
 *
 *
 *  --Callable + Future + CompletableFuture
 *
 *  synchronized(obj1) -> wait + notify
 *              obj1 -> waiting list
 *  reentrantLock
 *      1. condition   ->waiting list1 -> await() / signal()
 *      2. condition   ->waiting list2
 */