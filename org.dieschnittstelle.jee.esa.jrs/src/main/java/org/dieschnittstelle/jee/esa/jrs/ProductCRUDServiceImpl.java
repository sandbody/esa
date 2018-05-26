package org.dieschnittstelle.jee.esa.jrs;

import java.util.List;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/*
UE JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {

	protected static Logger LOG = Logger.getLogger(ProductCRUDServiceImpl.class);
	/**
	 * this accessor will be provided by the ServletContext, to which it is written by the ProductServletContextListener
	 */
//	private GenericCRUDExecutor<AbstractProduct> productCRUD;

	/**
	 * here we will be passed the context parameters by the resteasy framework
	 * note that the request context is only declared for illustration purposes, but will not be further used here
	 * @param servletContext
	 */
//	public ProductCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
//		LOG.info("<constructor>: " + servletContext + "/" + request);
//		// read out the dataAccessor
//		this.productCRUD = (GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD");
//
//		LOG.debug("read out the productCRUD from the servlet context: " + this.productCRUD);
//	}


    @Context ServletContext servletContext;

    public ProductCRUDServiceImpl() {

        LOG.debug("read out the productCRUD from the servlet context: ");
    }


    @Override
	public AbstractProduct createProduct(
            AbstractProduct prod) {
		LOG.info("Begin to create a new product ");
		return (AbstractProduct)  ((GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD")).createObject(prod);
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		LOG.info("Returning all products ");
		return (List)((GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD")).readAllObjects();
	}

	@Override
	public AbstractProduct updateProduct(AbstractProduct update) {
		LOG.info("Begin to update incoming product with id = "+ update.getId());
		return (AbstractProduct)((GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD")).updateObject(update);
	}


	@Override
	public boolean deleteProduct(long id) {
		LOG.info("Begin to delete product with id = "+id);
		return ((GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD")).deleteObject(id);
	}

	@Override
	public AbstractProduct readProduct(long id) {
		LOG.info("Returning product with id = "+id);
		return (AbstractProduct)((GenericCRUDExecutor<AbstractProduct>)servletContext.getAttribute("productCRUD")).readObject(id);
	}
	
}
