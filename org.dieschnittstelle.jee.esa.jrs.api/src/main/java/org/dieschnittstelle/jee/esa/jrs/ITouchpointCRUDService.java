package org.dieschnittstelle.jee.esa.jrs;

import org.dieschnittstelle.jee.esa.entities.crm.StationaryTouchpoint;

import javax.ws.rs.*;
import java.util.List;

@Path("/touchpoints")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ITouchpointCRUDService {
	
	@GET
	public List<StationaryTouchpoint> readAllTouchpoints();

	@GET
	@Path("/{touchpointId}")
	public StationaryTouchpoint readTouchpoint(@PathParam("touchpointId") long id);

	@POST
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint);
	
	@DELETE
	@Path("/{touchpointId}")
	public boolean deleteTouchpoint(@PathParam("touchpointId") long id); 
		
	/*
	 * UE JRS1: add a new annotated method for using the updateTouchpoint functionality of TouchpointCRUDExecutor and implement it
	 */

	/*
	 * Kein Pathparameter nötig, da der Korrelator im touchpoint zufinden sein muss.
	 * Grund hierfür ist die Tatasache, dass die POST-annotierte Methode ein  StationaryTouchpoint - Objekt liefert.
	 */
	@PUT
	public StationaryTouchpoint updateTouchpoint(StationaryTouchpoint touchpoint);
	
}
