package com.ml.spring.tuto.authal.persistence.repositoris;

import com.ml.spring.tuto.authal.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);
}
