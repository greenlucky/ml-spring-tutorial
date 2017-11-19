package com.ml.spring.tuto.authal.persistence.model;

import javax.persistence.*;

@Entity
public class NewLocationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @ManyToOne(targetEntity = UserLocation.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_location_id")
    private UserLocation userLocation;

    public NewLocationToken() {
        super();
    }

    public NewLocationToken(final String token) {
        super();
        this.token = token;
    }

    public NewLocationToken(final String token, final UserLocation userLocation) {
        this.token = token;
        this.userLocation = userLocation;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public UserLocation getUserLocation() {
        return userLocation;
    }
}
