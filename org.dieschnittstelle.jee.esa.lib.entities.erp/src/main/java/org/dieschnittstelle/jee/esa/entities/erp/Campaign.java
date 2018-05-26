package org.dieschnittstelle.jee.esa.entities.erp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dieschnittstelle.org/jee/esa/entities/erp")
@Entity
public class Campaign extends AbstractProduct implements Serializable {

	private static Logger logger = Logger.getLogger(Campaign.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 4407600000386810001L;


	@OneToMany(cascade = CascadeType.ALL)
	private List<ProductBundle> bundles;

	public Campaign() {
		logger.info("<constructor>");
		this.bundles = new ArrayList<>();
	}

	public Campaign(String name) {
		super(name);
		this.bundles = new ArrayList<>();
	}

	public Collection<ProductBundle> getBundles() {
		return this.bundles;
	}

	public void setBundles(List<ProductBundle> bundles) {
		this.bundles = bundles;
	}

	public void addBundle(ProductBundle bundle) {
		this.bundles.add(bundle);
	}

    //	public boolean equals(Object other) {
//		return EqualsBuilder.reflectionEquals(this, other,
//				new String[] { "bundles" })
//				&& LangUtils
//						.setequals(this.bundles, ((Campaign) other).bundles);
//	}

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
        if (!(o instanceof Campaign)) return false;
        if (!super.equals(o)) return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(getBundles(), campaign.getBundles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBundles());
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "bundles=" + bundles +
                '}';
    }
}
