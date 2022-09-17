package com.appollo.api_controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.appollo.actions.UserActions;
import com.appollo.data_pojo.User;




@RestController
public class UserController {



   
    
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
                    public ResponseEntity<Slice<User>> getPageOfTestEntitiesByLName(@RequestParam("page") int page) {
                        try {
                            PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "lname");
                            
                            return new ResponseEntity<Slice<User>>(actions.getUsersByPage(p), HttpStatus.OK);
                        } catch (Exception e) {
                            return new ResponseEntity<Slice<User>>(HttpStatus.BAD_REQUEST);
                        }



            }
    
    
     @RequestMapping(
                value = "/getAllUsers",
                method = RequestMethod.GET,
                produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
                headers = "Accept=application/json"
            )
            public ResponseEntity<Iterable<User>> getAllUsers() {
                try {
                    return new ResponseEntity<Iterable<User>>(actions.getAllUsers(), HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<Iterable<User>>(HttpStatus.BAD_REQUEST);
                }



    }
     @RequestMapping(
                value = "/getPageOfUsersByLName",
                method = RequestMethod.GET,
                produces = { MimeTypeUtils.APPLICATION_JSON_VALUE },
                headers = "Accept=application/json"
            )
            public ResponseEntity<Slice<User>> getPageOfUserssByLName(@RequestParam("page") int page,@RequestParam("lname") String lname) {//probably check what this field is called in the database
                try {
                    PageRequest p = PageRequest.of(page, 20,Sort.Direction.ASC, "lname");
                    
                    return new ResponseEntity<Slice<User>>(actions.findUserBylname(lname,p), HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<Slice<User>>(HttpStatus.BAD_REQUEST);
                }



    }
}