package com.hasan.cplcrypt.services;

import com.hasan.cplcrypt.models.User;
import com.hasan.cplcrypt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }


    public User getUserByUserName(String email){
        return userRepository.getUserByUsername(email);
    }
}
