package com.supinfo.notetonsta.dao.jpa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.MarkCreationException;
import com.supinfo.notetonsta.dao.MarkDao;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;
import com.supinfo.notetonsta.util.Rating;

public class JpaMarkDao implements MarkDao {
	private EntityManagerFactory emf = null;
	private EntityManager em;
	private final Logger log = Logger.getLogger(JpaMarkDao.class
			.getSimpleName());

	// private PieChart2DProperties properties;

	public JpaMarkDao(EntityManagerFactory entityManagerFactory) {
		// TODO Auto-generated constructor stub
		this.emf = entityManagerFactory;
		this.em = emf.createEntityManager();
	}

	@Override
	public Mark find(Key markKey) {
		Mark mark = null;
		try {
			em.getTransaction().begin();
			mark = em.find(Mark.class, markKey);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.severe(e.getMessage());
		}finally{
			if(em.isOpen()){
				em.close();
			}
		}
		

		return mark;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mark> findAll() {
		
		List<Mark> marks = new ArrayList<Mark>();
		try {
			em.getTransaction().begin();
			marks = em.createQuery("SELECT m FROM Mark").getResultList();
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.severe(e.getMessage());
		}finally{
			if(em.isOpen()){
				em.close();
			}
		}
		return marks;
	}

	@Override
	public Mark addMark(Mark mark) throws MarkCreationException {
		Intervention intervention = DaoFactory.interventionDao().find(mark.getIntervention().getId());
		try {			
			em.getTransaction().begin();
			em.persist(mark);
			em.getTransaction().commit();			
			em.close();
			intervention.addMark(mark);
			DaoFactory.interventionDao().updateIntervention(intervention);
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.severe(e.getMessage());
		}finally{
			if(em.isOpen()){
				em.close();
			}
		}

		return mark;
	}

	@Override
	public void removeMark(Mark mark) {
		try {
			em.getTransaction().begin();
			em.remove(mark);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.severe(e.getMessage());
		}finally{
			if(em.isOpen()){
				em.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Rating getStatsForIntervention(
			Intervention intervention) {
		List<Mark> marks = null;
		Rating rating = new Rating();

		Double avgspeaker = new Double(0), avgslide = new Double(0), globalavg =new Double(0);
		
		try {
			em.getTransaction().begin();
			marks = em
					.createQuery(
							"SELECT m FROM Mark m "
									+ "WHERE m.intervention = :key")
					.setParameter("key", intervention.getKey()).getResultList();
			em.getTransaction().commit();
			em.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			log.severe(e.getMessage());
		} finally {
			if (em.isOpen())
				em.close();
		}
		
		if (marks != null){
			for (Mark mark : marks) {
				avgspeaker += mark.getSpeakerMark();
				avgslide += mark.getSlideMark();
			}	
			
			avgspeaker = avgspeaker / marks.size();
			avgslide = avgslide / marks.size();
			globalavg = (avgslide + avgspeaker) / 2;
		}
		
		rating.setAvgSpeaker(avgspeaker);
		rating.setAvgSlide(avgslide);
		rating.setGlobalAvg(globalavg);
		
		
		return rating;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Double> getDataChartForIntervention(
			Intervention intervention) {
		int countOfMark1 = 0, countOfMark2 = 0, countOfMark3 = 0, countOfMark4 = 0, countOfMark5 = 0;
		HashMap<String, Double> data = new HashMap<String, Double>();
		List<Mark> marks;
		em.getTransaction().begin();
		marks = new ArrayList<Mark>(em
				.createQuery(
						"SELECT m FROM Mark AS m WHERE m.intervention =:id ")
				.setParameter("id", intervention.getKey()).getResultList());

		em.getTransaction().commit();
		em.close();

		for (Mark mark : marks) {
			if (mark.getSlideMark() >= 1 && mark.getSlideMark() < 2
					|| mark.getSlideMark() >= 1 && mark.getSlideMark() < 2) {
				countOfMark1++;
			}

			if (mark.getSlideMark() >= 2 && mark.getSlideMark() < 3
					|| mark.getSlideMark() >= 2 && mark.getSlideMark() < 3) {
				countOfMark2++;
			}

			if (mark.getSlideMark() >= 3 && mark.getSlideMark() < 4
					|| mark.getSlideMark() >= 3 && mark.getSlideMark() < 4) {
				countOfMark3++;
			}

			if (mark.getSlideMark() >= 4 && mark.getSlideMark() < 5
					|| mark.getSlideMark() >= 4 && mark.getSlideMark() < 5) {
				countOfMark4++;
			}

			if (mark.getSlideMark() == 5 || mark.getSlideMark() == 5) {
				countOfMark5++;
			}
		}
		DecimalFormat df = new DecimalFormat("###.00");
		int total = countOfMark1 + countOfMark2 + countOfMark3 + countOfMark4
				+ countOfMark5;
		
		data.put("1 " + df.format(countOfMark1) + "%", this.toPercent(countOfMark1, total));
		data.put("2 " + df.format(countOfMark2) + "%", this.toPercent(countOfMark2, total));
		data.put("3 " + df.format(countOfMark3) + "%", this.toPercent(countOfMark3, total));
		data.put("4 " + df.format(countOfMark4) + "%", this.toPercent(countOfMark4, total));
		data.put("5 " + df.format(countOfMark5) + "%", this.toPercent(countOfMark5, total));
		
		return data;
	}

	public double toPercent(Object v, Object t) {
		double value = Double.parseDouble(v.toString());
		double total = Double.parseDouble(t.toString());
		return (value / total) * 100;
	}
}
