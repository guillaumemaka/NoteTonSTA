package com.supinfo.notetonsta.jaxrs;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class JaxRsEntryPoint extends JaxRsApplication{	
	public JaxRsEntryPoint(Context context) {
		super(context);
		this.add(new RestletApplication());
	}
}
