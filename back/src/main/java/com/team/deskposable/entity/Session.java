package com.team.deskposable.entity;

import com.team.deskposable.entity.User;
import javax.management.timer.Timer;
import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.team.deskposable.repository.UserRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * Created by fx on 30/08/17.
 */
@Entity
public class Session {

    @Value("${tokenPath}")
    private String tokenPath;

    @Id
    @Length(max = 36)
    private String token;

    @ManyToOne
    private User user;
    private Date expirationDate;

    public Session() {
        // NOTE : for bean convention
    }

    public Session(User user) {
        this.user = user;
        this.expirationDate = new Date(new Date().getTime() + 24L * Timer.ONE_HOUR);

        String tokenName = UUID.randomUUID().toString();
        /*
        try {
            File token = new File(tokenPath + "/" + tokenName);
            if (token.createNewFile()) {
                System.out.println("New session token : " + tokenName);
            } else {
                System.out.println("Session token writing error.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        this.token = tokenName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }
}
