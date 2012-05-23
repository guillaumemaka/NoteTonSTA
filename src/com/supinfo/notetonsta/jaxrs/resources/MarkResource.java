package com.supinfo.notetonsta.jaxrs.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Mark;

@Path("mark")
public class MarkResource {
	@GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n"
                + "<h1>Mark Resource.</h1>\n"
                + "</body></html>";
    }
	
	@GET @Path("{key}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Mark getMark(@PathParam("key") String markkey){
		return DaoFactory.markDao().find(KeyFactory.stringToKey(markkey));
	}
	
	@GET @Path("intervention/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mark> getMarkForIntervention(@PathParam("id") Long id){
		return DaoFactory.interventionDao().getMarks(id);
	}
}
