package com.ml.spring.tuto.authal.persistence.repositoris;

import com.ml.spring.tuto.authal.persistence.model.NewLocationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {
    
    NewLocationToken findByToken(String token);
}
