package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp;

import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;

import javax.jws.WebService;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by pentax on 28.01.17.
 */
@Path("/stocksystem")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface StockSystemRemoteREST {

    /**
     * adds some units of a product to the stock of a point of sale
     *
     * should check whether stock item for the given combination of product and pointOfSale already exists.
     * Depending on this, the existing stock item will be updated or a new one will be created
     *
     * @param pointOfSaleId
     * @param units
     */
    @POST
    @Path("/{id}/{pointOfSaleId}/{units}")
    public void addToStock(@PathParam("id") long id, @PathParam("pointOfSaleId") long pointOfSaleId, @PathParam("units") int units);

    /**
     * removes some units of a product from the stock of a point of sale
     *
     * here you can reuse the addToStock method passing a negative value for units
     *
     * @param pointOfSaleId
     * @param units
     */
    @DELETE
    @Path("/{id}/{pointOfSaleId}/{units}")
    public void removeFromStock(@PathParam("id") long id,@PathParam("pointOfSaleId")  long pointOfSaleId, @PathParam("units") int units);

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
    @GET
    @Path("/{pointOfSaleId}")
    public List<IndividualisedProductItem> getProductsOnStock(@PathParam("pointOfSaleId")long pointOfSaleId);

    /**
     * returns all products on stock
     *
     * this method can be implemented using the readAllPointsOfSale() method from the PointOfSale
     * CRUD EJB and using getProductsOnStock for each of point of sale. Note that there should be
     * no duplicates in the list that is returned.
     *
     * @return
     */
    @GET
    public List<IndividualisedProductItem> getAllProductsOnStock();

    /**
     * returns the units on stock for a product at some point of sale
     *
     * this method can very easily be implemented using the readStockItem method of the StockItem EJB.
     * Consider the case that no stock item exists for the given combination to avoid NullPointerException
     *
     * @param pointOfSaleId
     * @return
     */
    @GET
    @Path("/{id}/{pointOfSaleId}/stockunits")
    public int getUnitsOnStock(@PathParam("id") long id, @PathParam("pointOfSaleId") long pointOfSaleId);

    /**
     * returns the total number of units on stock for some product
     *
     * here you can combine the readAllPointsOfSale() method from the PointOfSale CRUD EJB with the
     * getUnitsOnStock() method above
     *
     * @return
     */
    @GET
    @Path("/{id}/totalunits")
    public int getTotalUnitsOnStock(@PathParam("id") long id);

    /**
     * returns the points of sale where some product is available
     *
     * here you can use readStockItemsForProduct(product) and create a list of the stock items'
     * pointOfSale Ids
     *
     * @return
     */
    @GET
    @Path("/{id}/salepoints")
    public List<Long> getPointsOfSale(@PathParam("id") long id);


}



