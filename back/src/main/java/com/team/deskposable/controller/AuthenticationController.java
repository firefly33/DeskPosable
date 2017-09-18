package com.team.deskposable.controller;

import com.team.deskposable.entity.Session;
import com.team.deskposable.entity.User;
import com.team.deskposable.repository.SessionRepository;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping()
    public Session authentication(@RequestBody User user, HttpServletResponse response) {
        String mailParam = user.getEmail();
        String passwordParam = user.getPassword();
        if(mailParam == null || passwordParam == null || mailParam.equals("") ||  passwordParam.equals("")){
            response.setStatus(401);
            return null;
        }
        try {
            user = userRepository.findByEmail(mailParam);
            if(!user.getPassword().equals(passwordParam)) {
                response.setStatus(401);
                return null;
            }
            Session s = new Session(user);
            sessionRepository.save(s);
            return s;
        }
        catch(Exception e){
            response.setStatus(500);
            return null;
        }
    }

    public String createConnection(){

        // Build Session token
        return "tokenName";
    }
}
