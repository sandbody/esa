package org.dieschnittstelle.jee.esa.ejb.client.shopping;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.exceptions.VerifyCampaignException;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.shopping.ShoppingSessionFacadeRemote;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.crm.Customer;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ShoppingSessionFacadeClient implements ShoppingBusinessDelegate {

	protected static Logger logger = Logger
			.getLogger(ShoppingSessionFacadeClient.class);

	/*
	 * use the ShoppingSessionFacadeRemote interface
	 */

    private ShoppingSessionFacadeRemote proxy;
	@Override
	public void initialise() {
		/* create a jndi context */
        Context context = null;
        try {
            context = new InitialContext();

		/* lookup the bean */
        proxy = (ShoppingSessionFacadeRemote) context
                .lookup(ShoppingSessionFacadeRemote.JNDI_PATH);
        } catch (NamingException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void setTouchpoint(AbstractTouchpoint touchpoint) {
	    proxy.setTouchpoint(touchpoint);
	}

	@Override
	public void setCustomer(Customer customer) {
	    proxy.setCustomer(customer);
	}

	@Override
	public void addProduct(AbstractProduct product, int units) {
	    proxy.addProduct(product,units);
	}

	@Override
	public void purchase() {
        try {
            proxy.purchase();
        } catch (VerifyCampaignException e) {
            throw new RuntimeException(e);
        }
    }

}
