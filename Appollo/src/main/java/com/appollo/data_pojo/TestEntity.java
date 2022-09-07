package com.appollo.data_pojo;


import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "TestEntity")
public class TestEntity {
	@Id
	Long id;

	String fname;

	String lname;

	int year;

	public TestEntity(String fname, String lname, int year) {
		this.fname = fname;
		this.lname = lname;
		this.year = year;
	}

	public long getId() {
		return this.id;
	}
	
	public String getlname()
	{
		return this.lname;
	}
	
	public String getfname()
	{
		return this.fname;
	}
	public int getYear()
	{
		return this.year;
	}

	
	
/*	build and deply project:
 * 
 * right click project Appollo [boot] and select Run As. Select maven build with goal - clean package. Should result in new ja file Appollo-0.0.1-SNAPSHOT.jar in target directory.
 * Within Eclipse copy the new Appollo-0.0.1-SNAPSHOT.jar file from target to src\main\appengine folder
 * right click on appengine folder and open in terminal
 * In terminal run gcloud app deploy.
 */
		
}