package org.dieschnittstelle.jee.esa.wsv.client.service;

import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;

import javax.ws.rs.*;
import java.util.List;

@Path("/touchpoints")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ITouchpointCRUDService {
	
	@GET
	public List<AbstractTouchpoint> readAllTouchpoints();

	@GET
	@Path("/{touchpointId}")
	public AbstractTouchpoint readTouchpoint(@PathParam("touchpointId") long id);

	@POST
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint);
	
	@DELETE
	@Path("/{touchpointId}")
	public boolean deleteTouchpoint(@PathParam("touchpointId") long id);

//	@PUT
//	@Path("/{touchpointId}")
//	public AbstractTouchpoint updateTouchpoint(@PathParam("touchpointId") long id, AbstractTouchpoint touchpoint);

    // No Id needed, id is in Object... Server-Side-Method-Signature hasn't a parameter
    @PUT
    public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint touchpoint);

}
