package com.bethappy.demo.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bethappy.demo.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByUsername(String username);
  Boolean existsByUsername(String username);
  List<User> findAll();  
}
