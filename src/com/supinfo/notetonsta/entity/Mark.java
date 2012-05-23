package com.supinfo.notetonsta.entity;

import java.io.Serializable;

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


/**
 * The persistent class for the marks database table.
 * 
 */
@Entity
public class Mark implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Key key;

	@Basic
	private Double slideMark;
	
	@Basic
	private Double speakerMark;

	@Basic
	private Long interventionId;

	@Basic
	private String idbooster;
	
	@Basic
	private String coment;
	
	public Mark() {}

	public Mark(Key key, Double slideMark, Double speakerMark,
			String idbooster, String coment) {
		super();
		this.key = key;
		this.slideMark = slideMark;
		this.speakerMark = speakerMark;
		this.idbooster = idbooster;
		this.coment = coment;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Double getSlideMark() {
		return slideMark;
	}

	public void setSlideMark(Double slideMark) {
		this.slideMark = slideMark;
	}

	public Double getSpeakerMark() {
		return speakerMark;
	}

	public void setSpeakerMark(Double speakerMark) {
		this.speakerMark = speakerMark;
	}

	public Long getInterventionId() {
		return interventionId;
	}

	public void setInterventionId(Long interventionId) {
		this.interventionId = interventionId;
	}

	public String getIdbooster() {
		return idbooster;
	}

	public void setIdbooster(String idbooster) {
		this.idbooster = idbooster;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
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
	 * 	JSON Serialization / Deserialzation Datastore key
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