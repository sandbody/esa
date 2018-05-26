package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemLocal;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemRemote;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.entities.erp.PointOfSale;
import org.dieschnittstelle.jee.esa.entities.erp.StockItem;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.*;

/**
 * Created by pentax on 12.12.16.
 * Zugriff auf StockSystemSingleton über Boundary (StockSystemBean)
 */
@Singleton
@Startup
public class StockSystemSingleton {
    protected static Logger logger = Logger.getLogger(StockSystemSingleton.class);

    private Map<String , List<Long>> nameIdMap = new HashMap<>();
    private Map<Long, List<StockItem>> idStockItemMap = new HashMap<>();

    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        logger.info("addToStock(): " +  product +" : "+ pointOfSaleId +" : " + units);
        List<Long>  pointOfSaleIds =  nameIdMap.get(product.getName());
        if(null == pointOfSaleIds){
            pointOfSaleIds = new ArrayList<>();
            nameIdMap.put(product.getName(),pointOfSaleIds);
        }
        //Beware of auto-in and out-boxing
        Long id = Long.valueOf(pointOfSaleId);
        pointOfSaleIds.add(id);
        addNewStockItem(product,id,units);
    }

    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        logger.info("removeFromStock(): " + product +" : "+ pointOfSaleId +" : " + units);
    }

    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        logger.info("getProductsOnStock(): " + pointOfSaleId);
        return Collections.emptyList();
    }

    public List<IndividualisedProductItem> getAllProductsOnStock() {
        logger.info("getAllProductsOnStock(): " );

        return Collections.emptyList();
    }

    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        logger.info("getUnitsOnStock(): " + product +" : "+ pointOfSaleId);
        List<Long> pointOfSaleIds = nameIdMap.get(product.getName());
        return null == pointOfSaleIds ? 0 : pointOfSaleIds.size();
    }

    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        logger.info("getTotalUnitsOnStock(): " + product);

        return 0;
    }

    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        logger.info("getPointsOfSale(): " + product);

        return Collections.emptyList();
    }



    private void addNewStockItem(IndividualisedProductItem product, Long pointOfSaleId, int units){
        PointOfSale pos = new PointOfSale();
        pos.setId(pointOfSaleId.longValue());
        StockItem stockItem = new StockItem(product,pos, units);


        List<StockItem>  stockItems =  idStockItemMap.get(pointOfSaleId);
        if(null == stockItems){
            stockItems = new ArrayList<>();
            idStockItemMap.put(pointOfSaleId,stockItems);
        }
        stockItems.add(stockItem);
    }
}
