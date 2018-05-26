package org.dieschnittstelle.jee.esa.ue.jws4;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;

/*
 * UE JWS4: machen Sie die Funktionalitaet dieser Klasse als Web Service verfuegbar und verwenden Sie fuer 
 * die Umetzung der beiden Methoden die Instanz von GenericCRUDExecutor<AbstractProduct>, 
 * die Sie aus dem ServletContext auslesen koennen
 *
 */
@WebService(serviceName = "ProductCRUDSOAPService" , portName = "ProductServicePort", targetNamespace = "http://dieschnittstelle.org/jee/esa/jws")
public class ProductCRUDWebService {
    protected static Logger logger = Logger
            .getLogger(ProductCRUDWebService.class);

    @Resource
    WebServiceContext context;
 // Defaulteinstellung: Alle Methoden mit Sichtbarkeit public sind automatisch als @WebMethod definiert. Daher keine zusätzliche Annotation

    public List<AbstractProduct> readAllProducts() {
        logger.info("readAllProducts()");

        GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) context
                .getMessageContext().get(MessageContext.SERVLET_CONTEXT))
                .getAttribute("productCRUD");

        return productCRUD.readAllObjects();
	}

    public AbstractProduct createProduct(AbstractProduct product) {
        logger.info("createProduct()");

        GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) context
                .getMessageContext().get(MessageContext.SERVLET_CONTEXT))
                .getAttribute("productCRUD");

        return productCRUD.createObject(product);
	}

	public AbstractProduct updateProduct(AbstractProduct update) {
        logger.info("updateProduct()");

        GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) context
                .getMessageContext().get(MessageContext.SERVLET_CONTEXT))
                .getAttribute("productCRUD");

        return productCRUD.updateObject(update);

	}

	public boolean deleteProduct(long id) {
        logger.info("deleteProduct()");

        GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) context
                .getMessageContext().get(MessageContext.SERVLET_CONTEXT))
                .getAttribute("productCRUD");

        return productCRUD.deleteObject(id);
	}

	public AbstractProduct readProduct(long id) {
        logger.info("readProduct()");

        GenericCRUDExecutor<AbstractProduct> productCRUD = (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) context
                .getMessageContext().get(MessageContext.SERVLET_CONTEXT))
                .getAttribute("productCRUD");

        return  productCRUD.readObject(id);
	}

}
