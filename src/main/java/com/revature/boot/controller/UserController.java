package com.revature.boot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.boot.entities.User;
import com.revature.boot.entities.UsernamePasswordAuthentication;
import com.revature.boot.exceptions.EntityNotFound;
import com.revature.boot.service.UserService;

@RestController
public class UserController {

    private static Logger userLogger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e){
        userLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        userLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UsernamePasswordAuthentication loginRequest, HttpServletRequest request){
        User u = userService.getUserByUsername(loginRequest.getUsername());
        if(u.getUsername() != null && u.getPassword().equals(loginRequest.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user", u);
            return new ResponseEntity<>("logged in successfully", HttpStatus.OK);
        }else{
            throw new EntityNotFound("Username or Password is incorrect");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsernamePasswordAuthentication regsterRequest){
        return new ResponseEntity<>(this.userService.createUser(regsterRequest), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> invalidateSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new ResponseEntity<>("logged out", HttpStatus.OK);
    }
}
