package org.dieschnittstelle.jee.esa.jrs.client.junit;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;
import org.dieschnittstelle.jee.esa.jrs.IProductCRUDService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.List;

public class ProductCRUDClient {

	private IProductCRUDService proxy;
	
	protected static Logger logger = Logger.getLogger(ProductCRUDClient.class);

	public ProductCRUDClient() throws Exception {

		/*
		 * create a client for the web service using ResteasyClientBuilder and ResteasyWebTarget
		 */
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8888/org.dieschnittstelle.jee.esa.jrs/api");
		proxy =  target.proxy(IProductCRUDService.class);
	}

	public AbstractProduct createProduct(AbstractProduct prod) {
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
