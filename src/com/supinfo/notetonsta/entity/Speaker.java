package com.supinfo.notetonsta.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.appengine.api.datastore.Key;

/**
 * The persistent class for the speakers database table.
 * 
 */
@Entity
public class Speaker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private List<Key> interventions = new ArrayList<Key>();

	@OneToMany(fetch = FetchType.LAZY)
	@Column(name = "interventions", insertable = false, updatable = false)
	private List<Intervention> interventionsAlias;

	public Speaker() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key id) {
		this.key = id;
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

	public List<Key> getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Key> interventions) {
		this.interventions = interventions;
	}

	public List<Intervention> getInterventionsAlias() {
		return interventionsAlias;
	}

	public void setInterventionsAlias(List<Intervention> interventionsAlias) {
		this.interventionsAlias = interventionsAlias;
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