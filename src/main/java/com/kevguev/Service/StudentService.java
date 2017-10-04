package com.kevguev.Service;

import com.kevguev.DAO.StudentDao;
import com.kevguev.Entity.Course;
import com.kevguev.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    @Qualifier("fakedata")
    private StudentDao fakeStudentDaoImpl;

    public HashMap<String, Student> retrieveAllStudents(){
        return fakeStudentDaoImpl.retrieveAllStudents();
    }

    public Student getStudentById(String id){
        return this.fakeStudentDaoImpl.retrieveStudentById(id);
    }

    public void removeStudentById(String id) {
        this.fakeStudentDaoImpl.removeStudentById(id);
    }

    public void updateStudent(Student student){

        this.fakeStudentDaoImpl.updateStudent(student);
    }

    public void insertStudent(Student student) {
        this.fakeStudentDaoImpl.insertStudent(student);
    }

    public List<Course> retrieveCourses(String id) {
        return this.fakeStudentDaoImpl.retrieveCourses(id);
    }

    public Course retrieveCourse(String studentId, String courseId) {
        return this.fakeStudentDaoImpl.retrieveCourse(studentId, courseId);
    }

    public Course addCourse(String studentId, Course newCourse) {
        return this.fakeStudentDaoImpl.addCourse(studentId, newCourse);
    }
}
