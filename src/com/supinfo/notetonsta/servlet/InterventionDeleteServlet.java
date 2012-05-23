package com.supinfo.notetonsta.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Intervention;

/**
 * Servlet implementation class InterventionDeleteServlet
 */

public class InterventionDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InterventionDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));

		try{
			Intervention intervention = DaoFactory.interventionDao().find(id);
			
			DaoFactory.interventionDao().removeIntervention(intervention);
			
			request.setAttribute("success", "Intervention deleted successfully !");
			
		}catch(Exception e){
			request.setAttribute("error", "An error occur durring the intervention deletion proccess: " + e.getMessage());
		}
		
		request.getRequestDispatcher("/interventions/mine").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/interventions/mine").forward(request, response);
	}

}
