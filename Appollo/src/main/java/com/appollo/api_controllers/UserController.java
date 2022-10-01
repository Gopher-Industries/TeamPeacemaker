package com.appollo.api_controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.appollo.Views.UserView;
import com.appollo.actions.UserActions;
import com.appollo.data_pojo.User;


/* Endpoints for CRUD operations on User records used by all teams that store user records
 * For a list of expected request parameters and returned response parameters see:
 * ?????
 * Note that the underlying entity class can be configured to allow alias names on POST request parameters 
 * Responses using the UserView singleton instance can be customized to suite the client - ie which User fields are returned and the name used for each name/value pair  
 * Note that the UserView relies on the request containing a valid requester parameter. At present this is an unencrypted string eg: requester=eternals/avengers
 * 
 * 
 * createUser
 * getUsersByPage
 * getAllUsers
 * getPageOfUsersByLName - search by last name
 * getPageOfUsersByMedicareNumber - should only return a single record since medicare numbers are unique identifiers but test data can be unreliable!
 * 
 *  Paged results return up to 20 records per request. The response includes fields to indicate current page, total number of pages and if more records exist. 
 */


@RestController
public class UserController {

	private static final long serialVersionUID = 1L;
	@Autowired
	Environment env;


	@Autowired
	UserActions actions;

	@RequestMapping(value="/createUser",method=RequestMethod.POST,produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },headers = "Accept=application/json")
	public String createUser(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request)
	{
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("version", Long.toString(serialVersionUID));
		responseHeaders.set("requester", user.getRequester());
		
		try {
				if (bindingResult.hasErrors()) {
					//System.out.println(bindingResult.toString());
					return (new ResponseEntity<String>("Invalid values: "+ bindingResult.toString(),HttpStatus.BAD_REQUEST).toString());
					
				}else {
					return UserView.getInstance().getObjectMapper(user.getRequester()).writeValueAsString(new ResponseEntity<User>(actions.save(user),responseHeaders, HttpStatus.OK));
				}
		} catch (Exception e) {
				e.printStackTrace();
				return getErrorString(e,request);
		}
	}

	@RequestMapping(value = "/getUsersByPage",method = RequestMethod.GET,produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },headers = "Accept=application/json")
	public String getPageOfUsers( HttpServletRequest request,@RequestParam("page") int page, @RequestParam("requester") String requester) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("version", Long.toString(serialVersionUID));
		responseHeaders.set("requester", requester);
		
		try {
			//System.out.println("Active profiles: "+Arrays.asList(env.getActiveProfiles()));
			PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "Last_name");
			
			return  UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<Page<User>>(actions.getUsersByPage(requester,p),responseHeaders,HttpStatus.OK));
		} catch (Exception e) {
			
			return getErrorString(e,request);
		}
	}
	
	@RequestMapping(value = "/getUserByID",method = RequestMethod.GET,produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },headers = "Accept=application/json")
	public String getUserByID( HttpServletRequest request, @RequestParam("requester") String requester, @RequestParam("id") long id) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("version", Long.toString(serialVersionUID));
		responseHeaders.set("requester", requester);
		
		try {
						
			return  UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<User>(actions.getUserByID(id),responseHeaders,HttpStatus.OK));
		} catch (Exception e) {
			
			return getErrorString(e,request);
		}
	}
	
	@RequestMapping(value = "/getUserByEmail",method = RequestMethod.GET,produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },headers = "Accept=application/json")
	public String getUserByEmail( HttpServletRequest request, @RequestParam("requester") String requester, @RequestParam("email") String email) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("version", Long.toString(serialVersionUID));
		responseHeaders.set("requester", requester);
		
		try {
						
			return  UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<User>(actions.getUserByEmail(email,requester),responseHeaders,HttpStatus.OK));
		} catch (Exception e) {
			
			return getErrorString(e,request);
		}
	}

	@RequestMapping(value = "/getPageOfUsersByLName",method = RequestMethod.GET,produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },headers = "Accept=application/json"	)
	public String getPageOfUsersByLName(HttpServletRequest request,@RequestParam("page") int page,@RequestParam("last_name") String lastName,@RequestParam("requester") String requester) {
		try {

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("version", Long.toString(serialVersionUID));
			responseHeaders.set("requester", requester);
			PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "Last_name");

			return UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<Page<User>>(actions.getUsersByLastName(lastName,requester,p),responseHeaders, HttpStatus.OK));
		} catch (Exception e) {
			return getErrorString(e,request);
		}
	}

	
	
	@RequestMapping(value = "/getPageOfUsersByMedicareNumber",method = RequestMethod.GET,produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },headers = "Accept=application/json")
	public String getPageOfUsersByMedicareNumber(HttpServletRequest request,@RequestParam("page") int page,@RequestParam("medicare_number") String medicareNumber,@RequestParam("requester") String requester) {
		try {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("version", Long.toString(serialVersionUID));
			responseHeaders.set("requester", requester);
			PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "Medicare_Number");

			return UserView.getInstance().getObjectMapper(requester).writeValueAsString(new ResponseEntity<Page<User>>(actions.queryUserByMedicareNumber(medicareNumber,requester,p),responseHeaders, HttpStatus.OK));
		} catch (Exception e) {
			
			return getErrorString(e,request);
		}
	}
	
	private String getErrorString(Exception e, HttpServletRequest request)
	{
		String error ="";
		//create a unique error ID and print to a String
		UUID guid = java.util.UUID.randomUUID();
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);
    	  
    	//if deployed to cloud provide only the error ID for support/debug and log the request and the stacktrace with error ID
		 if (Arrays.asList(env.getActiveProfiles()).contains("cloud")) {
			 System.err.println("\nError GUID: "+ guid+" \nRequest:"+ httpServletRequestToString(request)  + " \nStacktrace: "+sw.toString());
			 error = new ResponseEntity<String>("For debug report error GUID: "+ guid,HttpStatus.BAD_REQUEST).toString();
	      }else {
	    	  //if not deployed to cloud send back the stacktrace
	    	  e.printStackTrace();		    	 
	    	  error = new ResponseEntity<String>("\nError GUID: "+ guid+" \nRequest:"+ httpServletRequestToString(request)  + " \nStacktrace: "+sw.toString(),HttpStatus.BAD_REQUEST).toString();
	      }
		 return error;
	}
	private String httpServletRequestToString(HttpServletRequest request) {
	    StringBuilder sb = new StringBuilder();
	    String headers =
		        Collections.list(request.getHeaderNames()).stream()
		            .map(headerName -> headerName + " : " + Collections.list(request.getHeaders(headerName)) )
		            .collect(Collectors.joining(", "));

		    if (headers.isEmpty()) {
		        sb.append("\nRequest headers: NONE,");
		    } else {
		        sb.append("\nRequest headers: ["+headers+"],");
		    }

	    sb.append("\nRequest Method = [" + request.getMethod() + "] ");
	    sb.append("\nRequest URL Path = [" + request.getRequestURL() + "] ");

	    
	    String parameters =
	        Collections.list(request.getParameterNames()).stream()
	            .map(p -> p + " : " + Arrays.asList( request.getParameterValues(p)) )
	            .collect(Collectors.joining(", "));             

	    if (parameters.isEmpty()) {
	        sb.append("\nRequest parameters: NONE.");
	    } else {
	        sb.append("\nRequest parameters: [" + parameters + "].");
	    }

	    return sb.toString();
	}
}