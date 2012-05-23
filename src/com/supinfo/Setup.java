package com.supinfo;

import com.google.appengine.api.datastore.KeyFactory;
import com.supinfo.exception.CampusCreationExecption;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Campus;

public class Setup {

	/**
	 * @param args
	 */
	public static void loadCampus() {
		// TODO Auto-generated method stub
		String[] campuses = {"Paris, Ch�teau-Landon",
				"Le Lamentin",
				"Strasbourg / Illkirch ",
				"Saint-Denis",
				"Montpellier",
				"Nice ",
				"Bordeaux",
				"M�con",
				"Troyes ",
				"Rennes",
				"Valenciennes",
				"Toulouse ",
				"Nantes",
				"Grenoble",
				"Caen ",
				"Tours",
				"Londres",
				"Montr�al ",
				"Clermont-Ferrand",
				"Limoges",
				"Lille ",
				"Marseille",
				"Orl�ans",
				"Paris, Bassano ",
				"Lyon",
				"Bruxelles",
				"Casablanca ",
				"Reims",
				"San Francisco",
				"Metz ",
				"Baie Mahault",
				"Rabat"};
		
		int i = 1;
		for(String name : campuses){
			Campus campus = new Campus();
			campus.setKey(KeyFactory.createKey(Campus.class.getSimpleName(), i));
			campus.setName(name);
			
			
			try {
				DaoFactory.campusDao().addCampus(campus);
				i++;
			} catch (CampusCreationExecption e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}

}
