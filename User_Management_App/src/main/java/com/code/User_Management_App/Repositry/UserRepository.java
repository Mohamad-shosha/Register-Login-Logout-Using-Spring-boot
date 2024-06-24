package com.code.User_Management_App.Repositry;
import com.code.User_Management_App.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls,Integer> {
    // no code required (Read , Add , Update and Delete) .....
    public boolean existsByEmail(String email);
}
