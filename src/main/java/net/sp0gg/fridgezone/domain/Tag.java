package net.sp0gg.fridgezone.domain;

import javax.persistence.*;

/**
 * Created by sp0gg on 9/19/15.
 */

@Entity(name="tag")
public class Tag {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Column(name = "item_id")
    private long itemId;

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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
