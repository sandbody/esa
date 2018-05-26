package org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.shopping;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.*;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.crm.exceptions.VerifyCampaignException;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.ProductCRUDBean;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.ProductCRUDLocal;
import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.StockSystemBean;
import org.dieschnittstelle.jee.esa.entities.crm.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.entities.crm.CrmProductBundle;
import org.dieschnittstelle.jee.esa.entities.crm.Customer;
import org.dieschnittstelle.jee.esa.entities.crm.CustomerTransaction;
import org.dieschnittstelle.jee.esa.entities.erp.AbstractProduct;
import org.dieschnittstelle.jee.esa.entities.erp.Campaign;
import org.dieschnittstelle.jee.esa.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.jee.esa.entities.erp.ProductBundle;

import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.util.List;

/**
 * Created by pentax on 19.01.17.
 */
@Remote(ShoppingSessionFacadeRemote.class)
@Stateful
public class ShoppingSessionBean implements ShoppingSessionFacadeRemote {

    protected static Logger logger = Logger
            .getLogger(ShoppingSessionBean.class);

    @EJB
    private ShoppingCartLocal shoppingCart;

    @EJB
    private CustomerTrackingLocal customerTracking;

    @EJB
    private CampaignTrackingLocal campaignTracking;


    @EJB
    ProductCRUDLocal productCRUDBean;

    @EJB
    StockSystemBean stockSystemBean;
    /**
     * the customer
     */
    private Customer customer;

    /**
     * the touchpoint
     */
    private AbstractTouchpoint touchpoint;

    public void setTouchpoint(AbstractTouchpoint touchpoint) {
        this.touchpoint = touchpoint;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addProduct(AbstractProduct product, int units) {
        this.shoppingCart.addProductBundle(new CrmProductBundle(product.getId(), units, product instanceof Campaign));
    }

    /*
     * verify whether campaigns are still valid
     */
    public void verifyCampaigns() throws VerifyCampaignException {
        if (this.customer == null || this.touchpoint == null) {
            throw new RuntimeException("cannot verify campaigns! No touchpoint has been set!");
        }

        for (CrmProductBundle productBundle : this.shoppingCart.getProductBundles()) {
            if (productBundle.isCampaign()) {
                int availableCampaigns = this.campaignTracking.existsValidCampaignExecutionAtTouchpoint(
                        productBundle.getErpProductId(), this.touchpoint);
                logger.info("got available campaigns for product " + productBundle.getErpProductId() + ": "
                        + availableCampaigns);
                // we check whether we have sufficient campaign items available
                if (availableCampaigns < productBundle.getUnits()) {
                    throw new VerifyCampaignException("verifyCampaigns() failed for productBundle " + productBundle
                            + " at touchpoint " + this.touchpoint + "! Need " + productBundle.getUnits()
                            + " instances of campaign, but only got: " + availableCampaigns);
                }
            }
        }
    }

    public void purchase() throws VerifyCampaignException{
        try {
            logger.info("purchase()");

            if (this.customer == null || this.touchpoint == null) {
                throw new RuntimeException(
                        "cannot commit shopping session! Either customer or touchpoint has not been set: " + this.customer
                                + "/" + this.touchpoint);
            }

            // verify the campaigns
            verifyCampaigns();


            // remove the products from stock
            checkAndRemoveProductsFromStock();

            // then we add a new customer transaction for the current purchase
            doCustomerTracking(true);

            resetLocalVariables();

            logger.info("purchase(): done.\n");
        }catch(VerifyCampaignException e){
            throw e;
        }catch (Exception e){
            throw  new ShoppingException("An exception occurred: "+ e);
        }

    }


    private void doCustomerTracking(boolean completed){
        List<CrmProductBundle> products = this.shoppingCart.getProductBundles();
        if(products.isEmpty()) {
            return;
        }
        CustomerTransaction transaction = new CustomerTransaction(this.customer, this.touchpoint, products);
        transaction.setCompleted(completed);
        customerTracking.createTransaction(transaction);
    }

    private void resetLocalVariables(){
        customer = null;
        touchpoint = null;
        shoppingCart.getProductBundles().clear();
    }
    /*
     * to be implemented as server-side method for PAT2
     */
    private void checkAndRemoveProductsFromStock() {
        logger.info("checkAndRemoveProductsFromStock");

        for (CrmProductBundle productBundle : this.shoppingCart.getProductBundles()) {
            long erpProductId = productBundle.getErpProductId();
            AbstractProduct abp= productCRUDBean.readProduct(erpProductId);
            int units = productBundle.getUnits();
            logger.info("units of "+ units);
            if(Campaign.class.isInstance(abp)){
            //if (productBundle.isCampaign()) {
                this.campaignTracking.purchaseCampaignAtTouchpoint(erpProductId, this.touchpoint,
                        productBundle.getUnits());
                logger.info("find Campaign with id "+ productBundle.getErpProductId());
                // wenn Sie eine Kampagne haben, muessen Sie hier
                // 1) zunaechst das Campaign-Objekt anhand der erpProductId von productBundle auslesen
                Campaign campaign = (Campaign) abp;
                // 2) dann ueber die ProductBundle Objekte auf dem Campaign Objekt iterieren und
                for(ProductBundle pb :campaign.getBundles()) {
                    // 3) fuer jedes ProductBundle das betreffende Produkt in der auf dem Bundle angegebenen Anzahl, multipliziert mit dem Wert von
                    // productBundle.getUnits() aus dem Warenkorb,
                    // - hinsichtlich Verfuegbarkeit ueberpruefen, und
                    // - falls verfuegbar aus dem Warenlager entfernen
                  int buys =   pb.getUnits()*productBundle.getUnits();
                    IndividualisedProductItem ivp = (IndividualisedProductItem)pb.getProduct();

                    stockSystemBean.removeFromStock(ivp, this.touchpoint.getErpPointOfSaleId(), buys);
                }
                // (Anm.: productBundle.getUnits() sagt Ihnen, wie oft ein Produkt, im vorliegenden Fall eine Kampagne, im
                // Warenkorb liegt)
            } else {
                // andernfalls (wenn keine Kampagne vorliegt) muessen Sie
                // 1) das Produkt (dann IndividualisedProductItem) anhand der erpProductId von productBundle auslesen, und

                IndividualisedProductItem ivp = (IndividualisedProductItem)abp;
                //int stockUnits = stockSystemBean.getTotalUnitsOnStock(ivp);
                stockSystemBean.removeFromStock(ivp, this.touchpoint.getErpPointOfSaleId(), units);


                /*
                int stockUnits = stockSystemBean.getTotalUnitsOnStock(ivp);
                // 2) das Produkt in der in productBundle.getUnits() angegebenen Anzahl hinsichtlich Verfuegbarkeit ueberpruefen und
*/
                // 3) das Produkt, falls verfuegbar, aus dem Warenlager entfernen

                // Schritt 1) koennen Sie ggf. auch mit Typ AbstractProduct vor
                // die if/else Verzweigung bezueglich isCampaign() platzieren -
                // in jedem Fall benoetigen Sie hierfuer Zugriff auf Ihre
                // ProductCRUD EJB
            }

        }
    }



    @PreDestroy
    private void checkTransactionFinished(){
        logger.info("  @PreDestroy : checkTransactionFinished");
        doCustomerTracking(false);

    }
}
