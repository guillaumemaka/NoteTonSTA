package com.supinfo.notetonsta.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.exception.InterventionCreationException;
import com.supinfo.exception.InterventionDeletionException;
import com.supinfo.exception.InterventionUpdateException;
import com.supinfo.notetonsta.dao.InterventionDao;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Speaker;

public class JpaInterventionDao implements InterventionDao {
	private EntityManagerFactory emf = null;
	private EntityManager em;
	private final Logger log = Logger.getLogger(JpaInterventionDao.class
			.getSimpleName());

	public JpaInterventionDao(EntityManagerFactory entityManagerFactory) {
		this.emf = entityManagerFactory;		
		this.em = emf.createEntityManager();
	}

	@Override
	public Intervention find(Long interventionId) {
		Key interventionKey = KeyFactory.createKey(
				Intervention.class.getSimpleName(), interventionId);
		em.getTransaction().begin();
		Intervention intervention = em
				.find(Intervention.class, interventionKey);
		em.getTransaction().commit();
		em.close();
		return intervention;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervention> findAll() {
		em.getTransaction().begin();
		List<Intervention> interventions = em.createQuery(
				"SELECT i FROM Intervention AS i").getResultList();
		em.getTransaction().commit();
		em.close();
		return interventions;
	}

	@Override
	public Intervention addIntervention(Intervention intervention)
			throws InterventionCreationException {

		try {
			em.getTransaction().begin();
			em.persist(intervention);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.severe(e.getMessage());
			throw new InterventionCreationException(e.getMessage());

		}finally{
			if(em.isOpen()){
				em.close();
			}
		}
		return intervention;
	}

	@Override
	public void removeIntervention(Intervention intervention)
			throws InterventionDeletionException {
		try {
			em.getTransaction().begin();
			em.remove(em.merge(intervention));
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.severe(e.getMessage());
			throw new InterventionDeletionException(e.getMessage());
		}finally{
			if(em.isOpen()){
				em.close();				
			}			
		}

	}

	@Override
	public Intervention updateIntervention(Intervention intervention)
			throws InterventionUpdateException {

		try {
			em.getTransaction().begin();
			em.persist(em.merge(intervention));
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.severe(e.getMessage());
			throw new InterventionUpdateException("Update failed");
		}finally{
			if(em.isOpen()){
				em.close();
			}
		}
		
		return intervention;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervention> findByCampus(Campus campus) {
		List<Intervention> interventions = null;
		em.getTransaction().begin();
		interventions = em
				.createQuery(
						"SELECT I FROM Intervention I JOIN I.campusAlias c WHERE c.key = :campus")
				.setParameter("campus", campus.getKey()).getResultList();
		em.getTransaction().commit();
		em.close();
		if (interventions == null) {
			interventions = new ArrayList<Intervention>();
		}

		return interventions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervention> findBySpeaker(Speaker speaker) {
		List<Intervention> interventions = null;
		em.getTransaction().begin();
		interventions = em
				.createQuery(
						"SELECT I FROM Intervention I JOIN I.speakerAlias S WHERE S.key = :speaker")
				.setParameter("speaker", speaker.getKey()).getResultList();
		em.getTransaction().commit();
		em.close();
		if (interventions == null) {
			interventions = new ArrayList<Intervention>();
		}

		return interventions;
	}
}
