package com.ml.spring.tuto.aae.repository;

import com.ml.spring.tuto.aae.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{


}
