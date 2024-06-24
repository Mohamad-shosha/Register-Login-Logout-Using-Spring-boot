package com.code.User_Management_App.Service;

import com.code.User_Management_App.model.UserDtls;

public interface UserService {
    public UserDtls creatUser(UserDtls userDtls) ;
    public boolean checkEmail(String email);
}
