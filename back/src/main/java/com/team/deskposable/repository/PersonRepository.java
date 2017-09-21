package com.team.deskposable.repository;

import com.team.deskposable.entity.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alec_ on 28/07/2017.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findOneByDesk_Id(Long id);
}
