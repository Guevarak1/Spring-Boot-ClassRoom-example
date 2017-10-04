package com.kevguev.DAO;

import com.kevguev.Entity.Course;
import com.kevguev.Entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Qualifier("mongodata")
public class MongoStudentDaoImpl implements StudentDao{

    @Override
    public HashMap<String, Student> retrieveAllStudents() {
        return null;
    }

    @Override
    public Student retrieveStudentById(String id) {
        return null;
    }

    @Override
    public List<Course> retrieveCourses(String id) {
        return null;
    }

    @Override
    public Course retrieveCourse(String studentId, String courseId) {
        return null;
    }

    @Override
    public List<String> retrieveGrades(String studentId) {
        return null;
    }

    @Override
    public void insertStudent(Student student) {

    }

    @Override
    public Course addCourse(String studentId, Course newCourse) {
        return null;
    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void removeStudentById(String id) {

    }
}
