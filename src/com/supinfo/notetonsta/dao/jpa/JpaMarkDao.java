package com.supinfo.notetonsta.dao.jpa;

//import java.awt.Color;
//import java.awt.Paint;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.Key;
import com.supinfo.exception.MarkCreationException;
import com.supinfo.exception.MarkDeletionException;
import com.supinfo.exception.MarkUpdateException;
import com.supinfo.notetonsta.dao.MarkDao;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;

public class JpaMarkDao implements MarkDao {
	private static final Logger logger = Logger.getLogger(JpaMarkDao.class
			.getSimpleName());
	private EntityManagerFactory emf = null;
	private EntityManager em;

	// private PieChart2DProperties properties;

	public JpaMarkDao(EntityManagerFactory entityManagerFactory) {
		// TODO Auto-generated constructor stub
		this.emf = entityManagerFactory;
		this.em = emf.createEntityManager();
	}

	@Override
	public Mark find(Key markkey) {
		Mark mark = new Mark();

		try {
			em.getTransaction().begin();
			mark = (Mark) em.createQuery(
					"select m from Mark m where m.key = :markkey")
					.setParameter("markkey", markkey);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING, "find() method throw an exception", e);
		} finally {
			if (em.isOpen())
				em.close();
		}

		return mark;
	}

	@Override
	public void updateMark(Mark mark) throws MarkUpdateException {
		try {
			em.getTransaction().begin();
			em.persist(em.merge(mark));
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING,
					"updateMarkl(Mark) method throw an exception", e);

			throw new MarkUpdateException();
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mark> findAll() {
		// TODO Auto-generated method stub
		List<Mark> marks = new ArrayList<Mark>();
		try {
			em.getTransaction().begin();
			marks = em.createQuery("SELECT m FROM Mark AS m").getResultList();
			em.getTransaction().commit();			
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING, "findAll() method throw an exception", e);
		} finally {
			if (em.isOpen())
				em.close();
		}

		return marks;
	}

	@Override
	public void addMark(Mark mark) throws MarkCreationException {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			em.persist(mark);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING,
					"addMark(Mark) method throw an exception", e);

			throw new MarkCreationException();
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}

	}

	@Override
	public void removeMark(Mark mark) throws MarkDeletionException {
		try {
			em.getTransaction().begin();
			em.remove(em.merge(mark));
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(Level.WARNING,
					"removeMark(Mark) method throw an exception", e);

			throw new MarkDeletionException();
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}

	}

	@Override
	public String[] getStatsForIntervention(Intervention intervention) {
		// TODO Auto-generated method stub
		String[] stats = new String[3];
		DecimalFormat df = new DecimalFormat("#.00");
		Double avgspeaker, avgslide;
		try {
			em.getTransaction().begin();

			avgspeaker = (Double) em
					.createQuery(
							"SELECT AVG(m.speakerMark) " + "FROM Mark AS m "
									+ "WHERE m.intervention.id = :id")
					.setParameter("id", intervention.getId()).getSingleResult();

			avgslide = (Double) em
					.createQuery(
							"SELECT AVG(m.slideMark) " + "FROM Mark AS m "
									+ "WHERE m.intervention.id = :id")
					.setParameter("id", intervention.getId()).getSingleResult();

			em.getTransaction().commit();

			stats[0] = df.format(avgspeaker);
			stats[1] = df.format(avgslide);
			stats[2] = df.format((avgslide + avgspeaker) / 2);
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			logger.log(
					Level.WARNING,
					"getStatsForIntervention(Intervention) method throw an exception",
					e);
		} finally {
			if (em.isOpen())
				em.close();
		}

		return stats;
	}

	// public PieChartDataSet getDataSetChartForIntervention(Intervention
	// intervention) throws ChartDataException{
	// Long countOfMark1, countOfMark2,countOfMark3,countOfMark4,countOfMark5;
	//
	// countOfMark1 =(Long) em.createQuery("SELECT COUNT(m.id) " +
	// "FROM Mark AS m " +
	// "WHERE m.intervention.id =:id " +
	// "AND m.speakerMark >= 1 AND m.speakerMark < 2 " +
	// "AND m.slideMark >= 1 AND m.slideMark < 2")
	// .setParameter("id", intervention.getId())
	// .getSingleResult();
	//
	// countOfMark2 =(Long) em.createQuery("SELECT COUNT(m.id) " +
	// "FROM Mark AS m " +
	// "WHERE m.intervention.id =:id " +
	// "AND m.speakerMark >= 2 AND m.speakerMark < 3 " +
	// "AND m.slideMark >= 2 AND m.slideMark < 3")
	// .setParameter("id", intervention.getId())
	// .getSingleResult();
	//
	// countOfMark3 =(Long) em.createQuery("SELECT COUNT(m.id) " +
	// "FROM Mark AS m " +
	// "WHERE m.intervention.id =:id " +
	// "AND m.speakerMark >= 3 AND m.speakerMark < 4 " +
	// "AND m.slideMark >= 3 AND m.slideMark < 4")
	// .setParameter("id", intervention.getId())
	// .getSingleResult();
	//
	// countOfMark4 =(Long) em.createQuery("SELECT COUNT(m.id) " +
	// "FROM Mark AS m " +
	// "WHERE m.intervention.id =:id " +
	// "AND m.speakerMark >= 4 AND m.speakerMark < 5 " +
	// "AND m.slideMark >= 4 AND m.slideMark < 5")
	// .setParameter("id", intervention.getId())
	// .getSingleResult();
	//
	// countOfMark5 =(Long) em.createQuery("SELECT COUNT(m.id) " +
	// "FROM Mark AS m " +
	// "WHERE m.intervention.id =:id " +
	// "AND m.speakerMark = 5 " +
	// "AND m.slideMark = 5")
	// .setParameter("id", intervention.getId())
	// .getSingleResult();
	//
	// Long total = countOfMark1 + countOfMark2 + countOfMark3 + countOfMark4 +
	// countOfMark5;
	//
	// double[] data = new double[]{
	// this.toPercent(countOfMark1,total),
	// this.toPercent(countOfMark2,total),
	// this.toPercent(countOfMark3,total),
	// this.toPercent(countOfMark4,total),
	// this.toPercent(countOfMark5,total)
	// };
	//
	// DecimalFormat df = new DecimalFormat("###.00");
	// Paint[] paints = new Paint[]{Color.blue, Color.red, Color.green,
	// Color.yellow, Color.white};
	// String[] labels = {
	// "1 " + df.format(data[0]) + "%" ,
	// "2 " + df.format(data[1]) + "%",
	// "3 " + df.format(data[2]) + "%",
	// "4 " + df.format(data[3]) + "%",
	// "5 " + df.format(data[4]) + "%"};
	// this.properties = new PieChart2DProperties();
	// this.properties.setPieLabelType(PieLabelType.VALUE_LABELS);
	// return new PieChartDataSet( "Marks Chart", data, labels, paints,
	// this.properties );
	// }

	public double toPercent(Object v, Object t) {
		double value = Double.parseDouble(v.toString());
		double total = Double.parseDouble(t.toString());
		return (value / total) * 100;
	}
}
