package com.team.deskposable.controller;

import com.team.deskposable.entity.Person;
import com.team.deskposable.repository.PersonRepository;
import com.team.deskposable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by alec_ on 28/07/2017.
 */

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping
    @ResponseBody
    public String addNewPerson(@RequestBody Person person) {
        if(person != null){
            personRepository.save(person);
            return "ok";
        }
        return "errors";
    }

    @GetMapping()
    public Iterable<Person> getAllPerson() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Person getPerson (@PathVariable Long id) {
        return personRepository.findOne(id);
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable Long id) {
        personRepository.delete(id);
        return "ok";
    }

    @PutMapping("/{id}")
    public String modifyPerson(@PathVariable Long id, @RequestBody Person person) {
        Person modifyPerson = personRepository.findOne(id);
        modifyPerson.setFirstname(person.getFirstname());
        modifyPerson.setLastname(person.getLastname());
        modifyPerson.setEmployment(person.getEmployment());
        modifyPerson.setDesk(person.getDesk());
        personRepository.save(modifyPerson);
        return "ok";
    }


}



