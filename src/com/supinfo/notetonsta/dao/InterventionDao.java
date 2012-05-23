package com.supinfo.notetonsta.dao;


import java.util.List;

import com.supinfo.exception.InterventionCreationException;
import com.supinfo.exception.InterventionDeletionException;
import com.supinfo.exception.InterventionUpdateException;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;

public interface InterventionDao {
	public Intervention find(Long intervention_id);
	public List<Intervention> findAll();
	public List<Mark> getMarks(Long  intervention_id);
	public void addIntervention(Intervention intervention) throws InterventionCreationException;
	public void removeIntervention(Intervention intervention) throws InterventionDeletionException;
	public void updateIntervention(Intervention intervention) throws InterventionUpdateException;	
}
