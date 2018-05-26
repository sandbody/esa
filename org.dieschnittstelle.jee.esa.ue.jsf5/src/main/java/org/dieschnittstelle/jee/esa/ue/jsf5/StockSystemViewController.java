package org.dieschnittstelle.jee.esa.ue.jsf5;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.TouchpointAccessLocal;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemLocal;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.entities.erp.PointOfSale;
import org.dieschnittstelle.jee.esa.entities.erp.StockItem;
import org.apache.log4j.Logger;
// declare a stocksystem local interface

/* declare the class as named component using CDI annotations */  
@Named("vc")
/* TODO declare the class as application scoped using the CDI annotation */
public class StockSystemViewController {

	protected static Logger logger = Logger
			.getLogger(StockSystemViewController.class);

	/*
	 * TODO declare a dependency to the stock system ejb via a new local interface,
	 * using the @Resource annotation and the mappedName:
	 * java:global/org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle
	 * .jee.esa
	 * .ejb.ejbmodule.erp/StockSystemSingleton!org.dieschnittstelle.jee
	 * .esa.ejb.ejbmodule.erp.StockSystemLocal
	 */
    @Resource(lookup = "java:global/org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp/StockSystemSingleton!org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.StockSystemLocal")
    private StockSystemLocal stockSystemLocal;

	/*
	 * use the helper bean - this is needed for JSF6
	 */
	@Inject
	private StockSystemHelper stockSystemHelper;

	/*
	 * TODO: later on (after only displaying the list), we also need access to the
	 * touchpoints ejb, using the @Resource annotation and the mappedName:
	 * java:global/org.dieschnittstelle.jee
	 * .esa.ejb/org.dieschnittstelle.jee.esa
	 * .ejb.ejbmodule.crm/TouchpointAccessStateless
	 * !org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.TouchpointAccessLocal
	 */
    @Resource(mappedName = "java:global/org.dieschnittstelle.jee.esa.ejb/org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm/TouchpointAccessStateless!org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.TouchpointAccessLocal")
    private TouchpointAccessLocal touchpointAccess;

	/*
	 * these are local structures created from the data read out from the beans
	 */
	/* the map of stock items */
	private Map<String, StockItemWrapper> stockItemsMap = new TreeMap<String, StockItemWrapper>();

	/* a map of touchpoints using the erpPointOfSaleId as key */
	private Map<Integer, AbstractTouchpoint> touchpointsMap = new HashMap<Integer, AbstractTouchpoint>();

	/*
	 * TODO: return the values of the stockItemsMap. Note that you might need to create a new Collection, e.g. ArrayList to add the values
	 */
	public Collection<StockItemWrapper> getStockItems() {
		return null;
	}

	/*
	 * the action method for updating the units of a stockItem
	 */
	public String updateUnits() {
		/*
		 * TODO: we accesss the parameters from the FacesContext.getCurrentInstance()
		 * .getExternalContext().getRequestParameterMap()
		 */
      Map<String, String> requestMap =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		/* TODO: read out the parameter(s) that we need */

		/*
		 * TODO: we read out the StockItemWrapper from the local map
		 */

		/*
		 * TODO: we use the units diff on StockItemWrapper for determining the number
		 * of units to add and call the add method on stockSystem
		 */

		/*
		 * TODO: once we are done we call the updateMethods on the item to set the new
		 * value of units on the StockItem object itself
		 */

		/* returning the empty string here results in keeping the current view */
		return "";
	}
	
	/*
	 * TODO: add a method for updating the price of a stock item
	 */
	
	/*
	 * TODO: add a method that calls the doShopping() method on stockSystemHelper
	 */

	/*
	 * a method for refreshing the view
	 */
	public void refreshView() {
		logger.info("refreshView()");
		// we call loadData
		loadData();
	}

	/*
	 * a method loadData() that loads all stock items and uses some wrapper
	 * classes to provide an integrated view on the data enriching the items
	 * with touchpoint names from crm
	 * 
	 * TODO: should be called once this bean is created
	 */
	public void loadData() {

		logger.info("@postConstruct: helper is: " + stockSystemHelper);

		// TODO: remove the comments (you might start with the first for statement and keep the second one commented until access to the touchpoints bean is done)
//		this.stockItemsMap.clear();
//
//		/*
//		 * read out the stock items and create the stock items and touchpoints
//		 * map
//		 */
//		for (StockItem item : stockSystem.getCompleteStock()) {
//			StockItemWrapper wrapper = new StockItemWrapper(item);
//			this.stockItemsMap.put(wrapper.getId(), wrapper);
//		}
//
//		/* also read out the touchpoints */
//		for (AbstractTouchpoint tp : touchpointBean.readAllTouchpoints()) {
//			if (!this.touchpointsMap.containsKey(tp.getErpPointOfSaleId())) {
//				this.touchpointsMap.put(tp.getErpPointOfSaleId(), tp);
//			}
//		}
	}

	/*
	 * an inner class that is wrapped around a stock item and provides the data
	 * we need to display it and handle updates
	 * 
	 * you might use this one or implement an individual solution
	 */
	public class StockItemWrapper {

		private StockItem item;

		/*
		 * the units that will be set via the gui, which we need to keep
		 * distinct from the actual units of the StockItem
		 */
		private int units;

		/*
		 * use an id that combines the ids of touchpoint and product -- this is
		 * required for retrieving an item from the list
		 */
		public String getId() {
			return item.getPos().getId() + "_" + item.getProduct().getId();
		}

		/* constructor */
		public StockItemWrapper(StockItem item) {
			this.item = item;
			this.units = item.getUnits();
		}

		public String getTouchpointName() {
			return touchpointsMap.get(item.getPos().getId()).getName();
		}

		public PointOfSale getPos() {
			return item.getPos();
		}

		public int getPrice() {
			if (this.item.getPrice() > 0) {
				return this.item.getPrice();
			}
			return this.item.getProduct().getPrice();
		}

		public void setPrice(int price) {
			logger.info("setPrice(): " + price);
			this.item.setPrice(price);
		}

		public IndividualisedProductItem getProduct() {
			return (IndividualisedProductItem) this.item.getProduct();
		}

		public int getUnits() {
			return this.units;
		}

		public void setUnits(int units) {
			this.units = units;
		}

		public int getUnitsDiff() {
			return this.units - this.item.getUnits();
		}

		/*
		 * update the units on the item after successful stock item update on
		 * the ejb
		 */
		public void updateUnits() {
			this.item.setUnits(this.units);
		}

	}

}
