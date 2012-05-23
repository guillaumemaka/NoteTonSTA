package com.supinfo.notetonsta.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.supinfo.notetonsta.entity.Speaker;

public class Authentication {
	private String username;
	private String password;
	private String userFullName;
	private Long userId;
	private final Logger log = Logger.getLogger(Authentication.class.getSimpleName());
	
	public Authentication(){
		
	}
	
	public AuthenticationError authenticate() throws Exception{
		EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();
				
		String hashed_password = Authentication.hash(this.password, "UTF-8");
				
		Speaker speaker;
		
		try {
			em.getTransaction().begin();
			speaker = (Speaker) em.createQuery("SELECT s FROM Speaker AS s WHERE email = :email")
			.setParameter("email", this.username).getSingleResult();
			
			em.getTransaction().commit();			
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			speaker = null;
		}finally{
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			
			if(em.isOpen()){
				em.close();
			}
		}
				
		
		if ( speaker == null ){
			return AuthenticationError.UserNotFound;
		}else if ( ! speaker.getPassword().equals(hashed_password)){
			
//			System.out.println("Plain text password:" + this.password);
//			System.out.println("DB hashed password:" + speaker.getPassword());
//			System.out.println("Plain text hashed password:" + hashed_password);					
			
			return AuthenticationError.PasswordMissMatch;
		}		
		
		this.userFullName = speaker.getFirstname() + " " + speaker.getLastname();
		this.userId = speaker.getKey().getId();
		log.info(this.getUserFullName() + ", userid: " + this.getUserId());
		return AuthenticationError.Success;
	}
	
	public Authentication setCredentials(String username, String password){
		this.username = username;
		this.password = password;
		
		return this;
	}

	public String getUserFullName() {
		// TODO Auto-generated method stub
		return this.userFullName;
	}

	public Long getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}	
	
	/**
	 * 
	 * Static method
	 * @throws GeneralSecurityException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 * 
	 */
	
	public static String hash(String data, String charset) throws GeneralSecurityException, UnsupportedEncodingException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(data.getBytes(charset),0,data.length());
		String hash = new BigInteger(1,md.digest()).toString(16);
		return hash;
	}
	

	public static String generateToken(String s) throws UnsupportedEncodingException, GeneralSecurityException{		
		return Authentication.hash(s, "UTF-8");		
	}
	
}
