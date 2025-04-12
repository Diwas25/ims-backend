package com.vitamindz.ims.backend.controller;

import com.vitamindz.ims.backend.dto.ResultObject;
import com.vitamindz.ims.backend.entity.Course;
import com.vitamindz.ims.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Add Course
    @PostMapping
    public ResponseEntity<ResultObject> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    // Get All Courses
    @GetMapping
    public ResponseEntity<ResultObject> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // Get Course by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResultObject> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // Edit Course
    @PutMapping("/{id}")
    public ResponseEntity<ResultObject> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    // Delete Course
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultObject> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }
}

