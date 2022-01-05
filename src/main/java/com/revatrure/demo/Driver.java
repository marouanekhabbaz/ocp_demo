package com.revatrure.demo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.revature.SQL.DDL;
import com.revature.SQL.DML;
import com.revature.SQL.DQL;
import com.revature.SQL.Transaction;
import com.revature.exception.DdlException;
import com.revature.introspection.ColumnField;
import com.revature.introspection.Inspector;
import com.revature.introspection.PrimaryKeyField;
import com.revature.util.DataBase;


public class Driver {
	
	
	public static DataBase db = new DataBase().getConnection();
	private static DDL ddl = new DDL();
	private static DML dml = new DML();
	private static DQL dql = new DQL();
	private static Scanner scanner  = new Scanner(System.in); 

	public static void main(String[] args)  {
	
		
		
		
		
		try {
			db.addMappedClass(  Client.class,  Car.class , Store.class );
			run();
		} catch (DdlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void run() {
		
		Client client1 = new Client("marwane", "khab", 27, true);
		Client client2 = new Client("adam", "jhon", 18, false);
		Client client3 = new Client("jhon", "adam", 30, true);
		Client client4 = new Client("messi", "leo", 37,true);
		Client client5 = new Client("ref", "nadal", 32);
		
		Car[] cars = {
			new Car("bmw", "white", 25),
			new Car("audi", "black", 27),
			new Car("ford", "blue", 25),
			new Car("benz", "white", 30),
			new Car("chevy", "grey", 24),
			new Car("toyota", "black", 22),
			new Car("honda", "white", 24)
		};
		
		
		
		System.out.println(" \n ");
		
		System.out.println(" ==================================================================== \n ");
		
		System.out.println("Welcome to OCP demo App");
		
		System.out.println("1- Please enter  A to turncate cars table \n");
		
		System.out.println("2- Please enter  A+ to force turncate cars table \n");
		
		System.out.println("3- Please enter  B to alter persons table by adding a new column (email) \n");
		
		System.out.println("4- Please enter  C to alter persons table by dropping email column \n");
		
		System.out.println("5- Please enter D to drop stores table \n");
		
		System.out.println("6- Please enter E to create a table for stores \n");
		
		System.out.println("-------------------------------------------------- \n ");
		
		System.out.println("7- Please enter F to insert clients into clients table \n");
		
		System.out.println("8- Please enter G to cache the clients table  \n");
		
		System.out.println("9- Please enter H to add cars to car table  \n");
		
		String[] instructionsStrings = {
				"1- Please enter  A to turncate cars table \n",
				"2- Please enter  A+ to force turncate cars table \n",
				"3- Please enter  B to alter persons table by adding a new column (email) \n",
				"4- Please enter  C to alter persons table by dropping email column \n",
				"5- Please enter B to drop All tables \n",
				"6- Please enter E to create a table for stores \n",
				"7- Please enter F to insert clients into clients table \n",
				"8- Please enter G to cache the clients table  \n",
				"9- Please enter H to add cars to car table  \n"
		};
		
		String[] validInputarr = {"A","a", "A+", "a+" ,"B","b", "C", "c", "D", "d" , "E", "e" , "F", "f"
				, "G", "g", "H", "h"};
	
		
		String input = scanner.next();
		
			input = entryValidation(input, validInputarr, instructionsStrings);
			
			if(input.equalsIgnoreCase("A")) {
				
				try {
					ddl.truncate(Car.class);
				} catch (DdlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				run();
			}
			
			if(input.equalsIgnoreCase("A+")) {
				
				try {
					ddl.truncateCascade(Car.class);
				} catch (DdlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				run();
			}
			
			if(input.equalsIgnoreCase("B")) {
				
				try {
					ddl.alter(Client.class, " ADD Email varchar(50) ");
				} catch (DdlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				run();
				
			}
		
			if(input.equalsIgnoreCase("C")) {
				
				try {
					ddl.alter(Client.class, " DROP COLUMN Email  ");
				} catch (DdlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				run();
				
			}
		
			
			if(input.equalsIgnoreCase("D")) {
				
				try {
					ddl.dropCascade(Store.class);
				} catch (DdlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				run();
				
			}
			
			
			if(input.equalsIgnoreCase("E")) {
				
				try {
					ddl.create(Store.class);
				} catch (DdlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				run();
				
			}
			
			List<Object> clientsReturned ;
			if(input.equalsIgnoreCase("F")) {
				
				try {
					clientsReturned =	dml.insert(client1, client2, client3, client4 , client5);
				} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				run();
				
				}
			
			if(input.equalsIgnoreCase("G")) {
				
					db.addTableToCach(Client.class);	
					
					System.out.println("Printing cache of the Database");
					System.out.println(db.cache);
					
				run();
				
				}
			
			
			
			// **********
			
			if(input.equalsIgnoreCase("H")) {
				/*
				 * get owner Id from the cache 
				 * then set owner id to some cars 
				 * then push it to the data base
				 */
			
				LinkedList<Client> clients = (LinkedList<Client>) db.cache.get("clients");
				
				
				for(int i=0 ; i < clients.size() ; i++) {
					
					int ownerId = clients.get(i).getId();
					
					 cars[i].setOwner(ownerId);
					 
					
				}
				
				try {
					
					dml.insert(cars);
				
				} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			run();
			
			}
			
		
		
	
	}
	
	
	
	
	public static String entryValidation(String input , String[] validInputarr , String[] instructions) {
		 List<String>  validInput = new ArrayList<>(Arrays.asList(validInputarr));
		while(!validInput.contains(input)) {
			System.out.println("=============================================================== \n");
			System.out.println( input + " is Not a valid entry");
			for(String str: instructions) {
				System.out.println(str);
			}
		
			input  = scanner.next();
		}
		return input;
	}
	

	
	
	
	
		
		
	
}
