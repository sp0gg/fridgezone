package net.sp0gg.fridgezone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;

    @Column(name = "stock_level")
    private String stockLevel;

    @Column(name = "optimal_quantity")
    private int optimalQuantity;

    @JsonIgnore
    private String username;

    @OneToMany(mappedBy="item", fetch=FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

    public Long getId() {return id;}
    public void setId(Long id) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String toString(){
        return String.format("Item[id=%d, name=%s, level=%s, user=%s], tags[%s]", id, name, stockLevel, username, this.getTags().toString());
    }

    public boolean containsTagName(String tagName) {
        for (Tag tag : this.getTags()) {
            if(tag.getName().equalsIgnoreCase(tagName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        Item item = (Item)obj;
        return this.getName() == item.getName()
                && this.getStockLevel() == item.getStockLevel()
                && this.getTags().equals(item.getTags());
    }
}
