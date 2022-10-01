package com.appollo.data_repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

import com.appollo.data_pojo.User;
import com.appollo.data_pojo.UserMedication;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;

/*
 * interface class used by Spring framework to build database access
 */

public interface UserRepository extends DatastoreRepository<User, Long> {
	
	//List<User> findByLname(String lname);
	
	List<User> findByAgeGreaterThan(int age);

	List<User> findByLnameAndAge(String lname, int age);

	@Query("SELECT * FROM User WHERE Last_name = @lastName AND @requester IN serviceSubscription")
	Page<User> queryUsersByLName(String lastName,String requester, Pageable pageable);
	
	@Query("SELECT * FROM User WHERE @requester IN serviceSubscription")
	Page<User> queryUsersByPage(String requester, Pageable pageable);
	
	@Query("SELECT * FROM User WHERE Email = @email")
	List<User> findByEmail(String email);
	
	@Query("SELECT * FROM User WHERE Email = @email AND @requester IN serviceSubscription")
	Optional<User> queryByEmail(String email,String requester);

	@Query("SELECT * FROM User WHERE Medicare_Number = @medicareNumber AND @requester IN serviceSubscription")
	Page<User> queryByMedicareNumber(String medicareNumber,String requester, Pageable pageable);
	
	@Query("SELECT * FROM User WHERE Medicare_Number >= @medicareNumber AND @requester IN serviceSubscription")
	Page<User> queryByMedicareNumberEqualorGreaterThan(String medicareNumber,String requester, Pageable pageable);
	
	//boolean existsById(long Id);
	
	@Query("SELECT * FROM User WHERE Medicare_Number = @medicareNumber")
	List<User> findByMedicareNumber(String medicareNumber);
	
	@Query("SELECT * FROM User WHERE Medicare_Number = @medicareNumber")
	User queryOneByMedicareNumber(String medicareNumber);
	
	//Optional<User> findById(@Param("id") Long id);

	
	
}



