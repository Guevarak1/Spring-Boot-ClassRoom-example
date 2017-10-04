package com.kevguev.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevguev.Controller.StudentController;
import com.kevguev.Entity.Course;
import com.kevguev.Entity.Student;
import com.kevguev.Service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    Course mockCourse = new Course("Course1", "Spring", "10 Steps",
            Arrays.asList("Learn Maven", "Import Project", "First Example",
                    "Second Example"));
    Course mockCourse2 = new Course("Course2", "Spring", "10 Steps",
            Arrays.asList("Learn Maven", "Import Project", "First Example",
                    "Second Example"));

    List<Course> mockCourses = new ArrayList<Course>();

    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

    HashMap<String, Student> mockStudents = new HashMap<>();

    @Test
    public void retrieveAllStudents() throws Exception{

        mockCourses.add(mockCourse);
        Student mockStudent = new Student("AAAAA", "Kevin", "CompSci",mockCourses);
        mockStudents.put(mockStudent.getId(), mockStudent);
        mockStudents.put(mockStudent.getId(), mockStudent);

        checkSuccessfulHTTPRequest("http://localhost:8080/students/");

        Mockito.when(studentService.retrieveAllStudents())
                .thenReturn(mockStudents);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        String expected =  "{\"AAAAA\": {\"id\": \"AAAAA\", \"name\": \"Kevin\",\"description\": \"CompSci\"," +
                "\"courses\":[{\"id\": \"Course1\",\"name\": \"Spring\",\"description\": \"10 Steps\",\"steps\": [\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}]}," +
                "\"AAAAA\": {\"id\": \"AAAAA\", \"name\": \"Kevin\",\"description\": \"CompSci\"," +
                "\"courses\":[{\"id\": \"Course1\",\"name\": \"Spring\",\"description\": \"10 Steps\",\"steps\": [\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}]}}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void retrieveDetailsForCourse() throws Exception{
        Mockito.when(studentService.retrieveCourse(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(mockCourse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/1/courses/Course1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void retrieveCoursesForStudent()throws Exception{
        mockCourses.add(mockCourse);
        mockCourses.add(mockCourse2);
        Mockito.when(
                studentService.retrieveCourses(Mockito.anyString()))
                .thenReturn(mockCourses);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/1/courses")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        String expected = "[{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}," +
                "{\"id\":\"Course2\",\"name\":\"Spring\",\"description\":\"10 Steps\"}]";

        JSONAssert.assertEquals(expected,result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void createStudentCourse() throws Exception {
        Course mockCourse = new Course("1", "Smallest Number", "1",
                Arrays.asList("1", "2", "3", "4"));

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(
                studentService.addCourse(Mockito.anyString(),
                        Mockito.any(Course.class))).thenReturn(mockCourse);

        // Send course as body to /students/1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/students/1/courses")
                .accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/students/1/courses/1",
                response.getHeader(HttpHeaders.LOCATION));

    }

    private void checkSuccessfulHTTPRequest(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .getForEntity(url, String.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
    }

    private void checkNotNullResponse(ResponseEntity<String> response, String nodeName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path(nodeName);
        assertThat(name.asText(), notNullValue());
    }
}
