package com.supinfo.notetonsta.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.exception.KeyNotFoundException;

/**
 * The persistent class for the speakers database table.
 * 
 */
@Entity
public class Speaker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Key key;

	@Basic
	private String email;

	@Basic
	private String firstname;

	@Basic
	private String lastname;

	@Basic
	private String password;
	
	@Basic
	private String token;

	@Basic
	private List<Long> interventionIds;

	public Speaker() {
	}

	public Speaker(Key key, String email, String firstname, String lastname,
			String password) {
		super();
		this.key = key;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Long> getInterventionIds() {
		return interventionIds;
	}

	public void setInterventionIds(List<Long> interventionsKey) {
		this.interventionIds = interventionsKey;
	}

	public void addInterventionId(Long id){
		if(!this.interventionIds.contains(id)) {
			this.interventionIds.add(id);
		}
	}
	
	public void removeInterventionId(Long id) throws KeyNotFoundException {
		if(!this.interventionIds.contains(id)){
			throw new KeyNotFoundException();			
		}
		
		this.interventionIds.remove(id);
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return EqualsBuilder.reflectionEquals(this, arg0);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/*
	 * 
	 * 
	 *  JSON Serialization/De-serialization Datastore Key
	 * 
	 * 
	 * 
	 */
	
	@JsonProperty
	public String key(){
		return KeyFactory.keyToString(this.key);
	}
	@JsonProperty
	public void key(String keystring){
		this.setKey(KeyFactory.stringToKey(keystring));
	}
}