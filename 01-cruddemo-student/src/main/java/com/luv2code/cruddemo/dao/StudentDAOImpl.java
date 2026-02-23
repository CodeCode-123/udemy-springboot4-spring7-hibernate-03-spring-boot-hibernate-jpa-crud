package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {
    private EntityManager entityManager;
    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void save(Student theStudent) {
        entityManager.persist(theStudent);
    }
    @Override
    public Student findById(Integer id) {
        return entityManager.find(Student.class, id);
    }
    @Override
    public List<Student> findAll() {
        // create query, JPQL, Student is the Java class name
        TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student", Student.class);
        // return query results
        return theQuery.getResultList();
    }
    @Override
    public List<Student> findByLastName(String theLastName) {
        // create query
        TypedQuery<Student> theQuery = entityManager.createQuery(
                "FROM Student WHERE lastName=:theData", Student.class);
        // set query parameters
        theQuery.setParameter("theData", theLastName);
        // return query result
        return theQuery.getResultList();
    }
    @Override
    @Transactional
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }
    @Override
    @Transactional
    public void delete(Integer id) {
        // retrieve the student
        Student theStudent = entityManager.find(Student.class, id);
        // delete the student
        entityManager.remove(theStudent);
    }
    // When performing a DML operation, you are not expecting a result set of specific entity objects back
    // but rather the number of rows affected as an int
    @Override
    @Transactional
    public int deleteAll() {
        return entityManager
                .createQuery("DELETE FROM Student") // DON'T use .createQuery("DELETE FROM Student", Student.class);
                .executeUpdate();
    }
}
