package com.team.deskposable.repository;

import com.team.deskposable.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by a637191 on 28/08/2017.
 */
public class UserRepository {

    @Autowired
    EntityManager entityManager;
    public User findByEmail(String email) {
        TypedQuery<User> query;
        query = entityManager.createQuery("SELECT u FROM user u WHERE email = '"+email+"'",User.class);
        List results = query.getResultList();
        if(!results.isEmpty()){
            return (User) results.get(0);
        }
        return null;
    }

}
