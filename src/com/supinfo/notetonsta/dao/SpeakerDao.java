package com.supinfo.notetonsta.dao;


import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.SpeakerCreationException;
import com.supinfo.exception.SpeakerDeletionException;
import com.supinfo.exception.SpeakerUpdateException;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Speaker;

public interface SpeakerDao {
	public Speaker find(Key speakerkey);
	public List<Speaker> findAll();
	public List<Intervention> getInterventions(Key speakerkey);
	public Boolean checkToken(String token);
	public void addSpeaker(Speaker speaker) throws SpeakerCreationException;
	public void removeSpeaker(Speaker speaker) throws SpeakerDeletionException;
	public void updateSpeaker(Speaker speaker) throws SpeakerUpdateException;
}
