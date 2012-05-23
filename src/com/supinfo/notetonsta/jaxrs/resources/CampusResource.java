package com.supinfo.notetonsta.jaxrs.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Campus;

@Path("campus")
public class CampusResource {
	@GET
	@Produces("text/html")
	public String getHtml() {
		return "<html><head></head><body>\n" + "<h1>Campus Resource.</h1>\n"
				+ "</body></html>";
	}

	@GET
	@Path("findall")
	@Produces({MediaType.APPLICATION_JSON,"application/xml"})	
	public List<Campus> getAllCampus() {
		List<Campus> camps = DaoFactory.campusDao().findAll();
		return camps;
	}
}
