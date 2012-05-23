package com.supinfo;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.data.MediaType;
import org.restlet.engine.Engine;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.supinfo.notetonsta.entity.Campus;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Engine.getInstance().getRegisteredConverters().clear();
		Engine.getInstance().getRegisteredConverters().add(new JacksonConverter());
		
		ClientResource cr = new ClientResource("http://notetonsta134508.appspot.com/campus/all");
		Campus camp = new Campus();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String json = cr.get(MediaType.APPLICATION_JSON).getText();
			System.out.println(json);
			List<Campus> campus = mapper.readValue(json, new TypeReference<List<Campus>>(){});
			
			for(Campus c : campus){
				System.out.println(c.getKey().getId() + " : " + c.getName());
				camp = c;
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Consume
		
		ClientResource consumer = new ClientResource("http://notetonsta134508.appspot.com/campus/statustest");
		consumer.setRequestEntityBuffering(true);
		System.out.println(camp.getName());
		try {
			consumer.post(camp, MediaType.APPLICATION_JSON).write(System.out);
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
