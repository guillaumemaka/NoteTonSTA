package com.supinfo.notetonsta.jaxrs.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.supinfo.notetonsta.util.ServiceStatus;

@Path("service")
public class ServiceResource {
	@GET @Path("avaibality")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceStatus checkServiceAvailability(){
		return ServiceStatus.Available;
	}
}
