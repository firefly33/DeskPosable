package com.team.deskposable.controller;

import com.team.deskposable.entity.User;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    @RequestMapping(path="/connect", method=RequestMethod.POST)
    public String authentication(WebRequest request) {
        String mailParam = request.getParameter("mail");
        String passwordParam = request.getParameter("password");
        if(mailParam.equals("") ||  passwordParam.equals("")){
            return "error";
        }
        User user;
        try {
            user = userRepository.findByEmail(mailParam);
            if(!user.getPassword().equals(passwordParam)) {
                return "error";
            }
            return createConnection();
        }
        catch(Exception e){
            return e.getMessage();
        }
    }

    public String createConnection(){
        return "TOOOOOOOOOOKKKKKKKKENNNNNNNNNNNNNNNNNNN";
    }
}
