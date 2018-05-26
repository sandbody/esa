package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;
import org.dieschnittstelle.jee.esa.entities.erp.Campaign;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by pentax on 22.12.16.
 */
@Remote(ProductCRUDRemote.class)
@Local(ProductCRUDLocal.class)
@Stateless
public class ProductCRUDBean implements ProductCRUDRemote, ProductCRUDLocal {

    protected static Logger logger = Logger.getLogger(ProductCRUDBean.class);

    @PersistenceContext(unitName = "crm_erp_PU")
    private EntityManager em;


    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        logger.info("Begin to create "+prod);
        return em.merge(prod);
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        logger.info("Begin to read all products ");
        Query query = em.createQuery("SELECT ap FROM AbstractProduct ap");

        List<AbstractProduct> list = (List<AbstractProduct>) query
                .getResultList();
        logger.info("found "+list.size()+" of products");
        return list;
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        logger.info("Begin to update "+update);
        return em.merge(update);
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        logger.info("Find product with id "+productID);

        AbstractProduct ap = em.find(AbstractProduct.class, productID);

        return ap;
    }

    @Override
    public boolean deleteProduct(long productID) {
        logger.info("Delete product with id "+productID);
        AbstractProduct product = readProduct(productID);
        if(null == product){
            return  false;
        }
        em.remove(product);
        return true;
    }
}
