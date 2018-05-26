package org.dieschnittstelle.jee.esa.ser;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;

public class TouchpointWebServiceServlet extends HttpServlet {

	protected static Logger logger = Logger
			.getLogger(TouchpointWebServiceServlet.class);

	public TouchpointWebServiceServlet() {
		System.err.println("TouchpointWebServiceServlet: constructor invoked\n");
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("doGet()");

		// we assume here that GET will only be used to return the list of all
		// touchpoints

		// obtain the executor for reading out the touchpoints
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		try {
			// set the status
			response.setStatus(HttpServletResponse.SC_OK);
			// obtain the output stream from the response and write the list of
			// touchpoints into the stream
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			// write the object
			oos.writeObject(exec.readAllTouchpoints());
			oos.close();
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doDelete()");

		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		try {
			// set the status
			resp.setStatus(HttpServletResponse.SC_OK);
			String id = req.getParameter("itemToDelete");
			logger.info("Incoming id is: "+id);
			// obtain the output stream from the response and write the list of
			// touchpoints into the stream
			ObjectOutputStream oos = new ObjectOutputStream(
					resp.getOutputStream());
			// delete the object
			long itemToDelete = Long.valueOf(id);
			oos.writeObject(exec.deleteTouchpoint(itemToDelete));
			oos.close();
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}


	@Override	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("doPost()");

		// assume POST will only be used for touchpoint creation, i.e. there is
		// no need to check the uri that has been used

		// obtain the executor for reading out the touchpoints from the servlet context using the touchpointCRUD attribute
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");

		response.setStatus(HttpServletResponse.SC_OK);

		try {
			// create an ObjectInputStream from the request's input stream
		
			// read an AbstractTouchpoint object from the stream
		
			// call the create method on the executor and take its return value
		
			// set the response status as successful, using the appropriate
			// constant from HttpServletResponse
		
			// then write the object to the response's output stream, using a
			// wrapping ObjectOutputStream

			InputStream is = request.getInputStream();
			ObjectInputStream  ois = new ObjectInputStream(is);

			AbstractTouchpoint entity = (AbstractTouchpoint)ois .readObject();
			// ... and write the object to the stream
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());

			oos.writeObject(exec.createTouchpoint(entity));
			oos.close();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new RuntimeException(e);
		}

	}

	private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
			try(ObjectInputStream o = new ObjectInputStream(b)){
				return o.readObject();
			}
		}
	}

	
}
