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

    @GetMapping()
    public Iterable<Person> readAllPersons () {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Person readPerson (@PathVariable Long id) {
        return personRepository.findOne(id);
    }

    @PostMapping()
    @ResponseBody
    public Person createPerson (@RequestBody Person person) {
        if(person != null){
            personRepository.save(person);
            return person;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Person deletePerson (@PathVariable Long id) {
        Person personToDelete = personRepository.findOne(id);

        personRepository.delete(id);
        return personToDelete;
    }

    @PutMapping("/{id}")
    public Person updatePerson (@PathVariable Long id, @RequestBody Person person) {
        Person personToUpdate = personRepository.findOne(id);
        personToUpdate.setFirstname(person.getFirstname());
        personToUpdate.setLastname(person.getLastname());
        personToUpdate.setEmployment(person.getEmployment());
        personToUpdate.setDesk(person.getDesk());
        personRepository.save(personToUpdate);
        return personToUpdate;
    }


}



