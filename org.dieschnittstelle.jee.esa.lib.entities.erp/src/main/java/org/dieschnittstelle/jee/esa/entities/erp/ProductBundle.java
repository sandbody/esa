package org.dieschnittstelle.jee.esa.entities.erp;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dieschnittstelle.org/jee/esa/entities/erp")
@Entity
public class ProductBundle implements Serializable {


	private static final long serialVersionUID = 1501911067906145681L;

    private static Logger logger = Logger.getLogger(ProductBundle.class);


    @Id
    @GeneratedValue
	private long id;

	@ManyToOne
	private IndividualisedProductItem product;

	private int units;

	public ProductBundle() {
		logger.info("<constructor>");
	}

	public ProductBundle(IndividualisedProductItem product, int units) {
		this.product = product;
		this.units = units;
	}

	public IndividualisedProductItem getProduct() {
		return this.product;
	}

	public void setProduct(IndividualisedProductItem product) {
		this.product = product;
	}

	public int getUnits() {
		return this.units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    @PostLoad
	public void onPostLoad() {
		logger.info("@PostLoad: " + this);
	}

	@PostPersist
	public void onPostPersist() {
		logger.info("@PostPersist: " + this);
	}

	@PostRemove
	public void onPostRemove() {
		logger.info("@PostRemove: " + this);
	}

	@PostUpdate
	public void onPostUpdate() {
		logger.info("@PostUpdate: " + this);
	}

	@PrePersist
	public void onPrePersist() {
		logger.info("@PrePersist: " + this);
	}

	@PreRemove
	public void onPreRemove() {
		logger.info("@PreRemove: " + this);
	}

	@PreUpdate
	public void onPreUpdate() {
		logger.info("@PreUpdate: " + this);
	}

    @Override
    public String toString() {
        return "ProductBundle{" +
                "id=" + id +
                ", product=" + product +
                ", units=" + units +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductBundle)) return false;
        ProductBundle that = (ProductBundle) o;
        return getId() == that.getId() &&
                getUnits() == that.getUnits() &&
                Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getUnits());
    }
}
