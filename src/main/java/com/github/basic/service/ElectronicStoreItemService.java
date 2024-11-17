package com.github.basic.service;

import com.github.basic.repository.ElectronicStoreItemRepository;
import com.github.basic.repository.ItemEntity;
import com.github.basic.web.dto.Item;
import com.github.basic.web.dto.ItemBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectronicStoreItemService {
    private ElectronicStoreItemRepository electronicStoreItemRepository;

    public ElectronicStoreItemService(ElectronicStoreItemRepository electronicStoreItemRepository) {
        this.electronicStoreItemRepository = electronicStoreItemRepository;
    }

    public List<Item> findAllItems() {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        List<Item> items = itemEntities.stream().map(Item::new).collect(Collectors.toList());
        return items;
    }

    public Integer saveItem(ItemBody itemBody) {
        ItemEntity itemEntity = new ItemEntity(null,itemBody.getName(),itemBody.getType(),
                                itemBody.getPrice(),itemBody.getSpec().getCpu(),itemBody.getSpec().getCapacity());

        return electronicStoreItemRepository.saveItem(itemEntity);
    }

    public Item findById(String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemRepository.findById(id);
        Item item = new Item(itemEntity);
        return item;
    }

    public List<Item> findItemsByIds(List<String> ids) {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        List<Item> items = itemEntities.stream().map(Item::new).filter(item -> ids.contains(item.getId())).collect(Collectors.toList());
        return items;
    }

    public void deleteItems(String id) {
        Integer idInt = Integer.parseInt(id);
        electronicStoreItemRepository.deleteItems(idInt);
    }

    public Item updateItemEntity(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntity = new ItemEntity(idInt,itemBody.getName(),itemBody.getType(),
                itemBody.getPrice(),itemBody.getSpec().getCpu(),itemBody.getSpec().getCapacity());
        ItemEntity itemEntityUpdated = electronicStoreItemRepository.updateItemEntity(idInt, itemEntity);
        Item itemUpdated = new Item(itemEntityUpdated);
        return itemUpdated;
    }
}
