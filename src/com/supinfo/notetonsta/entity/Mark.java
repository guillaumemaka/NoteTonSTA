package com.supinfo.notetonsta.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Key key;

	@Basic
	@JsonProperty
	private Double slideMark;

	@Basic
	@JsonProperty
	private Double speakerMark;

	@Basic
	@JsonProperty
	private String idbooster;

	@Basic
	@JsonProperty
	private String coment;

	@Basic
	@JsonIgnore
	private Key intervention;

	@OneToOne(fetch = FetchType.LAZY)
	@Column(name="intervention", insertable = false, updatable = false)
	@JsonIgnore
	private Intervention interventionAlias;

	public Mark() {
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

	public Key getIntervention() {
		return intervention;
	}

	public void setIntervention(Key intervention) {
		this.intervention = intervention;
	}

	public Intervention getInterventionAlias() {
		return interventionAlias;
	}

	public void setInterventionAlias(Intervention interventionAlias) {
		this.interventionAlias = interventionAlias;
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
	 *  JSON Se/Deserialization
	 * 
	 * 
	 */
	
	@XmlElement
	@JsonProperty
	public void key(String keyString){
		this.key = KeyFactory.stringToKey(keyString);				
	}
	
	@XmlElement
	@JsonProperty
	public String key() {
		return KeyFactory.keyToString(key);
	}
	
	@JsonProperty
	@XmlElement
	public void intervention(String keyString){
		this.intervention = KeyFactory.stringToKey(keyString);				
	}
	
	@XmlElement
	@JsonProperty
	public String intervention() {
		return KeyFactory.keyToString(this.intervention);
	}

}