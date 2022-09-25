package com.appollo.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.appollo.data_pojo.UserMedication;
import com.appollo.data_repositories.UserMedicationRepository;
import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.datastore.repository.query.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
//import org.springframework.data.repository.*;


@Service
public class UserMedicationActions 
{

	/*
	 * Action classes that link endpoint methods to datarepository queries
	 * Any backend business logic can be added here eg checking if record exists before creating a new record
	 * 
	 * Note the @Service annotation that allows Spring to Autowire this class in its corresponding controller class and entity class
	 */
	
	
	@Autowired
	UserMedicationRepository entityRepository;
	
	public List<UserMedication> getUserMedsByUserID(long userid)
	{
		return this.entityRepository.queryAllByuserID(userid);
	}
	
	public Page<UserMedication> getPageOfUserMedsByUserID(long userid, Pageable pageable)
	{
		return this.entityRepository.queryPageByUserID(userid, pageable);		
		
	}
	public List<UserMedication> getUserMedsByPrescriptionID(long prescriptionid)
	{
		return this.entityRepository.queryAllByPrescriptionID(prescriptionid);
	}
	
	public List<UserMedication> queryUserMedicationsDoseTakenBeforeDate(long userid, long dose_taken)
	{
		return this.entityRepository.queryUserMedicationsDoseTakenBeforeDate(userid,Timestamp.ofTimeMicroseconds(dose_taken*1000*1000));
	}
	

	public UserMedication updateUserMedication(long id, long dose_taken) {
		
		Optional<UserMedication> umOut =  this.entityRepository.findById(id);
		
		if(umOut.isPresent())
		{
			umOut.get().setDose_taken(dose_taken);
			return this.entityRepository.save(umOut.get());
		} else
		{
			return null;
		}
	}
	
	public UserMedication updateUserMedication(UserMedication umIn) {
		
			Optional<UserMedication> umOut =  this.entityRepository.findById(umIn.getId());
			
			if(umOut.isPresent())
			{
				return this.entityRepository.save(umIn);
			} else
			{
				return null;
			}
	}
	public UserMedication createUserMedication(UserMedication um) {
		UserMedication savedEntity = this.entityRepository.save(um);
		return savedEntity;
	}

	public Page<UserMedication> getPageUserMedsByUserIDDoseTimeBetweenDates(long userid, long startdate, long enddate,
			Pageable p) {
		
		return this.entityRepository.getPageUserMedsByUserIDDoseTimeBetweenDates(userid,Timestamp.ofTimeMicroseconds(startdate*1000*1000),Timestamp.ofTimeMicroseconds(enddate*1000*1000), p);	
	}
	
}
