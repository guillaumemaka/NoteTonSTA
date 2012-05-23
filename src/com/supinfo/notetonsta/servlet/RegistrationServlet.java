package com.supinfo.notetonsta.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.exception.SpeakerCreationException;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Speaker;
import com.supinfo.notetonsta.util.Authentication;

/**
 * Servlet implementation class RegistrationServlet
 */

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/pages/registrations/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Speaker speaker = new Speaker();
		
		speaker.setFirstname(request.getParameter("firstname"));
		speaker.setLastname(request.getParameter("lastname"));
		speaker.setEmail(request.getParameter("email"));
				
		try {
			speaker.setPassword(Authentication.hash(request.getParameter("password"), "UTF-8"));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DaoFactory.speakerDao().addSpeaker(speaker);
			request.setAttribute("success", "You are registered with success, Please Login in the form below");
		} catch (SpeakerCreationException e) {
			// TODO Auto-generated catch block
			request.setAttribute("error", "An error occur");			
		}
				
		request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
	}

}
