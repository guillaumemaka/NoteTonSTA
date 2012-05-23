package com.supinfo.notetonsta.jaxrs.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.supinfo.exception.SpeakerCreationException;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Speaker;
import com.supinfo.notetonsta.util.Status;

@Path("register")
public class RegistrationResource {
	@GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n"
                + "<h1>Registration Resource.</h1>\n"
                + "</body></html>";
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status register(Speaker speaker){
		try {
			DaoFactory.speakerDao().addSpeaker(speaker);		
		} catch (SpeakerCreationException e) {
			return Status.CREATION_FAIL;
		}
		return Status.OK;
	}
}
