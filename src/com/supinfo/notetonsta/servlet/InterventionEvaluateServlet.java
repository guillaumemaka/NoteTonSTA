package com.supinfo.notetonsta.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;

/**
 * Servlet implementation class InterventionEvaluateServlet
 */

public class InterventionEvaluateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InterventionEvaluateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Mark mark = new Mark();
		Intervention intervention = new Intervention();
		double speakerMark; 
		int q1, q2, q3;
		double slideMark;
		int q4, q5, q6; 
		String idbooster, coment;
		Long intervention_id;
		
		
		intervention_id = Long.parseLong(request.getParameter("intervention_id"));
		intervention = DaoFactory.interventionDao().find(intervention_id);
		
		idbooster = request.getParameter("idbooster");
		q1 = Integer.parseInt(request.getParameter("q1"));
		q2 = Integer.parseInt(request.getParameter("q2"));
		q3 = Integer.parseInt(request.getParameter("q3"));
		q4 = Integer.parseInt(request.getParameter("q4"));
		q5 = Integer.parseInt(request.getParameter("q5"));
		q6 = Integer.parseInt(request.getParameter("q6"));
		coment = request.getParameter("coment");
		
		speakerMark =	(q1 + q2 + q3) / 3;
		slideMark 	=	(q4 + q5 + q6) / 3;
				
		mark.setIntervention(intervention.getKey());
		mark.setIdbooster(idbooster);
		mark.setSpeakerMark(speakerMark);
		mark.setSlideMark(slideMark);
		mark.setComent(coment);
		
		try{
			DaoFactory.markDao().addMark(mark);			
			intervention.addMark(mark);			
			intervention = DaoFactory.interventionDao().find(intervention.getKey().getId());
			request.setAttribute("marks_avg", DaoFactory.markDao().getStatsForIntervention(intervention));
			request.setAttribute("intervention", DaoFactory.interventionDao().find(intervention_id));
			request.setAttribute("campus", DaoFactory.campusDao().find(intervention.getCampus()));
			request.setAttribute("speaker", DaoFactory.speakerDao().find(intervention.getSpeaker()));
			request.setAttribute("marks_avg", DaoFactory.markDao()
					.getStatsForIntervention(intervention));
			request.setAttribute("intervention", intervention);
			request.setAttribute("success","Evaluation completed successfully !");
		}catch(Exception e){
			request.setAttribute("error","An error occur, during proccessing your evaluation, please try again later !");
		}
		
		request.getRequestDispatcher("/pages/interventions/show.jsp").forward(request, response);
	}
}
