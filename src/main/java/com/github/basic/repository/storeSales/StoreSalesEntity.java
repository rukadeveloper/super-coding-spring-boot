package com.github.basic.repository.storeSales;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Builder
public class StoreSalesEntity {

    private Integer id;
    private String storeName;
    private Integer amount;

}
