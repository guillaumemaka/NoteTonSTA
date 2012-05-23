package com.supinfo.notetonsta.dao;

import java.util.List;

import com.supinfo.exception.InterventionCreationException;
import com.supinfo.exception.InterventionDeletionException;
import com.supinfo.exception.InterventionUpdateException;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Speaker;

public interface InterventionDao {
	public Intervention find(Long interventionId);

	public List<Intervention> findAll();

	public List<Intervention> findByCampus(Campus campus);

	public List<Intervention> findBySpeaker(Speaker speaker);

	public Intervention addIntervention(Intervention intervention)
			throws InterventionCreationException;

	public void removeIntervention(Intervention intervention)
			throws InterventionDeletionException;

	public Intervention updateIntervention(Intervention intervention)
			throws InterventionUpdateException;
}
