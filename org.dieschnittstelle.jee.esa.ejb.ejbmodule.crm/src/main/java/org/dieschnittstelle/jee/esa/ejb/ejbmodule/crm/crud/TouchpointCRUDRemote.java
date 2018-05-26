package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.crud;

import java.util.List;

import javax.ejb.Remote;

import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;

@Remote
public interface TouchpointCRUDRemote {
	
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint Touchpoint);

	public AbstractTouchpoint readTouchpoint(long id);

	public List<AbstractTouchpoint> readAllTouchpoints();
	
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint Touchpoint);
		
	public boolean deleteTouchpoint(long id);

}
