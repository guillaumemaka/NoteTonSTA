package com.supinfo.notetonsta.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Intervention;

/**
 * Servlet implementation class InterventionShowServlet
 */

public class InterventionShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InterventionShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));

		Intervention intervention = DaoFactory.interventionDao().find(id);
		
		request.setAttribute("marks_avg", DaoFactory.markDao().getStatsForIntervention(intervention));
		request.setAttribute("back_url", this.backUrl(request));
		request.setAttribute("intervention", intervention);
		request.setAttribute("campus", DaoFactory.campusDao().find(intervention.getCampus()));
		request.setAttribute("speaker", DaoFactory.speakerDao().find(intervention.getSpeaker()));
		
		request.getRequestDispatcher("/pages/interventions/show.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private String backUrl(HttpServletRequest req){
		return req.getHeader("referer");
	}
}
