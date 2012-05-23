package com.supinfo.notetonsta.jaxrs.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Speaker;

@Path("speaker")
public class SpeakerResource {
	@GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n"
                + "<h1>Speaker Resource.</h1>\n"
                + "</body></html>";
    }
	
	@GET @Path("interventions/{speakerkey}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Intervention> getSpeakerInterventions(@PathParam("speakerkey") String speakerkey){
		return DaoFactory.speakerDao().getInterventions(KeyFactory.stringToKey(speakerkey));
	}
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Speaker getSpeaker(String speakerkey){
		return DaoFactory.speakerDao().find(KeyFactory.stringToKey(speakerkey));
	}	
}
