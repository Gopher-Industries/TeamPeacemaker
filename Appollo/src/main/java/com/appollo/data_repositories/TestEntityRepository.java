package com.appollo.data_repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import com.appollo.data_pojo.TestEntity;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;



public interface TestEntityRepository extends DatastoreRepository<TestEntity, Long> {

	List<TestEntity> findByLname(String lname);

	List<TestEntity> findByYearGreaterThan(int year);

	List<TestEntity> findByLnameAndYear(String lname, int year);
	
	Slice<TestEntity> findBylname(String lname, Pageable pageable);
	
	//Slice<TestEntity> getPage(Pageable pageable);
	Slice<TestEntity> findBy(Pageable page);
	
	@Query("SELECT * FROM TestEntity WHERE year >= @year")
	  List<TestEntity> queryTestEntitiesAfterYear(@Param("year") int year);

	  @Query("SELECT * FROM TestEntity WHERE id = @id_val")
	  TestEntity queryTestEntityById(@Param("id_val") long id);  
	
}

