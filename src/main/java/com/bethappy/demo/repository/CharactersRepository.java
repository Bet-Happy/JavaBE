package com.bethappy.demo.repository;

import com.bethappy.demo.model.Characters;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CharactersRepository extends CrudRepository<Characters, Long>{
    @Override
    List<Characters> findAll();
    Optional<Characters> findById(Long id);
}
