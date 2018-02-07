package com.poc.austin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.austin.domain.Product;

/**
 * @author Somnath
 *
 */

@Repository
/*
 * CrudRepository provides sophisticated CRUD functionality for the entity class that is being managed.
 * 
 * a) Saves the given entity.
 * 
 * b) Returns the entity identified by the given id.
 * 
 * c) Returns all entities.
 * 
 * d) Returns the number of entities.
 * 
 * e) Deletes the given entity.
 * 
 * f) Indicates whether an entity with the given id exists. 
  The implementation of ProductRepository class all methods will be created by  Spring at runtime automatically.
 * */
 
public interface ProductRepository extends CrudRepository<Product, Integer> {

	// Use of @Query annotation to write query custom queries.
	/**
	 * This method is using common queries to fetch product with promotion
	 * @param productname
	 * @param promotion
	 * @return List<@Product> 
	 */
	@Query("SELECT a FROM Product a WHERE a.productname=:productname and a.promotion=:promotion")
	List<Product> fetchProduct(@Param("productname") String productname,
			@Param("promotion") int promotion);
}