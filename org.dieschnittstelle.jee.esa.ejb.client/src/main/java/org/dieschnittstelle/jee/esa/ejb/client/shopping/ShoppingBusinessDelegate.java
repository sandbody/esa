package org.dieschnittstelle.jee.esa.ejb.client.shopping;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.exceptions.VerifyCampaignException;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.crm.Customer;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

public interface ShoppingBusinessDelegate {
	
	public void initialise();
	
	public void setTouchpoint(AbstractTouchpoint touchpoint);
	
	public void setCustomer(Customer customer);
	
	public void addProduct(AbstractProduct product, int units);
	
	public void purchase() throws VerifyCampaignException;

}
