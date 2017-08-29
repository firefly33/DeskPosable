package com.team.deskposable.controller;

import com.team.deskposable.entity.Desk;
import com.team.deskposable.entity.Person;
import com.team.deskposable.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Person p = personRepository.findOne(id);

        personRepository.delete(id);
        return p;
    }

    @PutMapping("/{id}")
    public Person updatePerson (@PathVariable Long id, @RequestBody Person person) {
        Person p = personRepository.findOne(id);
        if (person.getFirstname() != null) {
            p.setFirstname(person.getFirstname());
        }
        if (person.getLastname() != null) {
            p.setLastname(person.getLastname());
        }
        if (person.getEmployment() != null) {
            p.setEmployment(person.getEmployment());
        }
        if (person.getDesk() != null) {
            p.setDesk(person.getDesk());
        }
        personRepository.save(p);
        return p;
    }


    @PutMapping("/{id}/affectDesk")
    public Person affectDesk (@PathVariable Long id, @RequestBody Desk desk) {
        Person personToEdit = personRepository.findOne(id);

        if (personToEdit != null) {
            personToEdit.setDesk(desk);
            personRepository.save(personToEdit);
        }

        return personToEdit;
    }
}



