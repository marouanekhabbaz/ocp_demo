package com.revature.util;

import com.revatrure.demo.Car;
import com.revatrure.demo.Service;
import com.revatrure.demo.Client;
import com.revature.exception.DdlException;
import com.revature.util.DataBase;

public class OcpUtil {
	private static DataBase db = new DataBase(); 
	

	private OcpUtil() {
	
	}

	public static DataBase getDabtBase() { 
		
		if (db == null) {
		db =	new DataBase().getConnection(); 
		}
		
		return db;
		
	}
	
	
	
	
}
