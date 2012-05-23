package com.supinfo.notetonsta.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * The persistent class for the interventions database table.
 * 
 */
@Entity
public class Intervention implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Basic
	private String subject;

	@Basic
	private String description;

	@Basic
	@JsonIgnore
	private Key speakerKey;

	@Basic
	@JsonIgnore
	private Key campusKey;
	
	@Basic
	private List<Key> marksKey;

	public Intervention() {
	}

	public Intervention(Long id, Date startDate, Date endDate, String subject,
			String description) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subject = subject;
		this.description = description;
	}

	public Intervention(Long id, Date startDate, Date endDate, String subject,
			String description, Key speakerKey, Key campusKey,
			List<Key> marksKey) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subject = subject;
		this.description = description;
		this.speakerKey = speakerKey;
		this.campusKey = campusKey;
		this.marksKey = marksKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Key getSpeakerKey() {
		return speakerKey;
	}

	public void setSpeakerKey(Key speakerKey) {
		this.speakerKey = speakerKey;
	}

	public Key getCampusKey() {
		return campusKey;
	}

	public void setCampusKey(Key campusKey) {
		this.campusKey = campusKey;
	}

	public List<Key> getMarksKey() {
		return marksKey;
	}

	public void setMarksKey(List<Key> marksKey) {
		this.marksKey = marksKey;
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
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this);
	}

	/*
	 * 
	 * Get the Status of the current intervention
	 * 
	 * @return String Not Started | In progress | Done
	 */
	public String getStatus() {
		// TODO Auto-generated method stub

		/*
		 * 
		 * Get the current date
		 */

		Date now = GregorianCalendar.getInstance().getTime();

		// ########## Log #############

		// System.out.println(startDate.toString());
		// System.out.println(endDate.toString());
		// System.out.println(now.toString());
		// System.out.println(years[0][0] + ", " + years[0][1]);

		// ########## Log #############

		/*
		 * 
		 * Status Logic definition
		 */

		if (now.after(startDate) && now.before(endDate)) {
			return "In progress";
		} else if (now.before(startDate)) {
			return "Not started";
		} else if (now.after(endDate)) {
			return "Done";
		}

		return null;
	}
	
	/*
	 * 
	 *	Json Serialization/Deserialization Datastore Key
	 * 
	 * 
	 */
	
	@JsonProperty
	public String speakerKey(){
		return KeyFactory.keyToString(speakerKey);
	}
	
	@JsonProperty
	public void speakerKey(String keystring){
		this.setSpeakerKey(KeyFactory.stringToKey(keystring));
	}
	
	@JsonProperty
	public String campusKey(){
		return KeyFactory.keyToString(this.campusKey);
	}
	
	@JsonProperty
	public void campusKey(String keystring){
		this.setCampusKey(KeyFactory.stringToKey(keystring));
	}
	
}