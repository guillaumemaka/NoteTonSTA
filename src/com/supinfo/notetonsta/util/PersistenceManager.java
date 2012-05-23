package com.supinfo.notetonsta.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {	
	private static EntityManagerFactory emf = null;
		
	public static EntityManagerFactory getEntityManagerFactory(){
		if(emf == null){
			emf = Persistence.createEntityManagerFactory("NoteTonSTA-PU");
		}
				
		return emf;
	}
		
	public static void close(){
		if(emf != null) {
			emf.close();
		}		
	}
}
