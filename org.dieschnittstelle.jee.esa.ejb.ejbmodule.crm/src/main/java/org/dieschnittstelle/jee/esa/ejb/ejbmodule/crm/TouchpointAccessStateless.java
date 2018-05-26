package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.crud.TouchpointCRUDLocal;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.crm.CrmProductBundle;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.jee.esa.entities.erp.PointOfSale;
import org.apache.log4j.Logger;

@Stateless
@WebService(targetNamespace = "http://dieschnittstelle.org/jee/esa/jws", serviceName = "TouchpointAccessWebService", endpointInterface = "org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.TouchpointAccessRemote")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class TouchpointAccessStateless implements
		TouchpointAccessRemote, TouchpointAccessLocal {

	protected static Logger logger = Logger
			.getLogger(TouchpointAccessStateless.class);

	@EJB
	private TouchpointCRUDLocal touchpointCRUD;

	@EJB
	private PointOfSaleCRUDLocal posCRUD;

	@Override
	public AbstractTouchpoint createTouchpoint(
			AbstractTouchpoint touchpoint) {
		logger.info("createTouchpoint(): " + touchpoint);
		
		logProductBundleKlass();

		// we first create the posCRUD
		PointOfSale pos = posCRUD.createPointOfSale(new PointOfSale());
		logger.info("createTouchpoint(): created pointOfSale: "
				+ pos);

		// we pass the id to the touchpoint
		touchpoint.setErpPointOfSaleId(pos.getId());

		// then we persist the touchpoint
		touchpoint = touchpointCRUD.createTouchpoint(touchpoint);
		logger.info("createTouchpoint(): created touchpoint: "
				+ touchpoint);

		// return it
		return touchpoint;
	}

    @Override
    public List<AbstractTouchpoint> readAllTouchpoints() {
        return touchpointCRUD.readAllTouchpoints();
    }

    @Override
    public boolean deleteTouchpoint(long id) {
        return touchpointCRUD.deleteTouchpoint(id);
    }


    // for testing class loading
	private void logProductBundleKlass() {
		StringBuffer log = new StringBuffer();
		log.append(CrmProductBundle.class + "\n");
		ClassLoader cl = CrmProductBundle.class.getClassLoader();
		do {
			log.append("\t"+ cl + "\n");
			cl = cl.getParent();
		}
		while (cl != null);
		
		logger.info("class loader hierarchy of CrmProductBundle is: \n" + log);	
	}




}
