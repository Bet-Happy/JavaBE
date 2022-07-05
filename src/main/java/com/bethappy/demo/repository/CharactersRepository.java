package com.bethappy.demo.repository;

import com.bethappy.demo.model.Characters;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CharactersRepository extends CrudRepository<Characters, Long>{
    
}
