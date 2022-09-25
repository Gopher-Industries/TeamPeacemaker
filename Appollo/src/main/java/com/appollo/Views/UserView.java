package com.appollo.Views;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.appollo.data_pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/*
 * A singleton class that adds custom JSON serializers to an ObjectMapper for each expected client.
 * Each ObjectMapper is placed in the userViewsmap HashMap with the client name as the Map key.
 * This key can be sent as a request parameter to ensure the response contains the custom fields8 and field names expected by the client
 * 
 * 
 * 
 */

public class UserView {
	
	private static UserView single_instance = null;
	
	static final String ETERNALS = "eternals"; //todo make this an encrypted shared key
	static final String AVENGERS = "avengers"; 
	
	UserSerializer eternals_serializer;
	UserSerializer avengers_serializer;
	
	private Map<String,ObjectMapper> userViewsmap;		
	private ObjectMapper teamEternalsUserMapper;
	private ObjectMapper teamAvengersUserMapper;
	
	 public static UserView getInstance()
	 {
	        if (single_instance == null)
	            single_instance = new UserView();	  
	        return single_instance;
	 }
	
	
	private UserView()
	{
		userViewsmap = new HashMap<String,ObjectMapper>();
		
		
		eternals_serializer = new UserSerializer();
		eternals_serializer.setKey(ETERNALS);
		teamEternalsUserMapper = new ObjectMapper();
		SimpleModule eternalsMappingModule = new SimpleModule();

		//simpleModule.addSerializer(DateTime.class, new CustomJodaDateTimeSerializer());
		//simpleModule.addSerializer(Double.class, new DoubleToStringCustomSerializer());
		eternalsMappingModule.addSerializer(User.class, eternals_serializer);
		teamEternalsUserMapper.registerModule(eternalsMappingModule);
		userViewsmap.put(ETERNALS, teamEternalsUserMapper);
		
		avengers_serializer = new UserSerializer();
		avengers_serializer.setKey(AVENGERS);
		teamAvengersUserMapper = new ObjectMapper();
		SimpleModule avengersMappingModule = new SimpleModule();
		avengersMappingModule.addSerializer(User.class, avengers_serializer);
		teamAvengersUserMapper.registerModule(avengersMappingModule);
		userViewsmap.put(AVENGERS, teamAvengersUserMapper);
	}
	 
    public ObjectMapper getObjectMapper(String mapKey)
    {
    	return userViewsmap.get(mapKey);
    }

}
