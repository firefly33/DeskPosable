package com.team.deskposable.controller;

import com.team.deskposable.entity.User;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
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
            u.setLastName(user.getLastName());
            u.setFirstName(user.getFirstName());
            u.setBirthday(user.getBirthday());
            userRepository.save(u);
        }

        return u;
    }
}
