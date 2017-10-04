package com.kevguev.DAO;

import com.kevguev.Entity.Course;
import com.kevguev.Entity.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface StudentDao {
    //get
    HashMap<String, Student> retrieveAllStudents();

    Student retrieveStudentById(String id);

    List<Course> retrieveCourses(String id);

    Course retrieveCourse(String studentId, String courseId);

    //post
    void insertStudent(Student student);

    Course addCourse(String studentId, Course newCourse);

    //put
    void updateStudent(Student student);

    //delete
    void removeStudentById(String id);

}
