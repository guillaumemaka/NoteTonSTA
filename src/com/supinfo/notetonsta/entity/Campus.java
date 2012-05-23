package com.supinfo.notetonsta.entity;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * The persistent class for the campus datastore table.
 * 
 */
@Entity
public class Campus implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Campus.class.getSimpleName());
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Key key;
	
	@Basic
	private String name;
	
	@Basic
	@JsonIgnore
	private List<Long> interventionIds;

	public Campus(){}
	
	public Campus(Key key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Long> getInterventionIds() {
		return interventionIds;
	}

	public void setInterventionsKey(List<Long> interventionIds) {
		this.interventionIds = interventionIds;
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
	
	@JsonProperty
	public String key(){
		log.log(Level.INFO, "Key conversion (before)", this.key);
		String convert_key = KeyFactory.keyToString(this.key);
		log.log(Level.INFO, "Key conversion (after)", convert_key);
		
		return convert_key;
	}
	
	@JsonProperty
	public void key(String k){
		this.setKey(KeyFactory.stringToKey(k));
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
}