package com.supinfo.notetonsta.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.CampusCreationExecption;
import com.supinfo.exception.CampusDeletionExecption;
import com.supinfo.notetonsta.dao.CampusDao;
import com.supinfo.notetonsta.entity.Campus;

public class JpaCampusDao implements CampusDao {
	private EntityManagerFactory emf = null;
	private EntityManager em;

	public JpaCampusDao(EntityManagerFactory entityManagerFactory) {
		// TODO Auto-generated constructor stub
		this.emf = entityManagerFactory;
		this.em = emf.createEntityManager();
	}

	@Override
	public Campus find(Key campusKey) {
		em.getTransaction().begin();
		Campus campus = em.find(Campus.class, campusKey);
		em.getTransaction().commit();
		em.close();
		return campus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Campus> findAll() {
		em.getTransaction().begin();
		List<Campus> camps = em.createQuery("SELECT c FROM Campus AS c")
				.getResultList();
		em.getTransaction().commit();
		em.close();
		return camps;
	}

	@Override
	public void addCampus(Campus campus) throws CampusCreationExecption {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			em.persist(campus);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new CampusCreationExecption("Campus creaton failed: "
					+ campus.getName());
		}finally{
			if(em.isOpen()){
				em.clear();
			}
				
		}
	}

	@Override
	public void removeCampus(Campus campus) throws CampusDeletionExecption {
		try {
			em.getTransaction().begin();
			em.remove(em.merge(campus));
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new CampusDeletionExecption("Campus creaton failed: "
					+ campus.getName());
		}finally{
			if(em.isOpen()){
				em.clear();
			}
				
		}
	}

}
