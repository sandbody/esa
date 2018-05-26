package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud;

import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by pentax on 28.01.17.
 */
public interface ProductCRUDLocal {
    public AbstractProduct createProduct(AbstractProduct prod);

    public List<AbstractProduct> readAllProducts();

    public AbstractProduct updateProduct(AbstractProduct update);

    public AbstractProduct readProduct(long productID);

    public boolean deleteProduct(long productID);
}
