

package com.appollo.data_pojo;

import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

/*
 * 
 *  An entity POJO for UserReward records.
 *
 *  The @Entity field allows to Spring to serialize/deserialize these objects to and from JSON to Java 
 *  Entity classes get mapped to JSON objects during serialization into JSON responses.
 *  Requests containing UserMedication objects get deserialized to UserMedication POJOs  
 *  
 *  Could use annotations for validation, the @field annotation for more explicit mapping to database fields and @JSON alias for more flexibility with request parameter names 
 *  - without these annotations method and property names must match up with request parameters and database fields to allow Spring to do its mapping magic 
 *  
 */

@Entity (name = "UserReward")//Entity annotation fails when using underscores
public class UserReward {
	
	@Id
	Long id;
	
	Long User_ID;	
	
	Integer User_Points;
	
	String Reward_Code;
	
	Timestamp Expiry_Date;
	
	Boolean Code_Redeemed;
	
	/*
	 *  As at 27/09/2022 fields documented as:
	 * User_Reward_ID
		User_ID
		Reward_code
		User_Points
		Expiry_date
		Code_Redeemed

	 */
	
	public long getId() {
		return this.id;
	}

	public Long getUser_ID() {
		return User_ID;
	}

	public void setUser_ID(Long user_ID) {
		this.User_ID = user_ID;
	}

	public Integer getUser_Points() {
		return User_Points;
	}

	public void setUser_Points(Integer user_Points) {
		this.User_Points = user_Points;
	}

	public String getReward_Code() {
		return Reward_Code;
	}

	public void setReward_Code(String reward_Code) {
		this.Reward_Code = reward_Code;
	}

	public long getExpiry_Date() {
		return Expiry_Date.getSeconds();
	}

	public void setExpiry_Date(long seconds) {
		this.Expiry_Date = Timestamp.ofTimeMicroseconds(seconds*1000*1000);
	}

	public Boolean getCode_Redeemed() {
		return Code_Redeemed;
	}

	public void setCode_Redeemed(Boolean code_Redeemed) {
		this.Code_Redeemed = code_Redeemed;
	}
	
	public UserReward()	{
	
	}

}