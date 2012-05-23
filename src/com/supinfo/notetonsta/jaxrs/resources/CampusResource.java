package com.supinfo.notetonsta.jaxrs.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Campus;
import com.supinfo.notetonsta.entity.Intervention;

@Path("campus")
public class CampusResource {	
	@GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n"
                + "<h1>Campus Resource.</h1>\n"
                + "</body></html>";
    }
	
	@GET @Path("findall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Campus> getAllCampus(){				
		List<Campus> camps = DaoFactory.campusDao().findAll();
		return camps;
	}
	
	@GET @Path("interventions/{campuskey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Intervention> getInterventionsForCampus(@PathParam("campuskey") String campuskey){
		return DaoFactory.campusDao().getInterventions(KeyFactory.stringToKey(campuskey));		
	}
	
	@POST @Path("statustest")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Campus status(Campus campus){		
		return campus;
	}	
}
