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
import com.supinfo.exception.InterventionCreationException;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Speaker;

/**
 * Servlet implementation class InterventionCreateServlet
 */
public class InterventionCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(InterventionCreateServlet.class
			.getSimpleName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InterventionCreateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Intervention intervention = new Intervention();

		request.setAttribute("campus", DaoFactory.campusDao().findAll());

		request.setAttribute("intervention", intervention);

		request.getRequestDispatcher("/pages/interventions/new.jsp").forward(
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
		Key interventionKey = KeyFactory.createKey(
				Intervention.class.getSimpleName(), intervention_id);
		intervention.setKey(interventionKey);

		Key campusKey = KeyFactory.createKey(Campus.class.getSimpleName(),
				Long.parseLong(request.getParameter("campus_id")));
		log.info("Campus key: " + campusKey.toString());
		intervention.setCampus(campusKey);

		Key speakerKey = KeyFactory.createKey(Speaker.class.getSimpleName(),
				(Long) request.getSession().getAttribute("user_id"));
		log.info("Speaker key: " + speakerKey.toString());
		intervention.setSpeaker(speakerKey);

		Long to = Date.parse(request.getParameter("to"));
		Long from = Date.parse(request.getParameter("from"));
		
		intervention.setSubject(request.getParameter("subject"));
		intervention.setEndDate(new Date(to));
		intervention.setStartDate(new Date(from));
		intervention.setDescription(request.getParameter("description"));

		try {
			DaoFactory.interventionDao().addIntervention(intervention);
			request.setAttribute("success", "Intervention created");
			request.setAttribute("intervention", intervention);
			request.setAttribute("campus", DaoFactory.campusDao().find(campusKey));
			request.setAttribute("speaker", DaoFactory.speakerDao().find(speakerKey));
			request.getRequestDispatcher("/pages/interventions/show.jsp")
					.forward(request, response);
		} catch (InterventionCreationException e) {
			request.setAttribute(
					"error",
					"An error occcur during intervention creation: "
							+ e.getMessage());

			request.setAttribute("intervention", intervention);
			request.getRequestDispatcher("/pages/interventions/new.jsp")
					.forward(request, response);
		}

	}

}
