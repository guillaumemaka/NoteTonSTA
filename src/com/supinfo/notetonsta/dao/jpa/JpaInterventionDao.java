package com.supinfo.notetonsta.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.supinfo.exception.InterventionCreationException;
import com.supinfo.exception.InterventionDeletionException;
import com.supinfo.exception.InterventionUpdateException;
import com.supinfo.notetonsta.dao.InterventionDao;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;
import com.supinfo.notetonsta.entity.Speaker;


public class JpaInterventionDao implements InterventionDao {
	private static final Logger logger = Logger.getLogger(JpaInterventionDao.class.getSimpleName());
	private EntityManagerFactory emf = null;
	private EntityManager em;

	public JpaInterventionDao(EntityManagerFactory entityManagerFactory) {
		// TODO Auto-generated constructor stub
		this.emf = entityManagerFactory;
		this.em = emf.createEntityManager();
	}

	@Override
	public Intervention find(Long intervention_id) {
		Intervention intervention = new Intervention();
		try {
			em.getTransaction().begin();
			intervention = em
					.find(Intervention.class, intervention_id);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			
			logger.log(Level.WARNING, "find() method throw an exception", e);			
		} finally {
			if(em.isOpen()) em.close();
		}

		
		return intervention;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervention> findAll() {
		// TODO Auto-generated method stub
		List<Intervention> interventions = new ArrayList<Intervention>();
		
		try {
			em.getTransaction().begin();
			
			interventions = em.createQuery(
					"SELECT i FROM Intervention AS i").getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			
			logger.log(Level.WARNING, "findAll() method throw an exception", e);
		} finally {
			if(em.isOpen()) em.close();
		}
		

		return interventions;
	}

	@Override
	public void addIntervention(Intervention intervention)
			throws InterventionCreationException {
		try {
			em.getTransaction().begin();
			em.persist(intervention);
			em.getTransaction().commit();
			em.close();

			Speaker speaker = DaoFactory.speakerDao().find(
					intervention.getSpeakerKey());
			Campus campus = DaoFactory.campusDao().find(
					intervention.getCampusKey());
			
			
			speaker.addInterventionId(intervention.getId());
			campus.addInterventionId(intervention.getId());

			DaoFactory.speakerDao().updateSpeaker(speaker);
			DaoFactory.campusDao().updateCampus(campus);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			
			logger.log(Level.WARNING, "addIntervention(Intervention) method throw an exception", e);

			throw new InterventionCreationException();
		} finally {
			if (em.isOpen())
				em.close();
		}
	}

	@Override
	public void removeIntervention(Intervention intervention)
			throws InterventionDeletionException {

		try {

			em.getTransaction().begin();

			em.persist(em.merge(intervention));
			em.remove(em.merge(intervention));

			em.getTransaction().commit();
			
			// Remove the intervention ID in both campus / speaker related pbject
			Speaker speaker = DaoFactory.speakerDao().find(
					intervention.getSpeakerKey());
			Campus campus = DaoFactory.campusDao().find(
					intervention.getCampusKey());

			speaker.removeInterventionId(intervention.getId());
			campus.removeInterventionId(intervention.getId());

			DaoFactory.speakerDao().updateSpeaker(speaker);
			DaoFactory.campusDao().updateCampus(campus);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			
			logger.log(Level.WARNING, "removeIntervention(Intervention) method throw an exception", e);
			
			throw new InterventionDeletionException();
		} finally {
			if (em.isOpen())
				em.close();
		}
	}

	@Override
	public void updateIntervention(Intervention intervention)
			throws InterventionUpdateException {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();

			em.merge(intervention);

			em.getTransaction().commit();
		} catch (Exception e) {	
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			
			logger.log(Level.WARNING, "updateIntervention() method throw an exception", e);
			
			throw new InterventionUpdateException();
		} finally {
			if (em.isOpen())
				em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mark> getMarks(Long intervention_id) {
		List<Mark> marks = new ArrayList<Mark>();

		try {
			em.getTransaction().begin();
			marks = em
					.createQuery(
							"select m from Mark m where m.interventionId = :intervention_id")
					.setParameter("intervention_id", intervention_id)
					.getResultList();
			
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();

			logger.log(Level.WARNING, "getMatks() method throw an exception", e);
		} finally {
			if (em.isOpen()) em.close();
		}

		return marks;
	}
}
