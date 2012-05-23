package com.supinfo.notetonsta.dao.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.CampusCreationExecption;
import com.supinfo.exception.CampusDeletionException;
import com.supinfo.exception.CampusUpdateException;
import com.supinfo.notetonsta.dao.CampusDao;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;

public class JpaCampusDao implements CampusDao {
	private static final Logger log = Logger.getLogger(JpaCampusDao.class.getSimpleName());
	private static final String CAMPUS_LIST_CACHE_KEY = "campus_list";
	private EntityManagerFactory emf = null;
	private EntityManager em;
	private Cache cache;
	
	public JpaCampusDao(EntityManagerFactory entityManagerFactory) {
		// TODO Auto-generated constructor stub
		this.emf = entityManagerFactory;
		this.em = emf.createEntityManager();
		try {
			cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
		} catch (CacheException e) {
			log.warning(e.getMessage());
		}
	}

	@Override
	public Campus find(Key campuskey) {
		// TODO Auto-generated method stub
		Campus campus = null;
		
		try {
			em.getTransaction().begin();
			campus = (Campus) em.createQuery("select c from Campus where c.key = :keyParam")
					.setParameter("keyParam", campuskey).getSingleResult();				
			em.getTransaction().commit();
		} catch (Exception e) {
			log.log(Level.WARNING, "find() method throw an exception", e);
		} finally {
			if(em.isOpen()) em.close();
		}
		
		return campus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Campus> findAll() {
		// TODO Auto-generated method stub
		List<Campus> camps = new ArrayList<Campus>();
				
		try {
			em.getTransaction().begin();		
			camps = (List<Campus>) em.createQuery("SELECT FROM Campus").getResultList();
			em.getTransaction().commit();			
					
		} catch (Exception e) {
			log.warning("findAll() method throw an exception " + e.getMessage());
		} finally {
			if(em.isOpen()) em.close();
		}
		
		return camps;
	}

	@Override
	public void addCampus(Campus campus) throws CampusCreationExecption {
		
		try {
			em.getTransaction().begin();
			em.persist(campus);
			em.getTransaction().commit();			
		} catch (Exception e) {
			if (em.getTransaction().isActive()){
				em.getTransaction().rollback();				
			}
			
			log.log(Level.WARNING, "addCampus() method throw an exception", e);
			
			throw new CampusCreationExecption();
		} finally {
			if (em.isOpen()){
				em.close();
			}
		}
	}

	@Override
	public void removeCampus(Campus campus) throws CampusDeletionException {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			em.remove(em.merge(campus));
			em.getTransaction().commit();			
		} catch (Exception e) {
			if(em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			
			log.log(Level.WARNING, "removeCampus() method throw an exception", e);
			
			throw new CampusDeletionException();
		} finally{
			if (em.isOpen()){
				em.close();
			}
		}
	}

	@Override
	public void updateCampus(Campus campus) throws CampusUpdateException {
		try {
			em.getTransaction().begin();		
			em.persist(em.merge(campus));		
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			log.log(Level.WARNING, "updateCampus() method throw an exception", e);
			
			throw new CampusUpdateException();
		}finally{
			if(em.isOpen()) {em.close() ; }
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Intervention> getInterventions(Key campusKey) {
		
		log.log(Level.INFO, "getInteventions(Key campusKey) , campusKey -> ", campusKey);
		
		List<Intervention> interventions = new ArrayList<Intervention>();
		
		try {
			em.getTransaction().begin();
			
			interventions = em.createQuery("select i from Intervention i where i.campusKey = :campusKey")
					.setParameter("campusKey", campusKey).getResultList();
			
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			
			log.log(Level.WARNING, "getInterventions(Key campusKey) method throw an exception", e);
			
		} finally {
			if (em.isOpen()) em.close();
		}
		
		return interventions;
	}

}
