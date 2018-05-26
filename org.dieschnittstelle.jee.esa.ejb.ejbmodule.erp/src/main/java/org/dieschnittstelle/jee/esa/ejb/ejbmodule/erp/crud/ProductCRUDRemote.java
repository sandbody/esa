package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud;

import java.util.List;

import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

import javax.ejb.Remote;

/*
 * TODO JPA3:
 * this interface shall be implemented using a stateless EJB with an EntityManager.
 * See TouchpointCRUDStateless for an example EJB with a similar scope of functionality
 */
public interface ProductCRUDRemote extends ProductCRUDLocal{

    /**
     * the base JNDI_PATH for the PreliminaryRequestBean EJB implementation of this view/interface
     */
    String BASE_JNDI_PATH = "ejb:org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp/ProductCRUDBean";
    /**
     * the view on this interface
     */
    String VIEW_JNDI_PATH = "!" + ProductCRUDRemote.class.getName();

    /**
     * The JNDI name for remote access to the implementing EJB.
     */
    String JNDI_PATH = BASE_JNDI_PATH + VIEW_JNDI_PATH;



}
