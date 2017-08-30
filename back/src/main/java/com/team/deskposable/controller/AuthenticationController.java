package com.team.deskposable.controller;

import com.team.deskposable.entity.Session;
import com.team.deskposable.entity.User;
import com.team.deskposable.repository.SessionRepository;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Session authentication(WebRequest request, HttpServletResponse response) {
        String mailParam = request.getParameter("mail");
        String passwordParam = request.getParameter("password");
        if(mailParam == null || passwordParam == null || mailParam.equals("") ||  passwordParam.equals("")){
            //return "error";
            response.setStatus(444);
            return null;
        }
        User user;
        try {
            user = userRepository.findByEmail(mailParam);
            if(!user.getPassword().equals(passwordParam)) {
                //return "error";
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
            //return e.getMessage();
        }
    }

    public String createConnection(){

        // Build Session token
        return "tokenName";
    }
}
