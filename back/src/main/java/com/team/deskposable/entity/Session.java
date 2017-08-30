package com.team.deskposable.entity;

import javax.management.timer.Timer;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
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
    //@Transient
    private User user;
    private Date expirationDate;

    public Session() {
        // NOTE : for bean convention
    }

    public Session(User user) {
        String tokenName = UUID.randomUUID().toString();
        this.token = tokenName;
        this.user = user;
        this.expirationDate = new Date(new Date().getTime() + 24L * Timer.ONE_HOUR);
    }

    @JsonProperty("user")
    public Long getUser() {
        return user.getId();
    }

    /*
    public User getUser() {
        return user;
    }
    */

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

    protected boolean isValid() {
        return this.expirationDate.after(new Date());
    }
}
