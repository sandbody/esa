package org.dieschnittstelle.jee.esa.ejb.client.ejbclients;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.TouchpointAccessRemote;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.ejb.client.Constants;

public class TouchpointAccessClient implements TouchpointAccessRemote {
	
	private TouchpointAccessRemote touchpointAccessProxy;
	
	public TouchpointAccessClient() throws Exception {
		Context context = new InitialContext();
		this.touchpointAccessProxy = (TouchpointAccessRemote) context.lookup(Constants.TOUCHPOINT_ACCESS_BEAN);
	}
	
	
	public List<AbstractTouchpoint> readAllTouchpoints() {
		return touchpointAccessProxy.readAllTouchpoints();
	}

	@Override
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {
		AbstractTouchpoint created = touchpointAccessProxy.createTouchpoint(touchpoint);
		touchpoint.setId(created.getId());
		touchpoint.setErpPointOfSaleId(created.getErpPointOfSaleId());
		
		return created;
	}


    public boolean deleteTouchpoint( long id) {
        return touchpointAccessProxy.deleteTouchpoint(id);
    }
}
