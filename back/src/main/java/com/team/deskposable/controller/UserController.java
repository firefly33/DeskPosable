package com.team.deskposable.controller;

import com.team.deskposable.entity.User;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usersControllers")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public Iterable<User> readAllUsers (){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User readUser (@PathVariable long id){
        return userRepository.findOne(id);
    }

    @PostMapping()
    @ResponseBody
    public User createUser (@RequestBody User user) {
            userRepository.save(user);
            return user;
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable long id) {
        User u = userRepository.findOne(id);

        userRepository.delete(id);
        return u;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id,@RequestBody User user) {
        User u = userRepository.findOne(id);

        if (u != null) {
            if (user.getLastName() != null) {
                u.setLastName(user.getLastName());
            }
            if (user.getLastName() != null) {
                u.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                u.setBirthday(user.getBirthday());
            }
            if (user.getLastName() != null) {
                u.setEmail(user.getEmail());
            }
            if (user.getLastName() != null) {
                u.setPassword(user.getPassword());
            }
            userRepository.save(u);
        }

        return u;
    }
}
