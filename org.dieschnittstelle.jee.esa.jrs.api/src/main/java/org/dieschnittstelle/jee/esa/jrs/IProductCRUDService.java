package org.dieschnittstelle.jee.esa.jrs;

import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;

import javax.ws.rs.*;
import java.util.List;

/*
 * UE JRS2: 
 * deklarieren Sie hier Methoden fuer:
 * - die Erstellung eines Produkts
 * - das Auslesen aller Produkte
 * - das Auslesen eines Produkts
 * - die Aktualisierung eines Produkts
 * - das Loeschen eines Produkts
 * und machen Sie diese Methoden mittels JAX-RS Annotationen als WebService verfuegbar
 */

/*
 * UE JRS3: aendern Sie Argument- und Rueckgabetypen der Methoden von IndividualisedProductItem auf AbstractProduct
 */

@Path("/products")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface IProductCRUDService {

	@POST
	public AbstractProduct createProduct(AbstractProduct prod);


	// JAX-RS-DEFAULT-Einstellung. Daher wird keine expliziete Pfadangabe benötigt, wenn es nicht nötig ist!
	@GET
	public List<AbstractProduct> readAllProducts();

	/*
	 * Kein Pathparameter nötig, da der Korrelator im touchpoint zufinden sein muss.
	 * Grund hierfür ist die Tatasache, dass die POST-annotierte Methode ein  IndividualisedProductItem - Objekt liefert.
	 */
	@PUT
	public AbstractProduct updateProduct(
            AbstractProduct update);

	@DELETE
	@Path("/{id}")
	boolean deleteProduct(@PathParam("id") long id);

	@GET
	@Path("/{id}")
	public AbstractProduct readProduct(@PathParam("id") long id);
			
}
