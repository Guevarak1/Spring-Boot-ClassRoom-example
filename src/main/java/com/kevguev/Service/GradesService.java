package com.kevguev.Service;

import com.kevguev.DAO.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradesService {
    @Autowired
    @Qualifier("fakedata")
    private StudentDao fakeStudentDaoImpl;

    public List<String> retrieveGrades(String studentId) {
        return this.fakeStudentDaoImpl.retrieveGrades(studentId);
    }

}
