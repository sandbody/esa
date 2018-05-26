package org.dieschnittstelle.jee.esa.entities.erp;

import javax.persistence.*;

import org.apache.log4j.Logger;

import java.util.Objects;

@Entity
@Table(name = "stock")
@IdClass(ProductAtPosPK.class)
public class StockItem {

	private static Logger logger = Logger.getLogger(StockItem.class);

	@Id
	@ManyToOne
	private PointOfSale pos;

	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private IndividualisedProductItem product;

	private int price;
	
	private int units;

	public StockItem() {
		logger.info("<constructor>");
	}

	public StockItem(IndividualisedProductItem product,
			PointOfSale pos, int units) {
		this.product = product;
		this.pos = pos;
		this.units = units;
	}

	public PointOfSale getPos() {
		return pos;
	}

	public void setPos(PointOfSale pos) {
		this.pos = pos;
	}

	public IndividualisedProductItem getProduct() {
		return product;
	}

	public void setProduct(IndividualisedProductItem product) {
		this.product = product;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/*
	 * the lifecycle log messages
	 */

	@PostLoad
	public void onPostLoad() {
		logger.info("onPostLoad(): " + this);
	}

	@PostPersist
	public void onPostPersist() {
		logger.info("onPostPersist(): " + this);
	}

	@PostRemove
	public void onPostRemove() {
		logger.info("onPostRemove(): " + this);
	}

	@PostUpdate
	public void onPostUpdate() {
		logger.info("onPostUpdate(): " + this);
	}

	@PrePersist
	public void onPrePersist() {
		logger.info("onPrePersist(): " + this);
	}

	@PreRemove
	public void onPreRemove() {
		logger.info("onPreRemove(): " + this);
	}

	@PreUpdate
	public void onPreUpdate() {
		logger.info("onPreUpdate(): " + this);
	}

    public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockItem)) return false;
        StockItem stockItem = (StockItem) o;
        return getPrice() == stockItem.getPrice() &&
                getUnits() == stockItem.getUnits() &&
                Objects.equals(getPos(), stockItem.getPos()) &&
                Objects.equals(getProduct(), stockItem.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPos(), getProduct(), getPrice(), getUnits());
    }
}
