package com.supinfo.notetonsta.dao.jpa;

import com.supinfo.notetonsta.util.PersistenceManager;

public class DaoFactory {
	  
	public DaoFactory() {}
	
	public static JpaInterventionDao interventionDao(){
		return new JpaInterventionDao(PersistenceManager.getEntityManagerFactory());
	}
	
	public static JpaCampusDao campusDao(){
		return new JpaCampusDao(PersistenceManager.getEntityManagerFactory());
	}
	
	public static JpaMarkDao markDao(){
		return new JpaMarkDao(PersistenceManager.getEntityManagerFactory());	
	}
	
	public static JpaSpeakerDao speakerDao(){
		return new JpaSpeakerDao(PersistenceManager.getEntityManagerFactory());
	}
}
