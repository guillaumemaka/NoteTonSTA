package com.supinfo.notetonsta.dao;


import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.CampusCreationExecption;
import com.supinfo.exception.CampusDeletionExecption;
import com.supinfo.notetonsta.entity.Campus;

public interface CampusDao {
	public Campus find(Key campusKey);
	public List<Campus> findAll();
	public void addCampus(Campus campus) throws CampusCreationExecption;
	public void removeCampus(Campus campus) throws CampusDeletionExecption;
}
