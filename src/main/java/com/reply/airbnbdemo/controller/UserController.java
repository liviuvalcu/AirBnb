package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.bean.UserBean;
import com.reply.airbnbdemo.dto.UserDto;
import com.reply.airbnbdemo.enums.UserType;
import com.reply.airbnbdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("byEmail")
    public ResponseEntity<UserDto> getAllUsersByEmail(@RequestParam(value = "email") String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("byNameAndSurname")
    public ResponseEntity<UserDto> getUserByNameAndSurname(@RequestParam(value = "firstName") String firstName,
                                                            @RequestParam(value = "lastName") String lastName){
        return ResponseEntity.ok(userService.findByFnameAndLName(firstName, lastName));
    }



    @DeleteMapping("byId")
    public ResponseEntity<String> deleteUserById(@RequestParam(value = "id") Integer id){
        userService.deleteUserById(id);

        return new ResponseEntity<>("CREATED", HttpStatus.ACCEPTED);
    }

    @PostMapping("user")
    public ResponseEntity<Object> insertUser(UserBean user){
        userService.insertUser(user);
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<Object> updateUser(UserBean user){
        userService.insertUser(user);
        return new ResponseEntity<>("UPDATED", HttpStatus.OK);
    }

    @PostMapping("addType")
    public ResponseEntity<Object> setUserType(@RequestParam("userType")UserType userType, @RequestParam("id") Integer userId){
        userService.setUserType(userType, userId);
        return ResponseEntity.ok("CREATED");
    }

}
