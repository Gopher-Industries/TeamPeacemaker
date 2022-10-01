

package com.appollo.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.stereotype.Service;

import com.appollo.Views.UserView;
import com.appollo.data_pojo.User;
import com.appollo.data_pojo.UserMedication;
import com.appollo.data_repositories.UserRepository;

import com.google.cloud.spring.data.datastore.repository.query.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.Optional;

/*
 * Action classes that link endpoint methods to datarepository queries
 * Any backend business logic can be added here eg checking if record exists before creating a new record
 * 
 * Note the @Service annotation that allows Spring to Autowire this class in its corresponding controller class and entity class
 */


@Service
public class UserActions {

	@Autowired
	UserRepository userRepository;

	//Create users
	/*public User saveUser(String fname, String lname, int age, String medicare_num, String email, String phone_num, String emer_num_1, String emer_name_1, String emer_num_2, String emer_name_2)
	{
		User savedUser = this.userRepository.save(new User(fname, lname, age, medicare_num, email, phone_num, emer_num_1, emer_name_1, emer_num_2, emer_name_2));
		return savedUser;
	}

	public User createUser() {
		User savedUser = this.userRepository.save(new User("User", "First", 18, "B38834-343843", "fakemail@mail.com", "dfdasdAS", "12312312", "firstemername", "12312321", "2ndemername"));
		return savedUser;  
    }*/

	public User save(User newuser) {

		User existinguser;

		if( newuser.getMedicareNumber() != null && !this.userRepository.findByMedicareNumber(newuser.getMedicareNumber()).isEmpty())
		{
			existinguser = this.userRepository.queryOneByMedicareNumber(newuser.getMedicareNumber());			
			existinguser = updateUser(newuser,existinguser);			
			return this.userRepository.save(existinguser);
			
		} else if( !this.userRepository.findByEmail(newuser.getemail()).isEmpty()) {
			
			existinguser = this.userRepository.findByEmail(newuser.getemail()).get(0);
			existinguser = updateUser(newuser,existinguser);
			return this.userRepository.save(existinguser);
		}
		else {
			newuser.setServiceSubscription(newuser.getRequester());
			return this.userRepository.save(newuser);
		}
	}

	private User updateUser(User newuser,User existinguser)
	{
		if(newuser.getage() > 0) {

			existinguser.setage(newuser.getage());
		}
		if(newuser.getEpochDOB() > 0) {

			existinguser.setDOB(newuser.getEpochDOB());
		}
		if(newuser.getemail() != null)
		{
			existinguser.setemail(newuser.getemail());
		}
		if(newuser.getMedicareNumber() != null)
		{
			existinguser.setMedicareNumber(newuser.getMedicareNumber());
		}
		if(newuser.getemer_name_1() != null)
		{
			existinguser.setemername1(newuser.getemer_name_1());
		}
		if(newuser.getemer_name_2() != null)
		{
			existinguser.setemername2(newuser.getemer_name_2());
		}
		if(newuser.getemer_num_1() != null)
		{
			existinguser.setemernum1(newuser.getemer_num_1());
		}
		if(newuser.getemer_num_2() != null)
		{
			existinguser.setemernum1(newuser.getemer_num_2());
		}
		if(newuser.getfname() != null)
		{
			existinguser.setfname(newuser.getfname());
		}
		if(newuser.getGender() != null)
		{
			existinguser.setGender(newuser.getGender());
		}
		if(newuser.getlname() != null)
		{
			existinguser.setlname(newuser.getlname());
		}
		if(newuser.getphone_num() != null)
		{
			existinguser.setphonenum(newuser.getphone_num());
		}
		if(newuser.getPreferredName() != null)
		{
			existinguser.setPreferredName(newuser.getPreferredName());
		}
		if(newuser.getRequester() !=null && Arrays.asList(UserView.serviceSubscriptions).contains(newuser.getRequester()))
		{
			existinguser.setServiceSubscription(newuser.getRequester());
		}
		return existinguser;
	}

	//Delete all users
	public void removeAll() {
		this.userRepository.deleteAll();
	}


	
	public Page<User> getUsersByPage(String requester, Pageable pageable)
	{		

		Page<User> s = this.userRepository.queryUsersByPage(requester,pageable);		
		return s;
	}


	//Find Queries
	/*public List<User> findByLname(String lname) {
		List<User> users = this.userRepository.findByLname(lname);
		return users;
	}*/

	//findBylname

	public List<User> findByAgeGreaterThan(int age) {
		List<User> users = this.userRepository.findByAgeGreaterThan(age);
		return users;
	}


	public Page<User> getUsersByLastName(String lastName,String requester, Pageable pageable)
	{
		Page<User> s = this.userRepository.queryUsersByLName(lastName,requester, pageable);

		return s;
	}



	// Test email check
	public boolean isEmailAlreadyInUse(String email){

		boolean userInDb = true;
		if (getUsersByEmail(email) == true) userInDb = false;
		return userInDb;
	}

	// Get user by email
	public Boolean getUsersByEmail(String email)
	{
		List<User> s = this.userRepository.findByEmail(email);
		System.out.print(s.isEmpty());
		System.out.print(s);
		return s.isEmpty();
	}

	public Page<User> queryUserByMedicareNumber(String medicareNumber,String requester, PageRequest p) {
		// TODO Auto-generated method stub
		return this.userRepository.queryByMedicareNumber(medicareNumber,requester, p);
	}
	public Page<User> queryByMedicareNumberEqualorGreaterThan(String medicareNumber,String requester, PageRequest p) {
		// TODO Auto-generated method stub
		return this.userRepository.queryByMedicareNumber(medicareNumber,requester, p);
	}

	public User getUserByID(long id) {
		Optional<User> user =  this.userRepository.findById(id);
		
		if(user.isPresent())
		{
			return user.get();
		} else
		{
			return null;
		}
	}

	public User getUserByEmail(String email, String requester) {
		Optional<User> user =  this.userRepository.queryByEmail(email, requester);
		
		if(user.isPresent())
		{
			return user.get();
		} else
		{
			return null;
		}
	}
}
