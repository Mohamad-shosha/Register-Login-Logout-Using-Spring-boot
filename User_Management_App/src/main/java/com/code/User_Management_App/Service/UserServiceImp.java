package com.code.User_Management_App.Service;

import com.code.User_Management_App.Repositry.UserRepository;
import com.code.User_Management_App.model.UserDtls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    private UserRepository userRepository;
    @Autowired
    public  UserServiceImp (UserRepository repositoryy){
        this.userRepository = repositoryy;
    }
    @Override
    public UserDtls creatUser(UserDtls userDtls) {
        return userRepository.save(userDtls);
    }

    @Override
    public boolean checkEmail(String email) {return userRepository.existsByEmail(email);}
}
