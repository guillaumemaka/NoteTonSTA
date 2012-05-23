package com.supinfo.notetonsta.dao.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.SpeakerCreationException;
import com.supinfo.exception.SpeakerDeletionException;
import com.supinfo.exception.SpeakerUpdateException;
import com.supinfo.notetonsta.dao.SpeakerDao;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Speaker;

public class JpaSpeakerDao implements SpeakerDao {
	private static final Logger logger = Logger.getLogger(JpaSpeakerDao.class
			.getSimpleName());
	private EntityManagerFactory emf = null;
	private EntityManager em;

	public JpaSpeakerDao(EntityManagerFactory entityManagerFactory) {
		// TODO Auto-generated constructor stub
		this.emf = entityManagerFactory;
		this.em = emf.createEntityManager();
	}

	@Override
	public Speaker find(Key speakerkey) {

		Speaker speaker = null;
		try {
			em.getTransaction().begin();
			speaker = (Speaker) em
					.createQuery(
							"select s from Speaker s where s.key = :speakerkey")
					.setParameter("speakerkey", speakerkey).getSingleResult();
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING, "find() method throw an exception", e);
		} finally {
			if (em.isOpen())
				em.close();
		}
		return speaker;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Speaker> findAll() {

		List<Speaker> speakers = null;
		try {
			em.getTransaction().begin();
			speakers = em.createQuery("SELECT s FROM Speaker AS s")
					.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING, "findAll() method throw an exception", e);
		} finally {
			if (em.isOpen())
				em.close();
		}

		return speakers;
	}

	@Override
	public void addSpeaker(Speaker speaker) throws SpeakerCreationException {
		try {
			em.getTransaction().begin();
			em.persist(speaker);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING,
					"addSpeaker(Speaker) method throw an exception", e);
			
			throw new SpeakerCreationException();
		} finally {
			if (em.isOpen())
				em.close();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervention> getInterventions(Key speakerkey) {
		List<Intervention> interventions = null;

		interventions = em
				.createQuery(
						"select i from Intervention i where i.speakerKey = :speakerkey")
				.setParameter("speakerkey", speakerkey).getResultList();

		return interventions;
	}

	@Override
	public void updateSpeaker(Speaker speaker) throws SpeakerUpdateException {

		try {
			em.getTransaction().begin();
			em.persist(em.merge(speaker));
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}

			throw new SpeakerUpdateException();
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}

	}

	
	@Override
	public Boolean checkToken(String token) {
		try {
			em.getTransaction().begin();
			
			Speaker speaker = (Speaker) em.createQuery("select s from Speaker as s where s.token = :token").setParameter("token", token).getSingleResult();
			
			if(speaker == null){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			if(em.getTransaction().isActive()) em.getTransaction().rollback();
			logger.warning(e.getMessage());
			return false;
		}finally{
			if (em.isOpen()) em.close();
		}
	}
}
