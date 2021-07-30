package com.cashonline.controller;

import com.cashonline.dto.UserDTO;
import com.cashonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController
@RequestMapping("/users")
public class UserController {

    private Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id){
        logger.info("-------------------SERVICE------------------------");
        logger.info("Call service >>getUser<< with param id user " + id);

        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
        logger.info("-------------------SERVICE------------------------");
        logger.info("Call service >>createUser<< ");
        userService.addUser(userDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id){
        logger.info("-------------------SERVICE------------------------");
        logger.info("Call service >>deleteUser<< with param id user " + id);
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
