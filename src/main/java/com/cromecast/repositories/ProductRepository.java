package com.cromecast.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cromecast.domain.Product;

@Repository

/* 
 CrudRepository provides sophisticated CRUD functionality for the entity class that is being managed.
 @ Saves the given entity.
 @ Returns the entity identified by the given id.
 @Returns all entities.
 @Returns the number of entities.
 @Deletes the given entity.
 @Indicates whether an entity with the given id exists.
*/

//The implementation of ProductRepository class all methods will be created by Spring at runtime automatically.
public interface ProductRepository extends CrudRepository<Product, Integer>{
	
	
	// Use of @Query annotation to write query custom queries. 
	@Query("SELECT a FROM Product a WHERE a.productname=:productname and a.promotion=:promotion")
    List<Product> fetchProduct(@Param("productname") String productname, @Param("promotion") int promotion);
}