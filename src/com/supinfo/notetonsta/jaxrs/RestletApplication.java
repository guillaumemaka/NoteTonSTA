package com.supinfo.notetonsta.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.restlet.engine.Engine;
import org.restlet.ext.jackson.JacksonConverter;

import com.supinfo.notetonsta.jaxrs.resources.AuthenticationResource;
import com.supinfo.notetonsta.jaxrs.resources.CampusResource;
import com.supinfo.notetonsta.jaxrs.resources.InterventionResource;
import com.supinfo.notetonsta.jaxrs.resources.MarkResource;
import com.supinfo.notetonsta.jaxrs.resources.ServiceResource;


public class RestletApplication extends Application {
	public RestletApplication() {
		Engine.getInstance().getRegisteredConverters().clear();
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());		
	}

	public Set<Class<?>> getClasses() {		
		Set<Class<?>> rrcs = new HashSet<Class<?>>();
        
        rrcs.add(AuthenticationResource.class);        
        rrcs.add(InterventionResource.class);
        rrcs.add(CampusResource.class);        
        rrcs.add(MarkResource.class);
        rrcs.add(ServiceResource.class);
        
        return rrcs;
    }
}
