package org.dieschnittstelle.jee.esa.entities.erp;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Objects;

/*
 * UE JRS3: entfernen Sie die Auskommentierung der Annotation
 */
@XmlSeeAlso({IndividualisedProductItem.class, Campaign.class})
@XmlType(namespace = "http://dieschnittstelle.org/jee/esa/entities/erp")
@Entity
public abstract class AbstractProduct implements Serializable, GenericCRUDEntity {
    /**
     *
     */
    private static final long serialVersionUID = 6940403029597060153L;

	private static Logger logger = Logger.getLogger(AbstractProduct.class);

  	@Id
    @GeneratedValue
	private long id;

	private String name;
	
	private int price;

	public AbstractProduct() {
		logger.info("<constructor>");
	}

	public AbstractProduct(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractProduct)) return false;
        AbstractProduct product = (AbstractProduct) o;
        return getId() == product.getId() &&
                getPrice() == product.getPrice() &&
                Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice());
    }

    @Override
    public String toString() {
        return "AbstractProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
