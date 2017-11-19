package com.ml.spring.tuto.authal.persistence.model;

import javax.persistence.*;

@Entity
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String country;

    private boolean enabled;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public UserLocation() {
        super();
        enabled = false;
    }

    public UserLocation(String country, User user) {
        this.country = country;
        this.user = user;
        this.enabled = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", enabled=" + enabled +
                ", user=" + user +
                '}';
    }
}
