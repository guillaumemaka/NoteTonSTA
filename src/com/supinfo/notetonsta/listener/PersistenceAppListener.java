package com.supinfo.notetonsta.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.supinfo.notetonsta.util.PersistenceManager;

/**
 * Application Lifecycle Listener implementation class PersistenceAppListener
 *
 */
public class PersistenceAppListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public PersistenceAppListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	PersistenceManager.close();    	
    }
	
}
