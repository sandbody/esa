package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm;

import java.util.List;

import javax.ejb.Remote;

import org.dieschnittstelle.jee.esa.entities.crm.CustomerTransaction;

@Remote
public interface CustomerTrackingRemote {

    public void createTransaction(CustomerTransaction transaction);

    public List<CustomerTransaction> readAllTransactions();

}
