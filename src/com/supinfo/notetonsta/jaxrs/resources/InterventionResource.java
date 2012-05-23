package com.supinfo.notetonsta.jaxrs.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.supinfo.exception.InterventionCreationException;
import com.supinfo.exception.InterventionDeletionException;
import com.supinfo.exception.InterventionUpdateException;
import com.supinfo.exception.MarkCreationException;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;
import com.supinfo.notetonsta.util.Status;

@Path("intervention")
public class InterventionResource {
	@GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n"
                + "<h1>Intervention Resource.</h1>\n"
                + "</body></html>";
    }
	
	@GET @Path("findall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Intervention> getAllInterventions(){
		return DaoFactory.interventionDao().findAll();
	}
	
	@GET @Path("find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Intervention getIntervention(@PathParam("id") Long intervention_id){
		return DaoFactory.interventionDao().find(intervention_id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status create(Intervention intervention){
		try {
			DaoFactory.interventionDao().addIntervention(intervention);
		} catch (InterventionCreationException e) {
			return Status.CREATION_FAIL;
		}
		
		return Status.OK;
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status delete(Intervention intervention){
		try {
			DaoFactory.interventionDao().removeIntervention(intervention);
		} catch (InterventionDeletionException e) {
			return Status.DELETION_FAIL;
		}
		
		return Status.OK;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status update(Intervention intervention){
		try {
			DaoFactory.interventionDao().updateIntervention(intervention);
		} catch (InterventionUpdateException e) {
			return Status.UPDATE_FAIL;
		}
		
		return Status.OK;
	}
	
	@POST @Path("evaluate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status evaluate(Mark mark){
		try {
			DaoFactory.markDao().addMark(mark);
		} catch (MarkCreationException e) {			
			return Status.CREATION_FAIL;
		}
		
		return Status.OK;
	}
}
