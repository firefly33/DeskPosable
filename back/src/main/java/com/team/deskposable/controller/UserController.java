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
    public Iterable<User> users(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User user(@PathVariable long id){
        return userRepository.findOne(id);
    }

    @PostMapping()
    @ResponseBody
    public User newAccount(@RequestBody User user) {
            userRepository.save(user);
            return user;
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable long id) {
        User userToDelete = userRepository.findOne(id);

        userRepository.delete(id);
        return userToDelete;
    }

    @PutMapping("/{id}")
    public User modifyUser(@PathVariable long id,@RequestBody User user) {
        User userToEdit = userRepository.findOne(id);

        if (userToEdit != null) {
            userToEdit.setLastName(user.getLastName());
            userToEdit.setFirstName(user.getFirstName());
            userToEdit.setBirthday(user.getBirthday());
            userRepository.save(userToEdit);
        }

        return userToEdit;
    }
}
