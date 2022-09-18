package com.appollo.api_controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
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

import com.appollo.actions.UserMedicationActions;
import com.appollo.data_pojo.TestEntity;
import com.appollo.data_pojo.UserMedication;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 *  getUserMedsByUserID - all medications for a given user
 *  getPageOfUserMedsByUserID - retrieves a page of usermedications
 *  getUserMedsByPrescriptionID - all medications on a given prescription/medication script *  
 *  getUserMedsDoseTakenBeforeDateByUserID - all medications for a given user ID where dose taken <= given date ( can be used to determine if dose has been taken with date value of 0
 *  getPageUserMedsByUserIDDoseTimeBetweenDates  - all medications for a given user where dose time on a given date.
 * 
 *  createUserMedication - create a new user medication
 *  updateUserMedication - find UserMedByID param and update dose_taken field of found UserMedication object and then call UserMedicationRepository save
 * 
 * 
 */


@RestController
public class UserMedicationController { 


	@Autowired
	UserMedicationActions actions;
	
			@RequestMapping(
					value="/createUserMedication", 
					method=RequestMethod.POST,
					produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
					headers = "Accept=application/json")
			public ResponseEntity<UserMedication> createUserMedication(@RequestBody UserMedication um)
			{
				try {
					return new ResponseEntity<UserMedication>(actions.createUserMedication(um), HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<UserMedication>(HttpStatus.BAD_REQUEST);
				}
				
			}	
			
			@RequestMapping(
					value="/updateUserMedication", 
					method=RequestMethod.POST,
					produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
					headers = "Accept=application/json")
			public ResponseEntity<UserMedication> updateUserMedication(@RequestBody UserMedication um)
			{
				try {
					return new ResponseEntity<UserMedication>(actions.updateUserMedication(um), HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<UserMedication>(HttpStatus.BAD_REQUEST);
				}
				
			}	
			
			 @RequestMapping(
						value = "/getUserMedsByPrescriptionID",
						method = RequestMethod.GET,
						produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
						headers = "Accept=application/json"
					)
					public ResponseEntity<Iterable<UserMedication>> getUserMedsByPrescriptionID(@RequestParam("prescription_id") long prescription_id) {
					try {
							//System.out.println("Recieved: page = " + page + " userid="+userid);
							
						return new ResponseEntity<Iterable<UserMedication>>(actions.getUserMedsByPrescriptionID(prescription_id), HttpStatus.OK);
					} catch (Exception e) {
							
						e.printStackTrace();
						return new ResponseEntity<Iterable<UserMedication>>(HttpStatus.BAD_REQUEST);
					}

			 }
		
			 @RequestMapping(
						value = "/getUserMedsByUserID",
						method = RequestMethod.GET,
						produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
						headers = "Accept=application/json"
					)
					public ResponseEntity<Iterable<UserMedication>> getUserMedsByUserID(@RequestParam("userid") long userid) {
					try {
							//System.out.println("Recieved: page = " + page + " userid="+userid);
							
						return new ResponseEntity<Iterable<UserMedication>>(actions.getUserMedsByUserID(userid), HttpStatus.OK);
					} catch (Exception e) {
							
						e.printStackTrace();
						return new ResponseEntity<Iterable<UserMedication>>(HttpStatus.BAD_REQUEST);
					}

			 }
	
			 @RequestMapping(
					 value = "/getPageOfUserMedsByUserID",
					 method = RequestMethod.GET,
					 produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
					 headers = "Accept=application/json"
				)
			 	public ResponseEntity<Page<UserMedication>> getPageOfUserMedsByUserID(@RequestParam("page") int page,@RequestParam("userid") long userid) {
				try {
					System.out.println("Recieved: page = " + page + " userid="+userid);
					PageRequest p = PageRequest.of(page, 20);
					return new ResponseEntity<Page<UserMedication>>(actions.getPageOfUserMedsByUserID(userid,p), HttpStatus.OK);
				} catch (Exception e) {
					
					e.printStackTrace();
					return new ResponseEntity<Page<UserMedication>>(HttpStatus.BAD_REQUEST);
				}

			 }
			 
			 
			 
			 
			 
			 
			 @RequestMapping(
					 value = "/getPageUserMedsByUserIDDoseTimeBetweenDates",
					 method = RequestMethod.GET,
					 produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
					 headers = "Accept=application/json"
				)
			 	public ResponseEntity<Slice<UserMedication>> getPageUserMedsByUserIDDoseTimeBetweenDates(@RequestParam("page") int page,@RequestParam("userid") long userid,@RequestParam("startdate") long startdate,@RequestParam("enddate") long enddate) {
				try {
					
					PageRequest p = PageRequest.of(page, 20);
					return new ResponseEntity<Slice<UserMedication>>(actions.getPageUserMedsByUserIDDoseTimeBetweenDates(userid,startdate,enddate,p), HttpStatus.OK);
				} catch (Exception e) {
					
					e.printStackTrace();
					return new ResponseEntity<Slice<UserMedication>>(HttpStatus.BAD_REQUEST);
				}

			 }
			 
			 
			 
			 
			 
			 @RequestMapping(
						value = "/getUserMedsDoseTakenBeforeDateByUserID",
						method = RequestMethod.GET,
						produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
						headers = "Accept=application/json"
					)
					public ResponseEntity<Iterable<UserMedication>> getUserMedsByUserID(@RequestParam("userid") long userid, @RequestParam("date") long date) {
					try {
							//System.out.println("Recieved: page = " + page + " userid="+userid);
							
						return new ResponseEntity<Iterable<UserMedication>>(actions.queryUserMedicationsDoseTakenBeforeDate(userid, date), HttpStatus.OK);
					} catch (Exception e) {
							
						e.printStackTrace();
						return new ResponseEntity<Iterable<UserMedication>>(HttpStatus.BAD_REQUEST);
					}

			 }
}