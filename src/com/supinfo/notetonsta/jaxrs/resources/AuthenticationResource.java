package com.supinfo.notetonsta.jaxrs.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.restlet.data.Form;

import com.supinfo.notetonsta.util.Authentication;
import com.supinfo.notetonsta.util.AuthenticationError;

@Path("auth")
public class AuthenticationResource {
	@GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n"
                + "<h1>Authentication Resource.</h1>\n"
                + "</body></html>";
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Form authenticate(Form auth_data){
		Form response = new Form();
		Authentication auth = new Authentication();
		auth.setCredentials(auth_data.getValues("username"), auth_data.getValues("password"));
		
		try {
			switch(auth.authenticate()){
			case Success : 
				response.add("token", Authentication.generateToken(auth_data.getValues("username")));				
				break;
			case PasswordMissMatch :
				response.add("error", AuthenticationError.PasswordMissMatch.name());
				break;
			case UserNotFound :
				response.add("error", AuthenticationError.UserNotFound.name());
				break;
			case UnknownError :
				response.add("error", AuthenticationError.UnknownError.name());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.add("exception", e.getMessage());
		}
		
		return response;
	}
	
	@POST @Path("token")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String token(Form auth_data){
		return null;		
	}
	
	@POST @Path("checktoken")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Form checkToken(Form data){
		Form response = new Form();
		response.add("isValid","VALID");
		return response;
	}
}
