package org.dieschnittstelle.jee.esa.ejb.client.ejbclients;

import java.util.List;

import org.dieschnittstelle.jee.esa.ejb.client.Constants;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.ProductCRUDRemote;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ProductCRUDClient implements ProductCRUDRemote {

	private ProductCRUDRemote proxy;

	public ProductCRUDClient() throws Exception {
		// obtain the beans using a jndi context
		Context context = new InitialContext();
		proxy = (ProductCRUDRemote) context
				.lookup(ProductCRUDRemote.JNDI_PATH);
	}

	public AbstractProduct createProduct(AbstractProduct prod) {

		// JPA3: KOMMENTIEREN SIE DIE FOLGENDE ZUWEISUNG VON IDs UND DIE RETURN-ANWEISUNG AUS
		//prod.setId(Constants.nextId());
		//return prod;

		// JPA3: KOMMENTIEREN SIE DEN FOLGENDEN CODE EIN		
		AbstractProduct created = proxy.createProduct(prod);
//		// as a side-effect we set the id of the created product on the argument before returning
		prod.setId(created.getId());
		return created;
	}

	public List<AbstractProduct> readAllProducts() {
		return proxy.readAllProducts();
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
		return proxy.updateProduct(update);
	}

	public AbstractProduct readProduct(long productID) {
		return proxy.readProduct(productID);
	}

	public boolean deleteProduct(long productID) {
		return proxy.deleteProduct(productID);
	}

}
