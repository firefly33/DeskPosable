package com.team.deskposable.controller;

import com.team.deskposable.entity.User;
import com.team.deskposable.repository.IUserRepository;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    UserRepository userRepository;

    @PostMapping()
    @ResponseBody
    public User authentication(@RequestParam("mail") String mail, @RequestParam("password") String password ) {
        User user = userRepository.findByEmail(mail);
        if(user != null){
            if(user.getPassword() == password) {
                return user;
            }
        }
        return null;
    }
}
