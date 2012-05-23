package com.supinfo.notetonsta.dao.jpa;


import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.SpeakerCreationException;
import com.supinfo.exception.SpeakerDeletionException;
import com.supinfo.notetonsta.dao.SpeakerDao;
import com.supinfo.notetonsta.entity.Speaker;

public class JpaSpeakerDao implements SpeakerDao {
	private EntityManagerFactory emf = null;
	private EntityManager em;
	private final Logger log = Logger.getLogger(JpaSpeakerDao.class.getSimpleName());
	public JpaSpeakerDao(EntityManagerFactory entityManagerFactory) {
		// TODO Auto-generated constructor stub
		this.emf = entityManagerFactory;
		this.em = emf.createEntityManager();
	}

	@Override
	public Speaker find(Key speakerKey) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		Speaker speaker = em.find(Speaker.class, speakerKey); 
		em.getTransaction().commit();
		em.close();
		
		return speaker;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Speaker> findAll() {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		List<Speaker> speakers = em.createQuery("SELECT s FROM Speaker AS s").getResultList();		
		em.getTransaction().commit();
		em.close();
		
		return speakers;
		
	}

	@Override
	public void addSpeaker(Speaker speaker) throws SpeakerCreationException {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			em.persist(speaker);
			em.getTransaction().commit();
			em.close();			
			log.info("Added: " + speaker.getEmail() + ", key: " + speaker.getKey().toString());
		} catch (Exception e) {			
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();				
			}
			
			log.severe(e.getMessage());
			throw new SpeakerCreationException(e.getMessage());
		}finally{
			if(em.isOpen()){
				em.close();
			}
		}
	}

	@Override
	public void removeSpeaker(Speaker speaker) throws SpeakerDeletionException {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		em.remove(em.merge(speaker));
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Speaker updateSpeaker(Speaker speaker) throws SpeakerCreationException {
		try {	
			em.persist(em.merge(speaker));			
			log.info("Added: " + speaker.getEmail() + ", key: " + speaker.getKey().toString());
		} catch (Exception e) {			
			
			log.severe(e.getMessage());
			throw new SpeakerCreationException(e.getMessage());
		}
		
		return speaker;
	}

}
