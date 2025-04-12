//package com.vitamindz.ims.backend.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.vitamindz.ims.backend.entity.Course;
//import com.vitamindz.ims.backend.entity.OurUsers;
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class ReqRes {
//    private int statusCode;
//    private String error;
//    private String message;
//    private String token;
//    private String refreshToken;
//    private String expirationTime;
//    private String name;
//    private String city;
//    private String role;
//    private String email;
//    private String password;
//    private OurUsers ourUsers;
//    private List<OurUsers> ourUsersList;
//    private List<Course> courseList;
//
//}
