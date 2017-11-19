package com.ml.spring.tuto.authal.persistence.repositoris;

import com.ml.spring.tuto.authal.persistence.model.User;
import com.ml.spring.tuto.authal.persistence.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    UserLocation findByUserAndCountry(User user, String country);
}
