package com.appollo.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.appollo.data_pojo.User;
import com.appollo.data_repositories.UserRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.Optional;


@Service
public class UserActions {

	@Autowired
	UserRepository userRepository;
	
	//Create users
	public User saveUser(String fname, String lname, int age, String medicare_num, String email, String phone_num, String emer_num_1, String emer_name_1, String emer_num_2, String emer_name_2)
	{
		User savedUser = this.userRepository.save(new User(fname, lname, age, medicare_num, email, phone_num, emer_num_1, emer_name_1, emer_num_2, emer_name_2));
		return savedUser;
	}
	
	public User createUser() {
		User savedUser = this.userRepository.save(new User("User", "First", 18, "B38834-343843", "fakemail@mail.com", "dfdasdAS", "12312312", "firstemername", "12312321", "2ndemername"));
		return savedUser;  
    }
	
	public User save(User t) {
		User savedUser = this.userRepository.save(t);
		return savedUser;
	}
	
	//Delete all users
	public void removeAll() {
		this.userRepository.deleteAll();
	}
	

	//Get Queries
	public List<User> getAllUsers() 
	{
		Iterable<User> users = this.userRepository.findAll();
		List<User> usersList = new ArrayList<>();
		users.forEach(usersList::add);		
		return usersList;
	}
	
	public Slice<User> getUsersByPage(Pageable pageable)
	{
		
		
		Slice<User> s = this.userRepository.findBy(pageable);
		
		return s;
	}

	
	//Find Queries
	public List<User> findByLname(String lname) {
		List<User> users = this.userRepository.findByLname(lname);
		return users;
	}

	public List<User> findByAgeGreaterThan(int age) {
		List<User> users = this.userRepository.findByAgeGreaterThan(age);
		return users;
	}

	
	public List<User> findByAuthorAge(String lname, int age) {
		List<User> users = this.userRepository.findByLnameAndAge(lname, age);
		return users;
	}

	
	public Slice<User> findUserBylname(String lname, Pageable pageable)
	{
		Slice<User> s = this.userRepository.findBylname(lname, pageable);
		
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
	
}
