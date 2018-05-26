package org.dieschnittstelle.jee.esa.ejb.client.ejbclients;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemRemote;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.List;

public class StockSystemClient implements StockSystemRemote {

	private StockSystemRemote proxy;
	
	public StockSystemClient() throws Exception {
		Context context = new InitialContext();

		this.proxy = (StockSystemRemote) context
				.lookup(StockSystemRemote.JNDI_PATH);
	}
	
	
	@Override
	public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
		this.proxy.addToStock(product, pointOfSaleId, units);
	}

	@Override
	public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId,
			int units) {
		this.proxy.removeFromStock(product, pointOfSaleId, units);
	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        return this.proxy.getProductsOnStock(pointOfSaleId);
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		return this.proxy.getAllProductsOnStock();
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
		return this.proxy.getUnitsOnStock(product, pointOfSaleId);
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		return this.proxy.getTotalUnitsOnStock(product);
	}

	@Override
	public List<Long> getPointsOfSale(IndividualisedProductItem product) {
		return this.proxy.getPointsOfSale(product);
	}


}
