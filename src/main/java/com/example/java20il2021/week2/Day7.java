package com.example.java20il2021.week2;

/**
 *
 *      emp table
 *      emp_id(pk), first_name, last_name, dept_id(fk)
 *          1     ,  xx         xx          1
 *          2     ,  ..                     2
 *
 *      dept table
 *      dept_id(pk), dept_name
 *          1      ,  "IT"
 *          2      ,  "Account"
 *          3      ,  "HR"
 *
 *
 *      A       B
 *      1       4
 *      2       5
 *      3
 *      cross join :
 *          select
 *          from A, B
 *          where ..
 *          1,  4
 *          1,  5
 *          2,  4
 *          2,  5
 *          3,  4
 *          3,  5
 *          select
 *          from emp, dept
 *          where emp.dept_id = dept.dept_id
 *      inner join / join
 *          select
 *          from emp join dept on emp.dept_id = dept.dept_id
 *      outer join
 *          select
 *          from emp left join dept on emp.dept_id = dept.dept_id
 *
 *          select
 *          from dept right join emp on emp.dept_id = dept.dept_id
 *
 *   *    *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *      Q:  count emp number in each department
 *          select
 *          from dept left join emp on emp.dept_id = dept.dept_id

 *      dept_id(pk), dept_name      emp_id(pk), first_name, last_name, dept_id(fk)
 *          1      ,  "IT"              1     ,  xx         xx          1
 *          2      ,  "Account"         2     ,  ..                     2
 *          3      ,  "HR"              null    null        null        null
 *
 *          select d.dept_id, (dept_name), count(emp_id)
 *          from dept d left join emp e on e.dept_id = d.dept_id
 *          group by d.dept_id, (dept_name)
 *
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *      emp_id(pk), first_name, last_name, salary,  dept_id(fk)
 *          1     ,  xx         xx         500        1
 *          2     ,  ..                    700        2
 *
 *    rank() vs dense_rank()
 *      select e.*, rank() over (order by salary desc) as rank
 *      from emp e
 *
 *      emp_id(pk), first_name, last_name, salary,  dept_id(fk),  rank
 *          2     ,  ..                    700        2             1
 *          1     ,  xx         xx         700        1             1
 *                                         500                      3
 *
 *      select e.*, rank() over (partition by dept_id order by salary desc) as rank
 *      from emp e
 *
 *      emp_id(pk), first_name, last_name, salary,  dept_id(fk),  rank
 *          2     ,  ..                    700        2             1
 *          1     ,  xx         xx         500        1             1
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *    Transaction (begin / rollback / commit)
 *
 *    example :
 *      begin tx
 *      user update 100 rows
 *      rollback
 *
 *      emp_id(pk), first_name, last_name, salary,  dept_id(fk),  rank,  rollback_pointer
 *          2     ,  ..                    800        2             1        |
 *                                                                           2   ... 700        2             1
 *
 *
 *    ACID :
 *          atomicity
 *          consistency
 *          isolation level
 *          durability
 *
 *   isolation level
 *         oracle(read committed, serializable, read only)
 *         mysql (read uncommitted, read committed, repeatable read, serializable)
 *
 *   Read UnCommitted(dirty read, non-repeatable read, phantom read)
 *   tx1    begin           insert / update / delete                    commit
 *   -----------------------------------------------------------------------------> timeline
 *   tx1      begin   select          !=            select
 *
 *   Read Committed(non-repeatable read, phantom read)
 *   tx1    begin           update / delete        commit
 *   -----------------------------------------------------------------------------> timeline
 *   tx1      begin   select             !=                  select
 *
 *
 *   Repeatable Read(phantom read)  next-key lock
 *   tx1    begin           insert        commit
 *   -----------------------------------------------------------------------------> timeline
 *   tx1      begin   select             !=                   select
 *
 *
 *   Serializable
 *   tx1    begin           insert delete update       commit
 *   -----------------------------------------------------------------------------> timeline
 *   tx1      begin   select             ===                     select
 *
 *
 *
 *   exclusive lock (block ex + share lock)
 *      select...for update, update, delete, insert
 *   share lock (block ex lock)
 *      select .. for share
 *
 *   id
 *   (<1) gap lock
 *   1  ex
 *   (2)  gap lock
 *   3  ex
 *   (4)  gap lock
 *   5  ex
 *   (>5)  gap lock
 *   select * for update
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   lost update
 *           user1(doc1)          user2(doc2->doc3)
 *
 *                 doc3(version / timestamp)
 *
 *
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *   tx_id -> ascending
 *   MVCC(tx id, rollback pointer, snapshot read / read view)
 *
 *
 *
 *
 *   query: rank/dense_rank + query + order by
 *   index: non-cluster/cluster/index organized table
 *          execution plan(hint)
 *
 */


// Java code to illustrate Reentrant Locks
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

class worker implements Runnable {
    String name;
    ReentrantLock re;
    public worker(ReentrantLock rl, String n)
    {
        re = rl;
        name = n;
    }
    public void run()
    {
        re.lock();

        // Returns True if lock is free
        try
        {
            Date d = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
            System.out.println("task name - "+ name
                    + " outer lock acquired at "
                    + ft.format(d)
                    + " Doing outer work");

            // Getting Inner Lock

            d = new Date();
            ft = new SimpleDateFormat("hh:mm:ss");
            System.out.println("task name - "+ name
                    + " inner lock acquired at "
                    + ft.format(d)
                    + " Doing inner work");
            System.out.println("Lock Hold Count - "+ re.getHoldCount());

            System.out.println("Lock Hold Count - " + re.getHoldCount());
            System.out.println("task name - " + name + " work done");
        }
        finally
        {
            //Outer lock release
            System.out.println("task name - " + name +
                    " releasing outer lock");

            re.unlock();
            System.out.println("Lock Hold Count - " +
                    re.getHoldCount());
        }
    }
}

class Test {
    static final int MAX_T = 4;
    public static void main(String[] args)
    {
        ReentrantLock rel = new ReentrantLock(false);
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
        Runnable w1 = new worker(rel, "Job1");
        Runnable w2 = new worker(rel, "Job2");
        Runnable w3 = new worker(rel, "Job3");
        Runnable w4 = new worker(rel, "Job4");
        pool.execute(w1);
        pool.execute(w2);
        pool.execute(w3);
        pool.execute(w4);
        pool.shutdown();
    }
}

/**
 *    [w4][w3][w2][w1]  -> T1 / 2 / 3/ 4
 *
 *
 *    1. T1 w1 -> locker holder
 *    2. T2 w2 -> wait for lock
 *    3. T3 w3
 *    4. T4 w4
 *    waiting -> T2 -> T4 -> T3
 */