package org.dieschnittstelle.jee.esa.ue.jws5.junit;


import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.jws.ProductCRUDSOAPService;
import org.dieschnittstelle.jee.esa.jws.ProductCRUDWebService;

import java.net.URL;
import java.util.List;



/**
 * Created by pentax on 17.11.16.
 */
public class ProductCRUDSOAPClient {

	private ProductCRUDWebService proxy;

	protected static Logger logger = Logger.getLogger(ProductCRUDSOAPClient.class);

	public ProductCRUDSOAPClient() throws Exception {

		/*
		 * create a client for the web service using the WebService class ProductCRUDSOAPService and accessing the "port" for ProductCRUDWebService using the appropriate getter
       * DO NOT FORGET TO SPECIFY THE WSDL URL IN THE pom.xml FILE OF THE JWS PROJECT (search for TODO)
		 */
		proxy = new ProductCRUDSOAPService(new URL("http://localhost:8080/org.dieschnittstelle.jee.esa.ue.jws4/ProductCRUDSOAPService")).getProductServicePort();
	}

	public AbstractProduct createProduct(IndividualisedProductItem prod) {
		AbstractProduct created = proxy.createProduct(prod);
		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	public List<?> readAllProducts() {
		return proxy.readAllProducts();
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
		return proxy.updateProduct(update);
	}

	public boolean deleteProduct(long id) {
		return proxy.deleteProduct(id);
	}

	public AbstractProduct readProduct(long id) {
		return proxy.readProduct(id);
	}


}
