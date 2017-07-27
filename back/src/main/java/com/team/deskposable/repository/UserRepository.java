package com.team.deskposable.repository;

import com.team.deskposable.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

}
