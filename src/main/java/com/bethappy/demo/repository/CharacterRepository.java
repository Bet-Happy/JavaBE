package com.bethappy.demo.repository;

import com.bethappy.demo.model.Character;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Character, Long>{
    
}
