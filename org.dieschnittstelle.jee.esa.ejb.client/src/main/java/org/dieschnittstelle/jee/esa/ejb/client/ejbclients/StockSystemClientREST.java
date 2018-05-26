package org.dieschnittstelle.jee.esa.ejb.client.ejbclients;

import org.dieschnittstelle.jee.esa.ejb.client.restclient.ITouchpointAccessRESTService;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemRemote;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemRemoteREST;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.*;
import java.util.List;

public class StockSystemClientREST implements StockSystemRemoteREST {

    private StockSystemRemoteREST client;
    public StockSystemClientREST(){
        ResteasyClient resteasyClient= new ResteasyClientBuilder().build();
        ResteasyWebTarget target = resteasyClient.target("http://localhost:8080/org.dieschnittstelle.jee.esa.ejb.webapp/rest");
         client = (StockSystemRemoteREST)target.proxy(StockSystemRemoteREST.class);
    }


    @Override
    @POST
    @Path("/{id}/{pointOfSaleId}/{units}")
    public void addToStock(@PathParam("id") long id, @PathParam("pointOfSaleId") long pointOfSaleId, @PathParam("units") int units) {
        client.addToStock(id,pointOfSaleId,units);
    }

    @Override
    @DELETE
    @Path("/{id}/{pointOfSaleId}/{units}")
    public void removeFromStock(@PathParam("id") long id, @PathParam("pointOfSaleId") long pointOfSaleId, @PathParam("units") int units) {
        client.removeFromStock(id,pointOfSaleId, units);
    }

    @Override
    @GET
    @Path("/{pointOfSaleId}")
    public List<IndividualisedProductItem> getProductsOnStock(@PathParam("pointOfSaleId") long pointOfSaleId) {
        return client.getProductsOnStock(pointOfSaleId);
    }

    @Override
    @GET
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        return client.getAllProductsOnStock();
    }

    @Override
    @GET
    @Path("/{id}/{pointOfSaleId}/stockunits")
    public int getUnitsOnStock(@PathParam("id") long id, @PathParam("pointOfSaleId") long pointOfSaleId) {
        return client.getUnitsOnStock(id, pointOfSaleId);
    }

    @Override
    @GET
    @Path("/{id}/totalunits")
    public int getTotalUnitsOnStock(@PathParam("id") long id) {
        return client.getTotalUnitsOnStock(id);
    }

    @Override
    @GET
    @Path("/{id}/salepoints")
    public List<Long> getPointsOfSale(@PathParam("id") long id) {
        return client.getPointsOfSale(id);
    }
}
