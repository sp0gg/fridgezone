package net.sp0gg.fridgezone.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;;import java.math.BigDecimal;

@Entity(name="item")
public class Item {
	
	@Id
    @GeneratedValue
	private long id;
	
	private String name;
    private BigDecimal quantity;

    public String toString(){
		return String.format("Item[id=%d, name='%s']", id, name);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, item.id)
                .append(quantity, item.quantity)
                .append(name, item.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(quantity)
                .toHashCode();
    }
}
