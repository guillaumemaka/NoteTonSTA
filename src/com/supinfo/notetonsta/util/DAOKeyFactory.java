package com.supinfo.notetonsta.util;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Mark;
import com.supinfo.notetonsta.entity.Speaker;

public final class DAOKeyFactory {
	public static Key SpeakerKeyFactory(Speaker speaker){
		Key k = KeyFactory.createKey(Speaker.class.getSimpleName(), speaker.getEmail());		
		return k;
	}
	
	public static Key CampusKeyFactory(Campus campus){
		Key k = KeyFactory.createKey(Campus.class.getSimpleName(), campus.hashCode());		
		return k;
	}
	
	public static Key MarkKeyFactory(Mark mark){
		Key k = KeyFactory.createKey(Mark.class.getSimpleName(), mark.getIdbooster());		
		return k;
	}
}
