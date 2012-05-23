package com.supinfo;

import com.supinfo.exception.CampusCreationExecption;
import com.supinfo.notetonsta.dao.jpa.DaoFactory;
import com.supinfo.notetonsta.entity.Campus;

public class Setup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] campuses = {"Paris, Château-Landon",
				"Le Lamentin",
				"Strasbourg / Illkirch ",
				"Saint-Denis",
				"Montpellier",
				"Nice ",
				"Bordeaux",
				"Mâcon",
				"Troyes ",
				"Rennes",
				"Valenciennes",
				"Toulouse ",
				"Nantes",
				"Grenoble",
				"Caen ",
				"Tours",
				"Londres",
				"Montréal ",
				"Clermont-Ferrand",
				"Limoges",
				"Lille ",
				"Marseille",
				"Orléans",
				"Paris, Bassano ",
				"Lyon",
				"Bruxelles",
				"Casablanca ",
				"Reims",
				"San Francisco",
				"Metz ",
				"Baie Mahault",
				"Rabat"};
		
		for(String name : campuses){
			Campus campus = new Campus();
			campus.setName(name);
			
			
			try {
				DaoFactory.campusDao().addCampus(campus);
			} catch (CampusCreationExecption e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}

}
