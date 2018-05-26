package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.ShoppingException;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.apache.log4j.Logger;

@Stateless
public class TouchpointCRUDStateless implements TouchpointCRUDRemote,
		TouchpointCRUDLocal {

	protected static Logger logger = Logger
			.getLogger(TouchpointCRUDStateless.class);

	@PersistenceContext(unitName = "crm_erp_PU")
	private EntityManager em;

	/*
	 * UE ADD1: run CreateTouchoints in the non-ws client project with the @TransactionAttribute commented in - what happens?
	 */
	@Override
	//@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public AbstractTouchpoint createTouchpoint(AbstractTouchpoint touchpoint) {

		/*
		 * UE ADD1: swap true/false
		 */		
		if (/*true*/false) {
			throw new ShoppingException(
					ShoppingException.ShoppingSessionExceptionReason.UNKNOWN);
		} else {
			logger.info("createTouchpoint(): before persist(): " + touchpoint);
			em.persist(touchpoint);

			logger.info("createTouchpoint(): after persist(): " + touchpoint);

			return touchpoint;
		}

	}

	@Override
	public AbstractTouchpoint readTouchpoint(long id) {
		logger.info("readTouchpoint(): " + id);

		AbstractTouchpoint touchpoint = em.find(AbstractTouchpoint.class, id);

		logger.info("readTouchpoint(): " + touchpoint);

		return touchpoint;
	}

	@Override
	public AbstractTouchpoint updateTouchpoint(AbstractTouchpoint touchpoint) {
		logger.info("updateTouchpoint(): before merge(): " + touchpoint);
		touchpoint = em.merge(touchpoint);

		logger.info("updateTouchpoint(): after merge(): " + touchpoint);
		return touchpoint;
	}

	@Override
	public boolean deleteTouchpoint(long id) {
		logger.info("deleteTouchpoint(): " + id);

		em.remove(em.find(AbstractTouchpoint.class, id));

		logger.info("deleteTouchpoint(): done");

		return true;
	}

	@Override
	public List<AbstractTouchpoint> readAllTouchpoints() {
		logger.info("readAllTouchpoints()");

		Query query = em.createQuery("SELECT tp FROM AbstractTouchpoint tp");

		List<AbstractTouchpoint> tps = (List<AbstractTouchpoint>) query
				.getResultList();

		logger.info("readAllTouchpoints(): " + tps);

		return tps;
	}

}
