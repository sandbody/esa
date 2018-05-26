package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.Remote;
//import javax.ejb.Remote;
import javax.jws.WebService;
import javax.ws.rs.*;

import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;

@Remote
@WebService
@Path("/touchpoints")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface TouchpointAccessRemote {

	@POST
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint);

	@GET
	public List<AbstractTouchpoint> readAllTouchpoints();


    @DELETE
   @Path("/{touchpointId}")
   public boolean deleteTouchpoint(@PathParam("touchpointId") long id);
}
