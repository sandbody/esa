package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.shopping;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.exceptions.VerifyCampaignException;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.crm.Customer;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

public interface ShoppingSessionFacadeRemote {
    /**
     * the base JNDI_PATH for the ShoppingSessionBean EJB implementation of this view/interface
     */
    String BASE_JNDI_PATH = "ejb:org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm/ShoppingSessionBean";
    /**
     * the view on this interface
     */
    String VIEW_JNDI_PATH = "!" + ShoppingSessionFacadeRemote.class.getName()+"?stateful";

    /**
     * The JNDI name for remote access to the implementing EJB.
     */
    String JNDI_PATH = BASE_JNDI_PATH + VIEW_JNDI_PATH;

	public void setTouchpoint(AbstractTouchpoint touchpoint);
	
	public void setCustomer(Customer customer);
	
	public void addProduct(AbstractProduct product, int units);
	
	public void purchase() throws VerifyCampaignException;
	
}
