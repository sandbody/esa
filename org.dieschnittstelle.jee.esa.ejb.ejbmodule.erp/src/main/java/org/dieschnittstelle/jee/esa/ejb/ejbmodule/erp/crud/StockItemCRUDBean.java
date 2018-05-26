package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.entities.erp.PointOfSale;
import org.dieschnittstelle.jee.esa.entities.erp.ProductAtPosPK;
import org.dieschnittstelle.jee.esa.entities.erp.StockItem;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.AmbiguousResolutionException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by pentax on 26.12.16.
 */
@Local(StockItemCRUDLocal.class)
@Stateless
public class StockItemCRUDBean implements StockItemCRUDLocal {
    protected static Logger logger = Logger.getLogger(StockItemCRUDBean.class);

    @PersistenceContext(unitName = "crm_erp_PU")
    EntityManager em;


    @Override
    public StockItem createStockItem(StockItem item) {
        logger.info("Creating Stock item "+item);
        return em.merge(item);
    }

    @Override
    public StockItem readStockItem(IndividualisedProductItem prod, PointOfSale pos) {
        logger.info("Reading stock item for "+prod+ " and "+pos);
//        Query query = em.createQuery("SELECT s FROM StockItem s where s.product=:prod and s.pos=:pos");
//        query.setParameter("prod", prod);
//        query.setParameter("pos", pos);
//        List<StockItem> items = (List<StockItem>)  query.getResultList();
//        logger.info("Found "+ items.size()+" of Stock item ");
//        if(items.size() > 1){
//           throw  new AmbiguousResolutionException("More than one entry found for IndividualisedProductItem "+prod+" and PointOfSale "+pos);
//       }
//
//        return items.size() < 0 ? null : items.get(0);


        ProductAtPosPK pk = new ProductAtPosPK(prod,pos);
        StockItem item = em.find(StockItem.class, pk);
        logger.info("Found StockItem: "+item);
        return item;

    }

    @Override
    public StockItem updateStockItem(StockItem item) {
        logger.info("Update Stock item "+item);
        return em.merge(item);
    }

    @Override
    public List<StockItem> readAllStockItems() {
        logger.info("Reading all stock items ");

        Query query = em.createQuery("SELECT s FROM StockItem s");

        List<StockItem> items = (List<StockItem>) query
                .getResultList();

        logger.info("Found "+ items.size()+" of Stock item ");
        return items;
    }

    @Override
    public List<StockItem> readStockItemsForProduct(IndividualisedProductItem prod) {
        logger.info("Reading stock items for "+prod);
        Query query = em.createQuery("SELECT s FROM StockItem s where s.product=:prod");
        query.setParameter("prod", prod);
        List<StockItem> items = (List<StockItem>)  query.getResultList();
        logger.info("Found "+ items.size()+" of Stock item ");
        return items;
    }

    @Override
    public List<StockItem> readStockItemsForPointOfSale(PointOfSale pos) {
        logger.info("Reading stock items for "+pos);
        Query query = em.createQuery("SELECT s FROM StockItem s where s.pos=:pos");
        query.setParameter("pos", pos);
        List<StockItem> items = (List<StockItem>)  query.getResultList();
        logger.info("Found "+ items.size()+" of Stock item ");
        return items;
    }
}
