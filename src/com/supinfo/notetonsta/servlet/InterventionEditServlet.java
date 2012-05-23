package com.supinfo.notetonsta.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Speaker;

/**
 * Servlet implementation class InterventionEditServlet
 */

public class InterventionEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(InterventionEditServlet.class
			.getSimpleName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InterventionEditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Intervention intervention = DaoFactory.interventionDao().find(
				Long.parseLong(request.getParameter("id")));

		request.setAttribute("campus", DaoFactory.campusDao().findAll());
		request.setAttribute("intervention", intervention);
		request.getRequestDispatcher("/pages/interventions/edit.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Intervention intervention = new Intervention();
		Long intervention_id = Long.parseLong(request
				.getParameter("intervention_id"));
		
		intervention = DaoFactory.interventionDao().find(intervention_id);

		Key campusKey = KeyFactory.createKey(Campus.class.getSimpleName(),
				Long.parseLong(request.getParameter("campus_id")));
		log.info("Campus key: " + campusKey.toString());
		intervention.setCampus(DaoFactory.campusDao().find(campusKey).getKey());

		Key speakerKey = KeyFactory.createKey(Speaker.class.getSimpleName(),
				(Long) 
				request.getSession().getAttribute("user_id"));
		log.info("Speaker key: " + speakerKey.toString());
		intervention.setSpeaker(speakerKey);

		Long to = Date.parse(request.getParameter("to"));
		Long from = Date.parse(request.getParameter("from"));
		
		intervention.setSubject(request.getParameter("subject"));
		intervention.setEndDate(new Date(to));
		intervention.setStartDate(new Date(from));
		intervention.setDescription(request.getParameter("description"));

		try {
			intervention = DaoFactory.interventionDao().updateIntervention(intervention);
			request.setAttribute("success", "Intervention updated");								
		} catch (Exception e) {
			request.setAttribute("error",
					"An error occcur during intervention update");			
		}

		request.setAttribute("campus", DaoFactory.campusDao().find(campusKey));
		request.setAttribute("speaker", DaoFactory.speakerDao().find(speakerKey));
		request.setAttribute("marks_avg", DaoFactory.markDao()
				.getStatsForIntervention(intervention));
		request.setAttribute("intervention", intervention);
		request.getRequestDispatcher("/pages/interventions/show.jsp").forward(
				request, response);
	}
}
