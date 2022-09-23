package com.appollo.data_repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import com.appollo.data_pojo.User;
import com.appollo.data_pojo.UserMedication;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;


public interface UserRepository extends DatastoreRepository<User, Long> {
	
	List<User> findByLname(String lname);
	
	List<User> findByAgeGreaterThan(int age);

	List<User> findByLnameAndAge(String lname, int age);

	Page<User> findBylname(String lname, Pageable pageable);
	
	//Slice<TestEntity> getPage(Pageable pageable);
	Page<User> findBy(Pageable page);
	
	@Query("SELECT * FROM User WHERE Email = @email")
	List<User> findByEmail(String email);

	@Query("SELECT * FROM User WHERE Medicare_Number = @medicareNumber")
	Page<User> queryByMedicareNumber(String medicareNumber, Pageable pageable);
	
	
}