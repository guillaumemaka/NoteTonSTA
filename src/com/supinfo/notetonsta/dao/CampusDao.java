package com.supinfo.notetonsta.dao;


import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.CampusCreationExecption;
import com.supinfo.exception.CampusDeletionException;
import com.supinfo.exception.CampusUpdateException;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;

public interface CampusDao {
	public Campus find(Key campuskey);
	public List<Campus> findAll();
	public List<Intervention> getInterventions(Key campusKey);
	public void addCampus(Campus campus) throws CampusCreationExecption;
	public void removeCampus(Campus campus) throws CampusDeletionException;
	public void updateCampus(Campus campus) throws CampusUpdateException;
}
