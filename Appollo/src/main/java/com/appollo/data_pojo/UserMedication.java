package com.appollo.data_pojo;

import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity (name = "UserMedication")//Entity annotation fails when using underscores
public class UserMedication {
	
	@Id
	Long id;

	Long user_id_fk;	
	
	Long prescription_id;//medication_script_id_fk
	
	String brand_name;

	String generic_name;
	
	String medication_dose;
	
	String medication_route;
	
	Timestamp dose_taken;
	
	Timestamp dose_time;
	
	
	
	public UserMedication()	{
	
	}
		
	public long getId() {
		return this.id;
	}
	
	public Long getuser_id_fk() {
		return user_id_fk;
	}

	public void setuseridfk(Long useridfk) {
		this.user_id_fk = useridfk;
	}

	public Long getPrescription_id() {
		return prescription_id;
	}

	public void setPrescription_id(Long prescriptionid) {
		this.prescription_id = prescriptionid;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getGeneric_name() {
		return generic_name;
	}

	public void setGeneric_name(String generic_name) {
		this.generic_name = generic_name;
	}

	public String getMedication_dose() {
		return medication_dose;
	}

	public void setMedication_dose(String medication_dose) {
		this.medication_dose = medication_dose;
	}

	public String getMedication_route() {
		return medication_route;
	}

	public void setMedication_route(String medication_route) {
		this.medication_route = medication_route;
	}

	public long getDose_taken() {
		//return dose_taken.toDate().toString();
		return dose_taken.getSeconds();
	}

	public void setDose_taken(long seconds) {
		this.dose_taken = Timestamp.ofTimeMicroseconds(seconds*1000*1000);
	}

	public long getDose_time() {
		return dose_time.getSeconds();
	}

	public void setDose_time(long seconds) {
		this.dose_time = Timestamp.ofTimeMicroseconds(seconds*1000*1000);
	}
}