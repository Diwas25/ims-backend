package com.vitamindz.ims.backend.service;

import com.vitamindz.ims.backend.dto.ResultObject;
import com.vitamindz.ims.backend.entity.Course;
//import com.vitamindz.ims.backend.repository.CourseRepository;
import com.vitamindz.ims.backend.repositroy.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public ResultObject addCourse(Course course) {
        ResultObject result = new ResultObject();
        try {
            Course saved = courseRepository.save(course);
            result.setResponseCode(200);
            result.setResponseMessage("Course added successfully");
            result.setResponseObject(saved);
        } catch (Exception e) {
            result.setResponseCode(500);
            result.setResponseMessage("Error adding course: " + e.getMessage());
        }
        return result;
    }

    public ResultObject getAllCourses() {
        ResultObject result = new ResultObject();
        try {
            List<Course> courses = courseRepository.findAll();
            result.setResponseCode(200);
            result.setResponseMessage("Courses fetched successfully");
            result.setResponseObject(courses);
        } catch (Exception e) {
            result.setResponseCode(500);
            result.setResponseMessage("Error fetching courses: " + e.getMessage());
        }
        return result;
    }

    public ResultObject getCourseById(Long id) {
        ResultObject result = new ResultObject();
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                result.setResponseCode(200);
                result.setResponseMessage("Course found");
                result.setResponseObject(course.get());
            } else {
                result.setResponseCode(404);
                result.setResponseMessage("Course not found");
            }
        } catch (Exception e) {
            result.setResponseCode(500);
            result.setResponseMessage("Error fetching course: " + e.getMessage());
        }
        return result;
    }

    public ResultObject updateCourse(Long id, Course courseDetails) {
        ResultObject result = new ResultObject();
        try {
            Optional<Course> courseOptional = courseRepository.findById(id);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                course.setCourseName(courseDetails.getCourseName());
                course.setFee(courseDetails.getFee());
                course.setDuration(courseDetails.getDuration());
                course.setBatch(courseDetails.getBatch());
                course.setStatus(courseDetails.getStatus());

                Course updated = courseRepository.save(course);
                result.setResponseCode(200);
                result.setResponseMessage("Course updated successfully");
                result.setResponseObject(updated);
            } else {
                result.setResponseCode(404);
                result.setResponseMessage("Course not found");
            }
        } catch (Exception e) {
            result.setResponseCode(500);
            result.setResponseMessage("Error updating course: " + e.getMessage());
        }
        return result;
    }

    public ResultObject deleteCourse(Long id) {
        ResultObject result = new ResultObject();
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                courseRepository.deleteById(id);
                result.setResponseCode(200);
                result.setResponseMessage("Course deleted successfully");
            } else {
                result.setResponseCode(404);
                result.setResponseMessage("Course not found");
            }
        } catch (Exception e) {
            result.setResponseCode(500);
            result.setResponseMessage("Error deleting course: " + e.getMessage());
        }
        return result;
    }
}
