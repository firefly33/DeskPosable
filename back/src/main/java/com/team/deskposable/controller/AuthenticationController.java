package com.team.deskposable.controller;

import com.team.deskposable.entity.Session;
import com.team.deskposable.entity.User;
import com.team.deskposable.repository.SessionRepository;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Value("${tokenPath}")
    private String tokenPath;

    @PostMapping()
    public Session authentication(WebRequest request, HttpServletResponse response) {
        String mailParam = request.getParameter("mail");
        String passwordParam = request.getParameter("password");
        if(mailParam == null || passwordParam == null || mailParam.equals("") ||  passwordParam.equals("")){
            response.setStatus(401);
            return null;
        }
        User user;
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
