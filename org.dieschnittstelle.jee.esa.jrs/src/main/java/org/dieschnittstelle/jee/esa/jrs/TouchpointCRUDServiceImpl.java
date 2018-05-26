package org.dieschnittstelle.jee.esa.jrs;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.crm.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;

public class TouchpointCRUDServiceImpl implements ITouchpointCRUDService {
	
	protected static Logger logger = Logger.getLogger(TouchpointCRUDServiceImpl.class);

	/**
	 * this accessor will be provided by the ServletContext, to which it is written by the TouchpointServletContextListener
	 */
//	private GenericCRUDExecutor<AbstractTouchpoint> touchpointCRUD;

    private @Context ServletContext servletContext;



	/**
	 * here we will be passed the context parameters by the resteasy framework
	 * note that the request context is only declared for illustration purposes, but will not be further used here
	 * //@param servletContext
	 */
//	public TouchpointCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
//		logger.info("<constructor>: " + servletContext + "/" + request);
//		// read out the dataAccessor
//		this.touchpointCRUD = (GenericCRUDExecutor<AbstractTouchpoint>)servletContext.getAttribute("touchpointCRUD");
//
//		logger.debug("read out the touchpointCRUD from the servlet context: " + this.touchpointCRUD);
//	}

    public TouchpointCRUDServiceImpl() {

    }

	@Override
	public List<StationaryTouchpoint> readAllTouchpoints() {
        logger.info("readAllTouchpoints is called");
        return (List) ((GenericCRUDExecutor<AbstractTouchpoint>)servletContext.getAttribute("touchpointCRUD")).readAllObjects();
	}

	@Override
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint) {
        logger.info("createTouchpoint is called with "+touchpoint);
		return (StationaryTouchpoint) ((GenericCRUDExecutor<AbstractTouchpoint>)servletContext.getAttribute("touchpointCRUD")).createObject(touchpoint);
	}

	@Override
	public boolean deleteTouchpoint(long id) {
        logger.info("deleteTouchpoint is called with "+id);
        return ((GenericCRUDExecutor<AbstractTouchpoint>)servletContext.getAttribute("touchpointCRUD")).deleteObject(id);
	}


	@Override
	public StationaryTouchpoint readTouchpoint(long id) {
        logger.info("readTouchpoint is called with "+id);
        return (StationaryTouchpoint)  ((GenericCRUDExecutor<AbstractTouchpoint>)servletContext.getAttribute("touchpointCRUD")).readObject(id);
	}

	/*
	 * UE JRS1: implement the method for updating touchpoints
	 */
	@Override
	public StationaryTouchpoint updateTouchpoint(StationaryTouchpoint touchpoint) {
        logger.info("updateTouchpoint is called with "+touchpoint);
        return (StationaryTouchpoint)  ((GenericCRUDExecutor<AbstractTouchpoint>)servletContext.getAttribute("touchpointCRUD")).updateObject(touchpoint);
	}

}
