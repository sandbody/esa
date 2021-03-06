package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.crud.CustomerTransactionCRUDLocal;
import org.dieschnittstelle.jee.esa.entities.crm.CustomerTransaction;
import org.apache.log4j.Logger;

/**
 * allows read/write access to a customer's shopping history
 */
@Stateless(name="customerTrackingSystem")
public class CustomerTrackingStateless implements CustomerTrackingRemote {

	protected static Logger logger = Logger
			.getLogger(CustomerTrackingStateless.class);

	/**
	 * we use the local interface to the CustomerTransactionCRUD
	 */
	@EJB
	private CustomerTransactionCRUDLocal customerTransactionCRUD;
	
	
	public CustomerTrackingStateless() {
		logger.info("<constructor>: " + this);
	}

	public void createTransaction(CustomerTransaction transaction) {
		logger.info("createTransaction(): " + transaction);
		
		customerTransactionCRUD.createTransaction(transaction);
	}
	
	public List<CustomerTransaction> readAllTransactions() {
		//return transactions;
		return new ArrayList<CustomerTransaction>();
	}

	@PostConstruct
	public void initialise() {
		logger.info("@PostConstruct: customerTransactionCRUD is: " + customerTransactionCRUD);
	}

	@PreDestroy
	public void finalise() {
		logger.info("@PreDestroy");
	}

}
