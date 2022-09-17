package com.appollo.api_controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appollo.actions.TestEntityActions;
import com.appollo.data_pojo.TestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class TestEntityController {

	//ObjectMapper jsonmapper = new ObjectMapper();
	
	@Autowired
	TestEntityActions actions;
		
	
	
	
	
	
			@RequestMapping(
					value="/createTestEntity", 
					method=RequestMethod.POST,
					produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
					headers = "Accept=application/json")
			public ResponseEntity<TestEntity> createTestEntity(@RequestBody TestEntity t)
			{
				try {
					return new ResponseEntity<TestEntity>(actions.save(t), HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<TestEntity>(HttpStatus.BAD_REQUEST);
				}
				
			}
	
			 @RequestMapping(
						value = "/getTestEntitiesByPage",
						method = RequestMethod.GET,
						produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
						headers = "Accept=application/json"
					)
					public ResponseEntity<Slice<TestEntity>> getPageOfTestEntitiesByLName(@RequestParam("page") int page) {
						try {
							PageRequest p = PageRequest.of(page, 2,Sort.Direction.ASC, "lname");
							
							return new ResponseEntity<Slice<TestEntity>>(actions.getTestEntitiesByPage(p), HttpStatus.OK);
						} catch (Exception e) {
							return new ResponseEntity<Slice<TestEntity>>(HttpStatus.BAD_REQUEST);
						}

			 }
	
	
	 @RequestMapping(
				value = "/getAllTestEntities",
				method = RequestMethod.GET,
				produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
				headers = "Accept=application/json"
			)
			public ResponseEntity<Iterable<TestEntity>> findAll() {
				try {
					return new ResponseEntity<Iterable<TestEntity>>(actions.getAllTestEntities(), HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<Iterable<TestEntity>>(HttpStatus.BAD_REQUEST);
				}

	 }
	 @RequestMapping(
				value = "/getPageOfTestEntitiesByLName",
				method = RequestMethod.GET,
				produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
				headers = "Accept=application/json"
			)
			public ResponseEntity<Slice<TestEntity>> getPageOfTestEntitiesByLName(@RequestParam("page") int page,@RequestParam("lname") String lname) {
				try {
					PageRequest p = PageRequest.of(page, 1);
					return new ResponseEntity<Slice<TestEntity>>(actions.findTestEntityBylname(lname,p), HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<Slice<TestEntity>>(HttpStatus.BAD_REQUEST);
				}

	 }
}