package com.github.basic.repository.storeSales;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@Entity
@Table(name="store_sales")
public class StoreSalesEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="store_name")
    private String storeName;

    @Column(name = "amount")
    private Integer amount;

}
