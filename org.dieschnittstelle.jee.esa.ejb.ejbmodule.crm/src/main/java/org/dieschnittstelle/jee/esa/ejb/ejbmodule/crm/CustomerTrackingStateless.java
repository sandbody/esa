package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.crud.CustomerTransactionCRUDLocal;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.shopping.CustomerTrackingLocal;
import org.dieschnittstelle.jee.esa.entities.crm.CustomerTransaction;
import org.apache.log4j.Logger;

/**
 * allows read/write access to a customer's shopping history
 */
@Stateless(name="customerTrackingSystem")
@Local(CustomerTrackingLocal.class)
public class CustomerTrackingStateless implements CustomerTrackingLocal, CustomerTrackingRemote {

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

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
