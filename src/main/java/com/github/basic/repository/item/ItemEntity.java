package com.github.basic.repository.item;

import com.github.basic.web.dto.ItemBody;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name="item")
public class ItemEntity {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "capacity")
    private String capacity;

    public ItemEntity(Integer id, String name, String type, Integer price, String cpu, String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.storeId = null;
        this.stock = 0;
        this.cpu = cpu;
        this.capacity = capacity;
    }

    public void setItemBody(ItemBody itemBody) {
        this.name = itemBody.getName();
        this.type = itemBody.getType();
        this.price = itemBody.getPrice();
        this.cpu = itemBody.getSpec().getCpu();
        this.capacity = itemBody.getSpec().getCapacity();
    }
}
