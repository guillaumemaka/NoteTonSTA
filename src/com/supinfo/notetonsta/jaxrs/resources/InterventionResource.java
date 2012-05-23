package com.supinfo.notetonsta.jaxrs.resources;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.exception.MarkCreationException;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Intervention;
import com.supinfo.notetonsta.entity.Mark;
import com.supinfo.notetonsta.util.Status;

@Path("intervention")
public class InterventionResource {
	private final Logger log = Logger.getLogger(InterventionResource.class.getSimpleName());

	@GET
	@Produces("text/html")
	public String getHtml() {
		return "<html><head></head><body>\n"
				+ "<h1>Intervention Resource.</h1>\n" + "</body></html>";
	}

	@GET
	@Path("findall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Intervention> getAllInterventions() {
		return DaoFactory.interventionDao().findAll();
	}

	@GET
	@Path("find/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public Intervention getIntervention(@PathParam("key") String interventionKey) {
		log.info("Received intervention key: " + interventionKey);
		return DaoFactory.interventionDao().find(KeyFactory.stringToKey(interventionKey).getId());
	}

	@GET
	@Path("findByCampus/{campusKey}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Intervention> getInterventionForCampus(@PathParam("campusKey") String campusKey) {
		Logger.getAnonymousLogger().info("Campus key from get: " + campusKey);
		return DaoFactory.interventionDao().findByCampus(
				DaoFactory.campusDao().find(KeyFactory.stringToKey(campusKey)));
	}

	//
	// @POST
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Status create(Intervention intervention){
	// try {
	// DaoFactory.interventionDao().addIntervention(intervention);
	// } catch (InterventionCreationException e) {
	// return Status.CREATION_FAIL;
	// }
	//
	// return Status.OK;
	// }
	//
	// @DELETE
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Status delete(Intervention intervention){
	// try {
	// DaoFactory.interventionDao().removeIntervention(intervention);
	// } catch (InterventionDeletionException e) {
	// return Status.DELETION_FAIL;
	// }
	//
	// return Status.OK;
	// }
	//
	// @PUT
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Status update(Intervention intervention){
	// try {
	// DaoFactory.interventionDao().updateIntervention(intervention);
	// } catch (InterventionUpdateException e) {
	// return Status.UPDATE_FAIL;
	// }
	//
	// return Status.OK;
	// }

	@POST
	@Path("evaluate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status evaluate(Mark mark) {
		try {			
			DaoFactory.markDao().addMark(mark);
		} catch (MarkCreationException e) {
			return Status.CREATION_FAIL;
		}

		return Status.OK;
	}
}
