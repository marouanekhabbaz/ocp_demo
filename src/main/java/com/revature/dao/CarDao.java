package com.revature.dao;


import java.sql.SQLException;
import java.util.List;

import com.revatrure.demo.Car;
import com.revature.SQL.DML;
import com.revature.SQL.DQL;
import com.revature.util.DataBase;


public class CarDao {
	

	private static DML dml = new DML();
	private static DQL dql = new DQL();
	
	public 	List<Object> insert(Car... cars) {
		
		try {
	
		List<Object> returned =	dml.insert(cars);
		return returned;
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public void list(int id) {
		try {
		Object obj =	dql.get(Car.class, id);
		System.out.println(obj.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
