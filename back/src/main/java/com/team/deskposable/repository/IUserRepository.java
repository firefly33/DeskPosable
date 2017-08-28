package com.team.deskposable.repository;

import com.team.deskposable.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface IUserRepository extends CrudRepository<User, Long> {

}
