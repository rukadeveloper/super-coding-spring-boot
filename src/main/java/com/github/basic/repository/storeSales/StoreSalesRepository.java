package com.github.basic.repository.storeSales;

public interface StoreSalesRepository {
    StoreSalesEntity findStoreSalesById(Integer storeId);

    void updateSalesAmount(Integer storeId, Integer i);
}
