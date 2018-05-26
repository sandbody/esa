package org.dieschnittstelle.jee.esa.entities.erp;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.RequestWrapper;
import java.io.Serializable;
import java.util.Objects;

@XmlType(namespace = "http://dieschnittstelle.org/jee/esa/entities/erp")
@Entity
public class IndividualisedProductItem extends AbstractProduct implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5109263395081656350L;

	private static Logger logger = Logger.getLogger(IndividualisedProductItem.class);


	@Enumerated(EnumType.STRING)
	private ProductType productType;

	private int expirationAfterStocked;


	public IndividualisedProductItem() {
		logger.info("<constructor>");
	}

	public IndividualisedProductItem(String name,ProductType type,int expirationAfterStocked) {
		super(name);
		this.productType = type;
		this.expirationAfterStocked = expirationAfterStocked;
	}
	
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	public int getExpirationAfterStocked() {
		return expirationAfterStocked;
	}

	public void setExpirationAfterStocked(int expirationAfterStocked) {
		this.expirationAfterStocked = expirationAfterStocked;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndividualisedProductItem)) return false;
        if (!super.equals(o)) return false;
        IndividualisedProductItem that = (IndividualisedProductItem) o;
        return getExpirationAfterStocked() == that.getExpirationAfterStocked() &&
                getProductType() == that.getProductType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductType(), getExpirationAfterStocked());
    }

    @Override
    public String toString() {
        return "IndividualisedProductItem{" +
                "productType=" + productType +
                ", expirationAfterStocked=" + expirationAfterStocked +
                '}';
    }
}
