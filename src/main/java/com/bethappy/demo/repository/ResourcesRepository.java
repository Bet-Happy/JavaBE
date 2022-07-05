package com.bethappy.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.bethappy.demo.model.Resource;

public interface ResourcesRepository extends CrudRepository<Resource, Long>{

}