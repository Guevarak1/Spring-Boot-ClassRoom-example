package com.kevguev.DAO;

import com.kevguev.Entity.Course;
import com.kevguev.Entity.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

@Repository
@Qualifier("fakedata")
public class FakeStudentDaoImpl implements StudentDao {

    private static HashMap<String,Student> students = new HashMap<>();
    static {
        Course course1 = new Course("Course1", "Spring", "10 Steps", Arrays
                .asList("Learn Maven", "Import Project", "First Example",
                        "Second Example"));
        Course course2 = new Course("Course2", "Spring MVC", "10 Examples",
                Arrays.asList("Learn Maven", "Import Project", "First Example",
                        "Second Example"));
        Course course3 = new Course("Course3", "Spring Boot", "6K Students",
                Arrays.asList("Learn Maven", "Learn Spring",
                        "Learn Spring MVC", "First Example", "Second Example"));
        Course course4 = new Course("Course4", "Maven",
                "Most popular maven course on internet!", Arrays.asList(
                "Pom.xml", "Build Life Cycle", "Parent POM",
                "Importing into Eclipse"));

        Student student1 = new Student("AAAAA", "Kevin","CompSci", new ArrayList<>(Arrays
                .asList(course1,course2,course3,course4)));
        Student student2 = new Student("AAAAB", "Mark","Finance", new ArrayList<>(Arrays
                .asList(course1,course2,course3,course4)));
        Student student3 = new Student("AAAAC", "Brian","CompSci", new ArrayList<>(Arrays
                .asList(course1,course2,course3,course4)));

        students.put(student1.getId(),student1);
        students.put(student2.getId(), student2);
        students.put(student3.getId(), student3);

            }

    @Override
    public HashMap<String, Student> retrieveAllStudents(){
        return students;
    }

    @Override
    public Student retrieveStudentById(String id){
        return students.get(id);
    }

    @Override
    public List<Course> retrieveCourses(String  id) {
        Student student = retrieveStudentById(id);
        if(student == null){ return null;}
        return student.getCourses();
    }

    @Override
    public void removeStudentById(String id) {
        //Student student = retrieveStudentById(id);
        students.remove(retrieveStudentById(id));
    }

    @Override
    public void updateStudent(Student student){


        Student s = retrieveStudentById(student.getId());
        s.setCourses(student.getCourses());
        s.setName(student.getName());
        s.setDescription(student.getDescription());
    }

    @Override
    public void insertStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public Course retrieveCourse(String id, String courseId) {
        Student student = retrieveStudentById(id);

        if(student == null){ return null;}
        for (Course course : student.getCourses()){
            if(course.getId().equals(courseId)){
                return course;
            }
        }
        return null;
    }

    @Override
    public Course addCourse(String studentId, Course newCourse) {
        Student student = retrieveStudentById(studentId);
        if(student == null){ return  null;}
        student.getCourses().add(newCourse);
        return newCourse;
    }
}
