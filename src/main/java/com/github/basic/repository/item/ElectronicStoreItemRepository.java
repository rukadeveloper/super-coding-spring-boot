package com.github.basic.repository.item;

import java.util.List;

public interface ElectronicStoreItemRepository {


    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    ItemEntity findById(String id);

    ItemEntity findByQuery(String id);

    void deleteItems(int id);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);

    void updateItemStock(Integer itemId, Integer minus);
}
