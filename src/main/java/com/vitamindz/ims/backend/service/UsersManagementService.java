package com.vitamindz.ims.backend.service;

import com.vitamindz.ims.backend.dto.ResultObject;
import com.vitamindz.ims.backend.entity.OurUsers;
import com.vitamindz.ims.backend.repositroy.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResultObject register(OurUsers registrationRequest) {
        ResultObject resp = new ResultObject();
        try {
            registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            OurUsers ourUsersResult = usersRepo.save(registrationRequest);
            if (ourUsersResult.getId() > 0) {
                resp.setResponseObject(ourUsersResult);
                resp.setmessageandcode(200, "User Saved Successfully");
            }
        } catch (Exception e) {
            resp.setmessageandcode(500, e.getMessage());
        }
        return resp;
    }

    public ResultObject login(OurUsers loginRequest) {
        ResultObject response = new ResultObject();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            HashMap<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("token", jwt);
            tokenMap.put("refreshToken", refreshToken);
            tokenMap.put("expirationTime", "24Hrs");
            tokenMap.put("role", user.getRole());
            response.setResponseObject(tokenMap);
            response.setmessageandcode(200, "Successfully Logged In");
        } catch (Exception e) {
            response.setmessageandcode(500, e.getMessage());
        }
        return response;
    }

    public ResultObject refreshToken(String refreshTokenRequest) {
        ResultObject response = new ResultObject();
        try {
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest);
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest, users)) {
                var jwt = jwtUtils.generateToken(users);
                HashMap<String, Object> tokenMap = new HashMap<>();
                tokenMap.put("token", jwt);
                tokenMap.put("refreshToken", refreshTokenRequest);
                tokenMap.put("expirationTime", "24Hr");
                response.setResponseObject(tokenMap);
                response.setmessageandcode(200, "Successfully Refreshed Token");
            }
        } catch (Exception e) {
            response.setmessageandcode(500, e.getMessage());
        }
        return response;
    }

    public ResultObject getAllUsers() {
        ResultObject resp = new ResultObject();
        try {
            List<OurUsers> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                resp.setResponseObject(result);
                resp.setmessageandcode(200, "Successful");
            } else {
                resp.setmessageandcode(404, "No users found");
            }
        } catch (Exception e) {
            resp.setmessageandcode(500, "Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ResultObject getUsersById(Integer id) {
        ResultObject resp = new ResultObject();
        try {
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            resp.setResponseObject(usersById);
            resp.setmessageandcode(200, "Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            resp.setmessageandcode(500, "Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ResultObject deleteUser(Integer userId) {
        ResultObject resp = new ResultObject();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                resp.setmessageandcode(200, "User deleted successfully");
            } else {
                resp.setmessageandcode(404, "User not found for deletion");
            }
        } catch (Exception e) {
            resp.setmessageandcode(500, "Error occurred while deleting user: " + e.getMessage());
        }
        return resp;
    }

    public ResultObject updateUser(Integer userId, OurUsers updatedUser) {
        ResultObject resp = new ResultObject();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUsers savedUser = usersRepo.save(existingUser);
                resp.setResponseObject(savedUser);
                resp.setmessageandcode(200, "User updated successfully");
            } else {
                resp.setmessageandcode(404, "User not found for update");
            }
        } catch (Exception e) {
            resp.setmessageandcode(500, "Error occurred while updating user: " + e.getMessage());
        }
        return resp;
    }

    public ResultObject getMyInfo(String email) {
        ResultObject resp = new ResultObject();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                resp.setResponseObject(userOptional.get());
                resp.setmessageandcode(200, "Successful");
            } else {
                resp.setmessageandcode(404, "User not found for update");
            }
        } catch (Exception e) {
            resp.setmessageandcode(500, "Error occurred while getting user info: " + e.getMessage());
        }
        return resp;
    }
}