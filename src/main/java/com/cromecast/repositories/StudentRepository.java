package com.cromecast.repositories;

import org.springframework.data.repository.CrudRepository;
import com.cromecast.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Integer>{
}