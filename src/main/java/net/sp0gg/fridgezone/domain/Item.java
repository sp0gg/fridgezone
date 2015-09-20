package net.sp0gg.fridgezone.domain;

import javax.persistence.*;
import java.util.List;

;

@Entity(name="item")
public class Item {
	
	@Id
    @GeneratedValue
	private long id;
	
	private String name;
    @Column(name = "stock_level")
    private String stockLevel;
    @Column(name = "optimal_quantity")
    private int optimalQuantity;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="itemId")
    private List<Tag> tags;

    public long getId() {return id;}
    public void setId(long id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    public String getStockLevel() {
        return stockLevel;
    }
    public void setStockLevel(String stockLevel) {
        this.stockLevel = stockLevel;
    }
    public int getOptimalQuantity() {
        return optimalQuantity;
    }

    public void setOptimalQuantity(int optimalQuantity) {
        this.optimalQuantity = optimalQuantity;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String toString(){
        return String.format("Item[id=%d, name='%s']", id, name);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, item.id)
                .append(name, item.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }
}
