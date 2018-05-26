package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemLocal;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemRemote;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemRemoteREST;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;

import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import java.util.List;

/**
 * Created by pentax on 28.01.17.
 */
@Stateless
public class StockSystemRestBean implements StockSystemRemoteREST {

  //  @EJB(lookup = "java:global/org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp/StockSystemBean!org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.StockSystemBean")
    @EJB
  StockSystemBean stock;

//    @EJB(lookup = "java:global/org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp/ProductCRUDBean!org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.ProductCRUDLocal")
    @EJB
    ProductCRUDLocal product;

    @Override
    public void addToStock(long id, long pointOfSaleId, int units) {
        stock.addToStock(findItem(id), pointOfSaleId, units);
    }

    @Override
    public void removeFromStock(long id, long pointOfSaleId, int units) {
        stock.removeFromStock(findItem(id), pointOfSaleId, units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        return stock.getProductsOnStock(pointOfSaleId);
    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        return stock.getAllProductsOnStock();
    }

    @Override
    public int getUnitsOnStock(long id, long pointOfSaleId) {
        return stock.getUnitsOnStock(findItem(id),pointOfSaleId);
    }

    @Override
    public int getTotalUnitsOnStock(long id) {
        return stock.getTotalUnitsOnStock(findItem(id));
    }

    @Override
    public List<Long> getPointsOfSale(long id) {
        return stock.getPointsOfSale(findItem(id));
    }


    private IndividualisedProductItem findItem(long id) {
        IndividualisedProductItem item = (IndividualisedProductItem)product.readProduct(id);
        if(null == item) {
            throw new IllegalArgumentException("No IndividualisedProductItem found with id " + id);
        }
        return item;
    }
}
