package com.supinfo.notetonsta.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
@XmlRootElement
public class Intervention implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	@XmlTransient
	private Key key;

	@Temporal(TemporalType.DATE)
	@XmlElement
	@JsonProperty
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@XmlElement
	@JsonProperty
	private Date endDate;

	@Basic
	@XmlElement
	@JsonProperty
	private String subject;

	@Basic
	@XmlElement
	@JsonProperty
	private String description;
	
	@Column(insertable = false, updatable = false)
	@XmlElement
	@JsonProperty
	private String status;
	
	@Basic
	@JsonIgnore
	private Key campus;
	
	@OneToOne(fetch = FetchType.EAGER)
	@Column(name="campus", insertable = false, updatable = false)
	@XmlTransient
	@JsonIgnore
	private Campus campusAlias;
	
	@Basic
	@XmlTransient
	@JsonIgnore
	private Key speaker;
	
	@OneToOne(fetch = FetchType.EAGER)
	@Column(name="speaker", insertable = false, updatable = false)
	@XmlTransient
	@JsonIgnore
	private Speaker speakerAlias;

	@Basic
	@XmlTransient
	@JsonIgnore
	private List<Key> marks = new ArrayList<Key>();

	@OneToMany( fetch = FetchType.LAZY)
	@Column(name="marksKey", insertable = false, updatable = false)
	@XmlTransient
	@JsonIgnore
	private List<Mark> marksAlias = new ArrayList<Mark>();

	public Intervention() {
		this.key = KeyFactory.createKey(getClass().getSimpleName(),
				new Date().getTime());
	}

	

	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
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



	public Key getCampus() {
		return campus;
	}



	public void setCampus(Key campus) {
		this.campus = campus;
	}



	public Campus getCampusAlias() {
		return campusAlias;
	}



	public void setCampusAlias(Campus campusAlias) {
		this.campusAlias = campusAlias;
	}



	public Key getSpeaker() {
		return speaker;
	}



	public void setSpeaker(Key speaker) {
		this.speaker = speaker;
	}



	public Speaker getSpeakerAlias() {
		return speakerAlias;
	}



	public void setSpeakerAlias(Speaker speakerAlias) {
		this.speakerAlias = speakerAlias;
	}



	public List<Key> getMarks() {
		return marks;
	}



	public void setMarks(List<Key> marks) {
		this.marks = marks;
	}



	public List<Mark> getMarksAlias() {
		return marksAlias;
	}



	public void setMarksAlias(List<Mark> marksAlias) {
		this.marksAlias = marksAlias;
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
			status = "In progress";
		} else if (now.before(startDate)) {
			status = "Not started";
		} else if (now.after(endDate)) {
			status = "Done";
		}

		return status;
	}

	/*
	 * 
	 * Manage manually OneToMany Relationship
	 */

	public void addMark(Mark mark) {
		if( ! marks.contains(mark.getKey())){
			this.marks.add(mark.getKey());			
		}
	}

	public void remaveMark(Mark mark) {
		
		if(marks.contains(mark.getKey())){
			this.marks.remove(mark.getKey());			
		}
	}
	
	/*
	 * 
	 *  JSON Se/Deserialization
	 * 
	 * 
	 */
	
	@JsonProperty
	@XmlElement
	public void key(String keyString){
		this.key = KeyFactory.stringToKey(keyString);				
	}
	
	@XmlElement
	@JsonProperty
	public String key() {
		return KeyFactory.keyToString(key);
	}
	
	@XmlElement
	@JsonProperty
	public int countMark() {
		return this.marks.size();
	}
	
}