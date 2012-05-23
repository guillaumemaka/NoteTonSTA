package com.supinfo.notetonsta.dao;


import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.MarkCreationException;
import com.supinfo.exception.MarkDeletionException;
import com.supinfo.exception.MarkUpdateException;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;

public interface MarkDao {
	public Mark find(Key markkey);
	public List<Mark> findAll();
	public void addMark(Mark mark) throws MarkCreationException;
	public void removeMark(Mark mark) throws MarkDeletionException;
	public void updateMark(Mark mark) throws MarkUpdateException;
	public Object getStatsForIntervention(Intervention intervention);
}
