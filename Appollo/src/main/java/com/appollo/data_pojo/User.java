package com.appollo.data_pojo;

import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.appollo.validator.Phone;
import com.appollo.validator.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;

/*
 * 
 *  An entity POJO for User records.
 *  Uses annotations for validation and JSON alias
 *  
 *  The @Entity field allows to Spring to serialize/deserialize these objects to and from JSON to Java 
 *  Entity classes get mapped to JSON objects during serialization into JSON responses.
 *  Requests containing User objects get deserialized to User POJOs  
 */

@Entity(name = "User")
public class User {
	@Id
	Long id;
	
	@NotNull @Size(min=2, max=20)
	@JsonAlias({ "fName", "Given_Name","First_Name","firstName","First_Name","familyName","family_Name","First_name" })
	@Field(name = "First_name")
	String fname;

	@NotNull @Size(min=2, max=20)
	@Field(name = "Last_name")
	@JsonAlias({ "lName", "Family_Name","Last_Name","lastName","Last_Name","familyName","family_Name","Last_name" })
	String lname;
	
	@Size(min=2, max=20)
	@Field(name = "Preferred_name")
	@JsonAlias({ "PreferredName", "Preferred_name","Preferred_Name" })
	String preferredName;
	
	@Field(name="Gender")
	@JsonAlias({ "gender", "Gender" })
	String gender;
	
	//Assume that app user should be old enough for usage. 
	@JsonAlias({ "Age", "age","Age_In_Days","age_in_days" })
	@Field(name = "Age")
	int age;
	
	// I think that Doctors would check medicare number legitimacy manually each time seeing a patient, so not sure if needed validator for this
	// Personally I also haven't learned how to identify legitimate Medicare-number as well
	@JsonAlias({ "medicare_number", "Medicare_Number","medicare_Number","MedicareNumber" })
	@Field(name = "Medicare_Number")
	String medicareNumber;
	
	
	@JsonAlias({ "Email_address", "email" })
	@Field(name = "Email")
	String email;
	
	
	@JsonAlias({ "Phone_number", "phone" })
	@Field(name = "Phone_number")
	String phone_num;
	
	//I assume that it's important to always have Emergency contact.
	
	@JsonAlias({ "Emergency_contact_Number1", "Emergency_Contact_phone_number_1" })
	@Field(name = "Emergency_contact_Number1")
	String emer_num_1;
	
	@JsonAlias({ "emergency_Contact_name_1", "Emergency_Name1" })
	@Field(name = "Emergency_Name1")
	String emer_name_1;
	
	
	@JsonAlias({ "Emergency_contact_Number2", "Emergency_Contact_phone_number_2" })
	@Field(name = "Emergency_contact_Number2")
	String emer_num_2;
	
	@JsonAlias({ "emergency_Contact_name_2", "Emergency_Name2" })
	@Field(name = "Emergency_Name2")
	String emer_name_2;
	
	@JsonAlias({ "DOB", "dob", "Date_Of_Birth", "Date_of_Birth" })
	@Field(name="Date_of_Birth")
	Timestamp dob;
	
	@Field(name="serviceSubscription")
	HashSet<String> serviceSubscription;

	@Transient
	transient String requester;
	

public User()
{
	
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
	public void setPreferredName(String pname)
	{
		this.preferredName = pname;
	}
	
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	public void setage(int age)
	{
		this.age=age;
	}
	public void setMedicareNumber(String medicare_num)
	{
		this.medicareNumber = medicare_num;
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
	
	public void setRequester(String requester)
	{
		this.requester=requester;
	}
	
	public String getRequester()
	{
		return requester;
	}
	public long getEpochDOB()
	{
		if(dob!=null) {
			return dob.getSeconds();
		}else
		{
		 return -5364662400l;//1800 nobody is this old so ok for default when null
		}
		
	}
	public Date getDOB() {
		//return dose_taken.toDate().toString();
		if(dob != null)
			return dob.toDate();
		else
			return new Date(0);
	}

	public void setDOB(long seconds) {
		this.dob = Timestamp.ofTimeMicroseconds(seconds*1000*1000);
	}
	
	public void setServiceSubscription(HashSet<String> serviceSubscription)
	{
		this.serviceSubscription = serviceSubscription;
	}
	public void setServiceSubscription(String service)
	{
		if(this.serviceSubscription == null)
		{
			serviceSubscription= new HashSet<String>();
		}
		this.serviceSubscription.add(service);
	}

	
	public String getlname()
	{
		return this.lname;
	}
	public String getfname()
	{
		 return this.fname;
	}
	public String getPreferredName()
	{
		return preferredName;
	}
	public int getage()
	{
		return this.age;
	}
	public String getMedicareNumber()
	{
		return this.medicareNumber;
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
	public String getGender() {
		// TODO Auto-generated method stub
		return this.gender;
	}
	
	public HashSet<String> getServiceSubscription()
	{
		return this.serviceSubscription;
	}
	
	
}