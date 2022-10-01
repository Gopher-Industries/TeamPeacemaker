package com.appollo.Views;

import java.io.IOException;

import com.appollo.data_pojo.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/*
 * 
 * A customer JSON serializer that allows different views to for each client app.  
 */

public class UserSerializer extends StdSerializer<User> {

    private static final long serialVersionUID = 1L;

    private String key;
    
    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class c) {
        super(c);
    }
   
    public void setKey(String key){
    	this.key = key;
    }
   
	@Override
	public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
		try {
		jsonGenerator.writeStartObject();
		switch (key) {
		case UserView.ETERNALS:
			
	        jsonGenerator.writeNumberField("id", user.getId());
	        jsonGenerator.writeStringField("Medicare_Number",user.getMedicareNumber());
	        jsonGenerator.writeStringField("First_name",user.getfname());
	        jsonGenerator.writeStringField("Last_name",user.getlname());
	        jsonGenerator.writeStringField("Email_address",user.getemail());
	        jsonGenerator.writeStringField("Phone_number",user.getphone_num());
	        jsonGenerator.writeStringField("Emergency_Contact_name_1",user.getemer_name_1());
	        jsonGenerator.writeStringField("Emergency_Contact_phone_number_1",user.getemer_num_1());
	        jsonGenerator.writeStringField("Emergency_Contact_name_2",user.getemer_name_2());
	        jsonGenerator.writeStringField("Emergency_Contact_phone_number_2",user.getemer_num_2());        
	       
			break;
			
		case UserView.AVENGERS:
			
	        jsonGenerator.writeNumberField("Patient_ID", user.getId());
	        jsonGenerator.writeStringField("Given_Name",user.getfname());
	        jsonGenerator.writeStringField("Family_Name",user.getlname());
	        jsonGenerator.writeStringField("Preferred_Name",user.getPreferredName());
	        jsonGenerator.writeStringField("Gender",user.getGender());
	        jsonGenerator.writePOJOField("Date_Of_Birth",user.getEpochDOB());
	        jsonGenerator.writePOJOField("Age_In_Days",user.getage());	
	       
			break;
			
			//TODO more to add
			
		
		}
		 jsonGenerator.writeEndObject();
		}catch(NullPointerException npe)
		{
			jsonGenerator.writeStartObject();
			 jsonGenerator.writeStringField("error","failed serialization");
			jsonGenerator.writeEndObject();
		}
		 
	}
}
