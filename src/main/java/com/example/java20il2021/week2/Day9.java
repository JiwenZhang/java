package com.example.java20il2021.week2;

/**
 *  super key (candidate key  (primary key))
 *  1st NF
 *      id,      name
 *      1      Tom,Yang
 *
 *  2nd NF (1NF + non-prime attributes fully depend on prime attributes)
 *      book_id,  book_name,   author_id,   author_name
 *        1         Java           A1           Tom
 *        1         Java           A2           Jerry
 *        2         C#             A1           Tom
 *
 *      primary key(book_id, author_id)
 *
 *      book_id -> book_name
 *      book_id -> author_name
 *      author_id -> book_name
 *      author_id -> author_name
 *
 *  3rd NF (2NF + no transitive relation)
 *      author_id,   author_name,    address_id,   street..
 *        A1           Tom              ..
 *        A2           Jerry            ..
 *        A1           Tom              ..
 *
 *      author_id  ->  address_id  ->  street
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *  1 - 1
 *  1 - m
 *      class(id(pk)) 1 - m students(id(pk), c_id(fk))
 *  m - m
 *      student m - m teacher
 *      student(id) 1 - m student_teacher(id, s_id, t_id)  m - 1 teacher(id)
 *
 *  Survey
 *      v1 -> 20 questions (firstname, last_name, address..)
 *      v2 -> 15 questions (car, ..)
 *      ..
 *
 *      solution1
 *          id, first_name, last_name, address..car,..
 *          1      null        null     null
 *      solution2
 *          table1: survey1(id..)
 *          table2: survey2(id..)
 *      solution3
 *          table1: common columns
 *          subTable1: survey1
 *          subTable2: survey2
 *      solution4
 *          id, 1,  2,  3,  4,...20
 *      solution5
 *          survey many to many question
 *      solution6
 *          entity-attribute-value
 *          table1
 *          id,     key,      value,      type,       document_id(fk)
 *          1    "firstName"   "Tom"      "string"       1
 *          2    "age"          5         "Integer"      1
 *
 *          table2(document)
 *          id,  description
 *          1       ..
 *      solution7
 *          table1: common columns, json

 * Assume we are working on a university system that tracks students in the university.
 * For each student, we want to store basic information (ID, first & last name, current GPA).
 * And, for each student we also want to store each class the student has taken.
 * For each class taken, we want to track the class number (e.g. MATH101), semester taken (e.g. FALL2018) and grade received (A, A-, B etc.).
 *
 * student (id(pk), first_name, last_name, gpa)
 * student_class (id, s_id(fk), c_id(fk), grade)
 * class (id(pk), class_number, semester)
 *
 */