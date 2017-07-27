package com.team.DeskPosable.Controller;

import com.team.DeskPosable.Entity.User;
import com.team.DeskPosable.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    public @ResponseBody
    String addNewAccount(@RequestBody User user) {

        if(user != null){
            userRepository.save(user);
            return "ok";
        }
        return "errors";
    }

    @GetMapping("/all")
    public ArrayList<User> getAllUsers(){
        return (ArrayList<User>)userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id){
        return userRepository.findOne(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id){
        userRepository.delete(id);
        return "ok";
    }

    @PutMapping("/{id}")
    public String modifyUser(@PathVariable long id,@RequestBody User user){
        User modifyUser = userRepository.findOne(id);
        modifyUser.setLastName(user.getLastName());
        modifyUser.setFirstName(user.getFirstName());
        modifyUser.setBirthday(user.getBirthday());
        userRepository.save(modifyUser);
        return "ok";
    }
}
