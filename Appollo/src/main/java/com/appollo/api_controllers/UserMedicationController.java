package com.appollo.api_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.appollo.actions.UserMedicationActions;
import com.appollo.data_pojo.UserMedication;

/* Endpoints for CRUD operations on UserMedication records used by Team Eternals medication reminder application
 * For a list of expected request parameters and returned response parameters see:
 * https://deakin365.sharepoint.com/:t:/r/sites/GopherIndustries2/Shared%20Documents/Team%20Peacemaker/T2%202022/Handover/Code%20documentation/usermedication_json_request_response.txt?csf=1&web=1&e=uaXxmu
 * Note that the underlying entity class can be configured to allow alias names on request parameters 
 * 
 * createUserMedication
 * updateUserMedication
 * getUserMedsByPrescriptionID
 * getUserMedsByUserID
 * getPageOfUserMedsByUserID - returns a page of up to 20 user medications for a given user_ID
 * getPageUserMedsByUserIDDoseTimeBetweenDates - returns a page of up to 20 user medications for a given user_ID where dose_time between 2 dates. Use to view medications due on a given date range
 * getUserMedsDoseTakenBeforeDateByUserID - all medications for a given user ID where dose taken <= given date ( can be used to determine if dose has been taken with date value of 0. Assuming 0 is default value when dose_taken has no data.
 * 
 * Note:
 * Dates are expected as long values ( number of seconds since epoch)
 * Paged results return up to 20 records per request. The response includes fields to indicate current page, total number of pages and if more records exist. 
 * 
 */


@RestController
public class UserMedicationController { 

	private static final long serialVersionUID = 1L;

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
	public ResponseEntity<Page<UserMedication>> getPageUserMedsByUserIDDoseTimeBetweenDates(@RequestParam("page") int page,@RequestParam("userid") long userid,@RequestParam("startdate") long startdate,@RequestParam("enddate") long enddate) {
		try {

			PageRequest p = PageRequest.of(page, 20);
			return new ResponseEntity<Page<UserMedication>>(actions.getPageUserMedsByUserIDDoseTimeBetweenDates(userid,startdate,enddate,p), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<Page<UserMedication>>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(
			value = "/getUserMedsDoseTakenBeforeDateByUserID",
			method = RequestMethod.GET,
			produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json"
			)
	public ResponseEntity<Iterable<UserMedication>> getUserMedsDoseTakenBeforeDateByUserID(@RequestParam("userid") long userid, @RequestParam("date") long date) {
		try {			
			return new ResponseEntity<Iterable<UserMedication>>(actions.queryUserMedicationsDoseTakenBeforeDate(userid, date), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<Iterable<UserMedication>>(HttpStatus.BAD_REQUEST);
		}

	}
}