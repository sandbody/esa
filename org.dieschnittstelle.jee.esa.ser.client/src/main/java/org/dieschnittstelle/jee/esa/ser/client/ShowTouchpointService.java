package org.dieschnittstelle.jee.esa.ser.client;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.crm.Address;
import org.dieschnittstelle.jee.esa.entities.crm.StationaryTouchpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ShowTouchpointService {

	protected static Logger logger = Logger
			.getLogger(ShowTouchpointService.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShowTouchpointService service = new ShowTouchpointService();
		service.run();
	}

	/**
	 * the http client that can be used for accessing the service on tomcat
	 */
	private HttpClient client;
	
	/**
	 * the attribute that controls whether we are running through (when called from the junit test) or not
	 */
	private boolean stepwise = true;

	/**
	 * constructor
	 */
	public ShowTouchpointService() {
		/*
		 * create a http client and access the web application to read out the
		 * list of touchpoints
		 */
		client = new DefaultHttpClient();
	}

	/**
	 * run
	 */
	public void run() {

		// 1) read out all touchpoints
		List<AbstractTouchpoint> touchpoints = readAllTouchpoints();

		// 2) delete the touchpoint after next console input
		if (touchpoints != null && touchpoints.size() > 0) {
			if (stepwise)
				step();

			deleteTouchpoint(touchpoints.get(0));
		}

		// 3) wait for input and create a new touchpoint
		if (stepwise) {
			step();
		}

		Address addr = new Address("Luxemburger Strasse", "10", "13353",
				"Berlin");
		StationaryTouchpoint tp = new StationaryTouchpoint(-1,
				"BHT Verkaufsstand", addr);

		createNewTouchpoint(tp);

		System.err.println("TestTouchpointService: done.\n");
	}

	/**
	 * read all touchpoints
	 * 
	 * @return
	 */
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoints()");

		try {

			// create a GetMethod

			// UE SER1: Aendern Sie die URL von api->gui
			HttpGet get = new HttpGet(
					"http://localhost:8888/org.dieschnittstelle.jee.esa.ser/api/touchpoints");
			// execute the method and obtain the response
			HttpResponse response = client.execute(get);

			// mittels der response.setHeader() Methode koennen Header-Felder
			// gesetzt werden

			// check the response status
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// try to read out an object from the response entity
				ObjectInputStream ois = new ObjectInputStream(response
						.getEntity().getContent());

				List<AbstractTouchpoint> touchpoints = (List<AbstractTouchpoint>) ois
						.readObject();

				logger.info("read touchpoints: " + touchpoints);

				// this is necessary to be able to use the client for
				// subsequent requests
				EntityUtils.consume(response.getEntity());

				return touchpoints;

			} else {
				String err = "could not successfully execute request. Got status code: "
						+ response.getStatusLine().getStatusCode();
				logger.error(err);
				throw new RuntimeException(err);
			}

		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * UE SER3
	 * 
	 * @param tp
	 */
	public void deleteTouchpoint(AbstractTouchpoint tp) {
		logger.info("deleteTouchpoint(): " + tp);
		HttpDelete delete = new HttpDelete(
				"http://localhost:8888/org.dieschnittstelle.jee.esa.ser/api/touchpoints?itemToDelete="+tp.getId());
		HttpResponse response = null;
		try {
			 response =client.execute(delete);

			ObjectInputStream ois = new ObjectInputStream(response
					.getEntity().getContent());
			System.err.println("delete order was successfull == : " + (Boolean)ois.readObject());
			EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// once you have received a response this is necessary to be able to
		// use the client for subsequent requests:
		// EntityUtils.consume(response.getEntity());

	}

	/**
	 * UE SER4
	 * 
	 * fuer das Schreiben des zu erzeugenden Objekts als Request Body siehe die
	 * Hinweise auf:
	 * http://stackoverflow.com/questions/10146692/how-do-i-write-to
	 * -an-outpustream-using-defaulthttpclient
	 * 
	 * @param tp
	 */
	public AbstractTouchpoint createNewTouchpoint(AbstractTouchpoint tp) {
		logger.info("createNewTouchpoint(): " + tp);

		try {

			HttpPost post = new HttpPost(
					"http://localhost:8888/org.dieschnittstelle.jee.esa.ser/api/touchpoints");


				// Increment Id
				tp.setId(tp.getId()+1);

				byte[] data = serialize(tp);
				ByteArrayEntity entity = new ByteArrayEntity(data);
				post.setEntity(entity);

			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				ObjectInputStream ois = new ObjectInputStream(response
						.getEntity().getContent());
				AbstractTouchpoint en = (AbstractTouchpoint) ois.readObject();

				System.err.println("post order gets a new entity with id : " + en);
				EntityUtils.consume(response.getEntity());
				return en;
			} else {
				String err = "could not successfully execute request. Got status code: "
						+ response.getStatusLine().getStatusCode();
				logger.error(err);
				throw new RuntimeException(err);
			}
			// create post request for the api/touchpoints uri

			// create an ObjectOutputStream from a ByteArrayOutputStream - the
			// latter must be accessible via a variable

			// write the object to the output stream

			// create a ByteArrayEntity and pass it the byte array from the
			// output stream

			// set the entity on the request

			// execute the request, which will return a HttpResponse object

			// log the status line

			// evaluate the result using getStatusLine(), use constants in
			// HttpStatus

			/* if successful: */

			// create an object input stream using getContent() from the
			// response entity (accessible via getEntity())

			// read the touchpoint object from the input stream

			// cleanup the request
			// EntityUtils.consume(response.getEntity());

			// return the object that you hae read from the response

		} catch (Exception e) {
			logger.error("got exception: " + e, e);
			throw new RuntimeException(e);
		}

	}


	private byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}
	/**
	 * 
	 * @param stepwise
	 */
	public void setStepwise(boolean stepwise) {
		this.stepwise = stepwise;
	}

	/**
	 * utility...
	 */
	private void step() {
		try {
			System.out.println("/>");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
