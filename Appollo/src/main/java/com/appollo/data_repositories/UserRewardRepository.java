package com.appollo.data_repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;


import com.appollo.data_pojo.UserReward;
import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;

/*
 * interface class used by Spring framework to build database access
 */

public interface UserRewardRepository extends DatastoreRepository<UserReward, Long> {
	
	
	/*
	 *  As at 27/09/2022 fields documented as:
	 * User_Reward_ID
		User_ID
		Reward_code
		User_Points
		Expiry_date
		Code_Redeemed

	 */

	
		
	Page<UserReward> findBy(Pageable page);
	
	
	@Query("SELECT * FROM UserReward WHERE User_ID = @User_ID")
	Page<UserReward> queryPageByUserID(long User_ID, Pageable pageable);
	
	@Query("SELECT * FROM UserReward WHERE User_ID = @User_ID")
	List<UserReward> queryAllByuserID(long User_ID);
	
	@Query("SELECT * FROM UserReward WHERE User_ID = @User_ID AND Expiry_date >= @startDate AND Expiry_date <= @endDate")
	  List<UserReward> queryUserRewardsBetweenDates(@Param("User_ID") long User_ID,@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate);
	
	@Query("SELECT * FROM UserReward WHERE User_ID = @User_ID AND Expiry_date >= @startDate AND Expiry_date <= @endDate")
	Page<UserReward> getPageUserRewardsBetweenDates(@Param("User_ID") long User_ID, @Param("startdate")Timestamp startdate, Timestamp enddate,Pageable p);

	@Query("SELECT * FROM UserReward WHERE Code_Redeemed = @Code_Redeemed")
	Page<UserReward> getPageUserMedsByUserIDDoseTimeBetweenDates(long user_id_fk, @Param("startdate")Timestamp startdate, Timestamp enddate,Pageable p);
	
	

}