package com.example.java20il2021.week4.day15.demo3;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnitUtil;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

public class MyJPADemo {

    private DataSource getDataSource() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setDatabaseName("OrmDemo");
        dataSource.setUser("postgres");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/university");
        return dataSource;
    }

    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.put( "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect" );
        properties.put( "hibernate.connection.driver_class", "org.postgresql.Driver" );
//        properties.put("hibernate.show_sql", "true");
        return properties;
    }

    private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties ){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan( "com/example/java20il2021/week4/day15/demo3");
        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
        em.setJpaProperties( hibernateProperties );
        em.setPersistenceUnitName( "demo-unit" );
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }

    public static void main(String[] args) {
        MyJPADemo jpaDemo = new MyJPADemo();
        DataSource dataSource = jpaDemo.getDataSource();
        Properties properties = jpaDemo.getProperties();
        EntityManagerFactory entityManagerFactory = jpaDemo.entityManagerFactory(dataSource, properties);
        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();


        EntityTransaction tx = em.getTransaction();
        tx.begin();
//        Student stu = em.find(Student.class, "5"); //persistent  / proxied / attached obj
//        Student s1 = new Student(); //transient object
//        s1.setName("Jerry");
//        em.persist(s1);
        List<Student> list = em.createQuery("select s from Student s join fetch s.teacher_students ts").getResultList();
        for(Student s: list) {
            for(Teacher_Student ts: s.getTeacher_students()) {
                Teacher t = ts.getTeacher();
                System.out.println(t);
            }
        }
        System.out.println(list);
        tx.commit();


//        List<Teacher> tList = em.createQuery("select t from Teacher t join t.teacher_students ts").getResultList();
//        Teacher t = tList.get(0);
//        System.out.println("**************************************");
//        System.out.println("class is loaded : " + unitUtil.isLoaded(t));
//        System.out.println("collection is loaded : " + unitUtil.isLoaded(t, "teacher_students"));
//        List<Teacher_Student> teacher_students = t.getTeacher_students();
//        System.out.println("collection is loaded : " + unitUtil.isLoaded(teacher_students, "teacher_students"));
//        System.out.println(teacher_students);
//        System.out.println("collection is loaded : " + unitUtil.isLoaded(teacher_students, "teacher_students"));
//        System.out.println("**************************************");
    }
}

/**
 *  JPA standard
 *      entity manager
 *  hibernate standard
 *      session
 *
 *  tomorrow :
 *      1. AOP homework
 *      2. server vs client : tomcat
 *      3. spring + spring boot  : IOC + AOP
 *      4. MVC
 *
 */