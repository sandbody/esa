package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp;

import java.util.List;

import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;

import javax.ws.rs.*;

/**
 * TODO JPA4:
 * this interface shall be implemented using the two CRUD EJBs for PointOfSale and StockItem via
 * local interface below, comments will give some hints at how the implementation could be done
 */

public interface StockSystemRemote {

    /**
     * the base JNDI_PATH for the PreliminaryRequestBean EJB implementation of this view/interface
     */
    String BASE_JNDI_PATH = "ejb:org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp/StockSystemBean";
    /**
     * the view on this interface
     */
    String VIEW_JNDI_PATH = "!" + StockSystemRemote.class.getName();

    /**
     * The JNDI name for remote access to the implementing EJB.
     */
    String JNDI_PATH = BASE_JNDI_PATH + VIEW_JNDI_PATH;

	/**
	 * adds some units of a product to the stock of a point of sale
	 *
	 * should check whether stock item for the given combination of product and pointOfSale already exists.
	 * Depending on this, the existing stock item will be updated or a new one will be created
	 *
	 * @param product
	 * @param pointOfSaleId
	 * @param units
	 */
	public void addToStock(IndividualisedProductItem product,  long pointOfSaleId,   int units);

	/**
	 * removes some units of a product from the stock of a point of sale
	 *
	 * here you can reuse the addToStock method passing a negative value for units
	 *
	 * @param product
	 * @param pointOfSaleId
	 * @param units
	 */
	public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId,  int units);

	/**
	 * returns all products on stock of some pointOfSale
	 *
	 * this method can be implemented on the basis of the readStockItemsForPointOfSale() method
	 * in the CRUD EJB for StockItem, note that here and in most of the following methods you
	 * first need to read out the PointOfSale object for the given pointOfSaleId
	 *
	 * @param pointOfSaleId
	 * @return
	 */
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId);

	/**
	 * returns all products on stock
	 *
	 * this method can be implemented using the readAllPointsOfSale() method from the PointOfSale
	 * CRUD EJB and using getProductsOnStock for each of point of sale. Note that there should be
	 * no duplicates in the list that is returned.
	 *
	 * @return
	 */
	public List<IndividualisedProductItem> getAllProductsOnStock();

	/**
	 * returns the units on stock for a product at some point of sale
	 *
	 * this method can very easily be implemented using the readStockItem method of the StockItem EJB.
	 * Consider the case that no stock item exists for the given combination to avoid NullPointerException
	 *
	 * @param product
	 * @param pointOfSaleId
	 * @return
	 */
	public int getUnitsOnStock(IndividualisedProductItem product,long pointOfSaleId);

	/**
	 * returns the total number of units on stock for some product
	 *
	 * here you can combine the readAllPointsOfSale() method from the PointOfSale CRUD EJB with the
	 * getUnitsOnStock() method above
	 *
	 * @param product
	 * @return
	 */
	public int getTotalUnitsOnStock(IndividualisedProductItem product);

	/**
	 * returns the points of sale where some product is available
	 *
	 * here you can use readStockItemsForProduct(product) and create a list of the stock items'
	 * pointOfSale Ids
	 *
	 * @param product
	 * @return
	 */
	public List<Long> getPointsOfSale(IndividualisedProductItem product);

}
