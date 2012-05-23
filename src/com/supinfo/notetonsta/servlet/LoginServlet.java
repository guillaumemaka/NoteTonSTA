package com.supinfo.notetonsta.servlet;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.notetonsta.util.Authentication;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Authentication auth = new Authentication()
		.setCredentials(
				request.getParameter("email"),
				request.getParameter("password"));
		
		String message = null;
		
		try {
			switch(auth.authenticate()){
			case PasswordMissMatch:
				message = "Password missmatch";
				request.setAttribute("error", message);
				this.doGet(request, response);
				break;
			case Success:
				message = "Your are successfully logged in";
				request.setAttribute("success", message);
				request.getSession().setAttribute("loggedIn", true);
				request.getSession().setAttribute("full_name", auth.getUserFullName());
				request.getSession().setAttribute("user_id", auth.getUserId());
				request.getRequestDispatcher("/home").forward(request, response);
				break;
			case UserNotFound:
				message = "Username provided not found in our record";
				request.setAttribute("error", message);
				this.doGet(request, response);
				break;		
			}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			message = e.getMessage();
			request.setAttribute("error", message);
			this.doGet(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
