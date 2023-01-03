package com.revature.boot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.boot.entities.User;
import com.revature.boot.entities.UsernamePasswordAuthentication;
import com.revature.boot.repository.UserDao;

@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;

    public User getUserByUsername(String username){
        Optional<User> possibleUser = this.userDao.findByUsername(username);
        if(possibleUser.isPresent()){
            return possibleUser.get();
        }else{
            return new User();//make this throw
        }
    }

    public String createUser(UsernamePasswordAuthentication registerRequest){
        this.userDao.createUser(registerRequest.getUsername(), registerRequest.getPassword());
        return "User created";
    }
}
