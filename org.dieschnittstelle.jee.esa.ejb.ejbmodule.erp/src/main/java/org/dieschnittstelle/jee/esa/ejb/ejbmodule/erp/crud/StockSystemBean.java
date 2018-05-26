package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemRemote;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.entities.erp.PointOfSale;
import org.dieschnittstelle.jee.esa.entities.erp.StockItem;

import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pentax on 10.12.16.
 */
@Remote(StockSystemRemote.class)
@Stateless
@LocalBean
public class StockSystemBean implements StockSystemRemote {

    protected static Logger logger = Logger.getLogger(StockSystemBean.class);

//    @EJB
//    private StockSystemSingleton crud;

//    @PersistenceContext(unitName = "crm_erp_PU")
//    EntityManager em;

    @EJB
    private StockItemCRUDLocal stock;

    @EJB
    private PointOfSaleCRUDLocal pos;

    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        logger.info("addToStock(): " +  product +" : "+ pointOfSaleId +" : " + units);

        PointOfSale p =   pos.readPointOfSale(pointOfSaleId);

        StockItem stockItem = stock.readStockItem(product, p);
        if(null == stockItem) {
            stockItem = stock.createStockItem(new StockItem(product, p, units));
        }else{
           stockItem.setUnits(stockItem.getUnits()+units);
          stockItem = stock.updateStockItem(stockItem);
        }

      //  em.persist(stockItem);

        //crud.addToStock(product,pointOfSaleId,units);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        logger.info("removeFromStock(): " + product +" : "+ pointOfSaleId +" : " + units);
        addToStock(product,pointOfSaleId,-units);

/*
        PointOfSale p =   pos.readPointOfSale(pointOfSaleId);

        StockItem item = stock.readStockItem(product, p);
        item.setUnits(item.getUnits() - units);

        stock.updateStockItem(item);
*/
      //  crud.removeFromStock(product,pointOfSaleId,units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        logger.info("getProductsOnStock(): " + pointOfSaleId);
        PointOfSale p =   pos.readPointOfSale(pointOfSaleId);

           //  return crud.getProductsOnStock(pointOfSaleId);
        return readStockItemsForPointOfSale(p, new ArrayList<>());
     }

    /**
     * Ask for all StockItem for given PointOfSale and add IndividualisedProductItem from the result  into to given list by checking
     * on unique list entry !
     *
     *  !!!! ATTENTION : NOT NULL-SAFE !!
     *
     * @param p the PointOfSale to aks for
     * @param ivy the list of unique IndividualisedProductItem
     * @return the incoming list with entries.
     */
     private  List<IndividualisedProductItem> readStockItemsForPointOfSale(PointOfSale p, List<IndividualisedProductItem> ivy ){
         List<StockItem> items = stock.readStockItemsForPointOfSale(p);
         for (StockItem item:items) {
             IndividualisedProductItem it = item.getProduct();
             if(ivy.contains(it)){
                 continue;
             }

             ivy.add(it);
         }

         return ivy;

     }

 @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        List<IndividualisedProductItem> list = new ArrayList<>();

        logger.info("getAllProductsOnStock(): " );
        List<PointOfSale> pointOfSales = pos.readAllPointsOfSale();

        for (PointOfSale pos: pointOfSales) {
            readStockItemsForPointOfSale(pos, list);
        }


        logger.info("found "+list.size()+" of products");

        return list;
        //return crud.getAllProductsOnStock();
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        logger.info("getUnitsOnStock(): " + product +" : "+ pointOfSaleId);
        PointOfSale p =   pos.readPointOfSale(pointOfSaleId);

        StockItem item = stock.readStockItem(product, p);

        return item.getUnits();
        //return crud.getUnitsOnStock(product, pointOfSaleId);
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        logger.info("getTotalUnitsOnStock(): " + product);
        List<StockItem> items =  stock.readStockItemsForProduct(product);
        int total = 0;

        for (StockItem item: items) {
            total +=item.getUnits();

        }

        return total;
        //return crud.getTotalUnitsOnStock(product);
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        logger.info("getPointsOfSale(): " + product);
        List<StockItem> items =  stock.readStockItemsForProduct(product);

        List<Long> pois= new ArrayList<>();
        for (StockItem item: items) {
           pois.add(item.getPos().getId());
        }

        return pois;

        //return crud.getPointsOfSale(product);
    }



 }
