package com.ml.spring.tuto.corej.persistence.repositories;

import com.ml.spring.tuto.corej.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
