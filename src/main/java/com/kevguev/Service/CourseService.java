package com.kevguev.Service;

import com.kevguev.DAO.StudentDao;
import com.kevguev.Entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    @Qualifier("fakedata")
    private StudentDao fakeStudentDaoImpl;

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
