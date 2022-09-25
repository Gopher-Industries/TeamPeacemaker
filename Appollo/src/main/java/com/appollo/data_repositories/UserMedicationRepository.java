package com.appollo.data_repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import com.appollo.data_pojo.TestEntity;
import com.appollo.data_pojo.UserMedication;
import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;

/*
 * interface class used by Spring framework to build database access
 */

public interface UserMedicationRepository extends DatastoreRepository<UserMedication, Long> {

	@Query("SELECT * FROM UserMedication WHERE prescription_id = @prescription_id")
	List<UserMedication> queryAllByPrescriptionID(long prescription_id);
	
		
	Page<UserMedication> findBy(Pageable page);
	
	
	@Query("SELECT * FROM UserMedication WHERE user_id_fk = @user_id_fk")
	Page<UserMedication> queryPageByUserID(long user_id_fk, Pageable pageable);
	
	@Query("SELECT * FROM UserMedication WHERE user_id_fk = @user_id_fk")
	List<UserMedication> queryAllByuserID(long user_id_fk);
	
	@Query("SELECT * FROM UserMedication WHERE user_id_fk = @user_id_fk AND dose_taken <= @dose_taken")
	  List<UserMedication> queryUserMedicationsDoseTakenBeforeDate(@Param("user_id_fk") long user_id_fk,@Param("dose_taken")Timestamp dose_taken);

	@Query("SELECT * FROM UserMedication WHERE user_id_fk = @user_id_fk AND dose_time >= @startdate AND dose_time <= @enddate")
	Page<UserMedication> getPageUserMedsByUserIDDoseTimeBetweenDates(long user_id_fk, @Param("startdate")Timestamp startdate, Timestamp enddate,Pageable p);
	
	

}