package net.wicp.tams.duckula.demo.cassandra.bean;

import java.sql.Date;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

import lombok.Data;

 
@UDT(keyspace = "gvp", name = "train")
public class Train {
	@Field(name = "name")
	private String name;
	//@Field(name = "date")
	//private Long date;
	@Field(name = "grade")
	private String grade;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}