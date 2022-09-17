package com.appollo.data_pojo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.appollo.validator.Phone;

@Entity(name = "User")
public class User {
	@Id
	Long id;
	
	@NotNull @Size(min=2, max=20)
	@Field(name = "First_name")
	String fname;

	@NotNull @Size(min=2, max=20)
	@Field(name = "Last_name")
	String lname;
	
	//Assume that app user should be old enough for usage. 
	@NotNull @Min(12) @Max(150)
	@Field(name = "Age")
	int age;
	
	// I think that Doctors would check medicare number legitimacy manually each time seeing a patient, so not sure if needed validator for this
	// Personally I also haven't learned how to identify legitimate Medicare-number as well
	@Field(name = "Medicare_Number")
	String medicare_num;
	
	@NotEmpty @Email
	@Field(name = "Email")
	String email;
	
	//Check for null and illegitimate number, only checking australian mobile number at the moment, can be added more later in PhoneValidator.
	@Phone
	@Field(name = "Phone_number")
	String phone_num;
	
	//I assume that it's important to always have Emergency contact.
	@Phone
	@Field(name = "Emergency_contact_Number1")
	String emer_num_1;
	
	@NotEmpty
	@Field(name = "Emergency_Name1")
	String emer_name_1;
	
	@Phone
	@Field(name = "Emergency_contact_Number2")
	String emer_num_2;
	
	@NotEmpty
	@Field(name = "Emergency_Name2")
	String emer_name_2;
	

public User()
{
	
}
	public User(String fname, String lname, int age, String medicare_num, String email, String phone_num, String emer_num_1, String emer_name_1, String emer_num_2, String emer_name_2) {
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.medicare_num = medicare_num;
		this.email = email;
		this.phone_num = phone_num;
		this.emer_num_1 = emer_num_1;
		this.emer_name_1 = emer_name_1;
		this.emer_num_2 = emer_num_2;
		this.emer_name_2 = emer_name_2;
	}

	public User(long id, String fname, String lname, int age, String medicare_num, String email, String phone_num, String emer_num_1, String emer_name_1, String emer_num_2, String emer_name_2) {
		this.id=id;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.medicare_num = medicare_num;
		this.email = email;
		this.phone_num = phone_num;
		this.emer_num_1 = emer_num_1;
		this.emer_name_1 = emer_name_1;
		this.emer_num_2 = emer_num_2;
		this.emer_name_2 = emer_name_2;
	}

	public long getId() {
		return this.id;
	}
	
	public void setlname(String lname)
	{
		this.lname=lname;
	}
	public void setfname(String fname)
	{
		this.fname=fname;
	}
	public void setage(int age)
	{
		this.age=age;
	}
	public void setmedicarenum(String medicare_num)
	{
		this.medicare_num = medicare_num;
	}
	public void setemail(String email)
	{
		this.email = email;
	}
	public void setphonenum(String phone_num)
	{
		this.phone_num = phone_num;
	}
	public void setemernum1(String emer_num_1)
	{
		this.emer_num_1 = emer_num_1;
	}
	public void setemername1(String emer_name_1)
	{
		this.emer_name_1 = emer_name_1;
	}
	public void setemernum2(String emer_num_2)
	{
		this.emer_num_2 = emer_num_2;
	}
	public void setemername2(String emer_name_2)
	{
		this.emer_name_2 = emer_name_2;
	}
	
	
	
	
	
	public String getlname()
	{
		return this.lname;
	}
	public String getfname()
	{
		 return this.fname;
	}
	public int getage()
	{
		return this.age;
	}
	public String getmedicare_num()
	{
		return this.medicare_num;
	}
	public String getemail()
	{
		return this.email;
	}
	public String getphone_num()
	{
		return this.phone_num;
	}
	public String getemer_num_1()
	{
		return this.emer_num_1;
	}
	public String getemer_name_1()
	{
		return this.emer_name_1;
	}
	public String getemer_num_2()
	{
		return this.emer_num_2;
	}
	public String getemer_name_2()
	{
		return this.emer_name_2;
	}
	
}