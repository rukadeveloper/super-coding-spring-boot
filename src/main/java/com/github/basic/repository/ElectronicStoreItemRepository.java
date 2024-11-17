package com.github.basic.repository;

import com.github.basic.web.dto.Item;
import com.github.basic.web.dto.ItemBody;

import java.util.List;

public interface ElectronicStoreItemRepository {


    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    ItemEntity findById(String id);

    ItemEntity findByQuery(String id);

    void deleteItems(int id);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);
}
