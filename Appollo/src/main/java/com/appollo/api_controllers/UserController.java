package com.appollo.api_controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.appollo.Views.UserView;
import com.appollo.actions.UserActions;
import com.appollo.data_pojo.User;




@RestController
public class UserController {

	private static final long serialVersionUID = 1L;



	@Autowired
	UserActions actions;

	@RequestMapping(
			value="/createUser",
			method=RequestMethod.POST,
			produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		else {
			try {
				return new ResponseEntity<User>(actions.save(user), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			}
		}

	}

	@RequestMapping(
			value = "/getUsersByPage",
			method = RequestMethod.GET,
			produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json"
			)
	public String getPageOfUsers(@RequestParam("page") int page, @RequestParam("requester") String requester) {
		try {
			PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "lname");
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("version", Long.toString(serialVersionUID));
			responseHeaders.set("requester", requester);
			return  UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<Page<User>>(actions.getUsersByPage(p),responseHeaders,HttpStatus.OK));
		} catch (Exception e) {

			e.printStackTrace();
			return (new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST).toString());

		}
	}


	@RequestMapping(
			value = "/getAllUsers",
			method = RequestMethod.GET,
			produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json"
			)
	public String getAllUsers(@RequestParam("requester") String requester) {
		try {

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("version", Long.toString(serialVersionUID));
			responseHeaders.set("requester", requester);
			return UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<Iterable<User>>(actions.getAllUsers(), HttpStatus.OK));
		} catch (Exception e) {
			return (new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST).toString());
		}

	}

	@RequestMapping(
			value = "/getPageOfUsersByLName",
			method = RequestMethod.GET,
			produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json"
			)
	public String getPageOfUserssByLName(@RequestParam("page") int page,@RequestParam("lname") String lname,@RequestParam("requester") String requester) {
		try {

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("version", Long.toString(serialVersionUID));
			responseHeaders.set("requester", requester);
			PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "lname");

			return UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<Page<User>>(actions.findUserBylname(lname,p), HttpStatus.OK));
		} catch (Exception e) {
			return (new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST).toString());
		}
	}

	@RequestMapping(
			value = "/getPageOfUsersByMedicareNumber",
			method = RequestMethod.GET,
			produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
			headers = "Accept=application/json"
			)
	public String getPageOfUserssByMedicareNumber(@RequestParam("page") int page,@RequestParam("medicare_number") String medicareNumber,@RequestParam("requester") String requester) {
		try {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("version", Long.toString(serialVersionUID));
			responseHeaders.set("requester", requester);
			PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "Medicare_Number");

			return UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<Page<User>>(actions.queryUserByMedicareNumber(medicareNumber,p), HttpStatus.OK));
		} catch (Exception e) {
			e.printStackTrace();
			return (new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST).toString());
		}
	}
}