package com.appollo.actions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.appollo.data_pojo.TestEntity;
import com.appollo.data_repositories.TestEntityRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
//import org.springframework.data.repository.*;


@Service
public class TestEntityActions 
{

	
	
	@Autowired
	TestEntityRepository entityRepository;
	
	public TestEntity saveTestEntity(String fname, String lname, int year) 
	{
		TestEntity savedEntity = this.entityRepository.save(new TestEntity(fname, lname, year));
		return savedEntity;
	}

	
	public List<TestEntity> getAllTestEntities() 
	{
		Iterable<TestEntity> entities = this.entityRepository.findAll();
		List<TestEntity> entitiesList = new ArrayList<>();
		entities.forEach(entitiesList::add);		
		return entitiesList;
	}

	
	public List<TestEntity> findByLname(String lname) {
		List<TestEntity> entities = this.entityRepository.findByLname(lname);
		return entities;
	}
	public List<TestEntity> findByYearGreaterThan(int year) {
		List<TestEntity> entities = this.entityRepository.findByYearGreaterThan(year);
		return entities;
	}
	public List<TestEntity> queryByYearGreaterThan(int year) {
		List<TestEntity> entities = this.entityRepository.queryTestEntitiesAfterYear(year);
		return entities;
	}
	
	public List<TestEntity> findByAuthorYear(String lname, int year) {
		List<TestEntity> entities = this.entityRepository.findByLnameAndYear(lname, year);
		return entities;
	}

	public TestEntity createTestEntities() {
		TestEntity savedEntity = this.entityRepository.save(new TestEntity("Eric", "Viking", 1999));
		return savedEntity;  
    }
	public void removeAll() {
		this.entityRepository.deleteAll();
	}
	
	public Slice<TestEntity> findTestEntityBylname(String lname, Pageable pageable)
	{
		Slice<TestEntity> s = this.entityRepository.findBylname(lname, pageable);
		
		return s;
	}
	public Slice<TestEntity> getTestEntitiesByPage(Pageable pageable)
	{
		
		
		Slice<TestEntity> s = this.entityRepository.findBy(pageable);
		
		return s;
	}


	public TestEntity save(TestEntity t) {
		TestEntity savedEntity = this.entityRepository.save(t);
		return savedEntity;
	}
	
}
