package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;


import org.dieschnittstelle.jee.esa.entities.crm.CrmProductBundle;
import org.apache.log4j.Logger;

/**
 * provides shopping cart functionality
 */
@Stateful
public class ShoppingCartStateful implements ShoppingCartRemote, ShoppingCartLocal {
	
	protected static Logger logger = Logger.getLogger(ShoppingCartStateful.class);

	private List<CrmProductBundle> productBundles = new ArrayList<CrmProductBundle>();
	
	public ShoppingCartStateful() {
		logger.info("<constructor>: " + this);
	}
	
	public void addProductBundle(CrmProductBundle product) {
		logger.info("addProductBundle(): " + product);

		// check whether we already have a bundle for the given product
		boolean bundleUpdate = false;
		for (CrmProductBundle bundle : productBundles) {
			if (bundle.getErpProductId() == product.getErpProductId()) {
				bundle.setUnits(bundle.getUnits()+product.getUnits());
				bundleUpdate = true;
				break;
			}
		}
		if (!bundleUpdate) {
			this.productBundles.add(product);
		}
	}
	
	public List<CrmProductBundle> getProductBundles() {
		logger.info("getProductBundles()");

		return this.productBundles;
	}

	// Kemal: Transaction...  eingeführt, um die Warnings los zu werden
	@PostConstruct
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void beginn() {
		logger.info("@PostConstruct");
	}

	@PreDestroy
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void abschluss() {
		logger.info("@PreDestroy");
	}

	@PrePassivate
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void passiviere() {
		logger.info("@PrePassivate");
	}

	@PostActivate
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void aktiviere() {
		logger.info("@PostActivate");
	}

}
