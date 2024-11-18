package com.github.basic.service;

import com.github.basic.repository.item.ElectronicStoreItemRepository;
import com.github.basic.repository.item.ItemEntity;
import com.github.basic.repository.storeSales.StoreSalesEntity;
import com.github.basic.repository.storeSales.StoreSalesRepository;
import com.github.basic.web.dto.BuyOrder;
import com.github.basic.web.dto.Item;
import com.github.basic.web.dto.ItemBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectronicStoreItemService {
    private ElectronicStoreItemRepository electronicStoreItemRepository;
    private StoreSalesRepository storeSalesRepository;

    public ElectronicStoreItemService(ElectronicStoreItemRepository electronicStoreItemRepository, StoreSalesRepository storeSalesRepository) {
        this.electronicStoreItemRepository = electronicStoreItemRepository;
        this.storeSalesRepository = storeSalesRepository;
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

    @Transactional(transactionManager = "tm1")
    public Integer buyItems(BuyOrder buyOrder) {

        // buyOrder에서 상품 ID와 수량 얻어내기
        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        // 상품을 조회하여 수량이 얼마나 있는지 확인한다.
        ItemEntity itemEntity = electronicStoreItemRepository.findById(String.valueOf(itemId));

        // 단 재고가 없거나 수량이 아예 없으면 살수 없다.
        if(itemEntity.getStoreId() == null) throw new RuntimeException("매장을 찾을 수 없습니다.");
        if(itemEntity.getStock() <= 0) throw new RuntimeException("상품의 재고가 없습니다.");

        Integer successByItemNums;
        if(itemNums >= itemEntity.getStock()) successByItemNums = itemEntity.getStock();
        else successByItemNums = itemNums;

        // 상품의 수량과 가격을 가지고 총 값을 계산한다.
        Integer totalPrice = successByItemNums * itemEntity.getPrice();

        if(successByItemNums == 4) throw new RuntimeException("4개를 구매하는 것은 허락하지 않습니다.");

        // 상품의 재고에 기존 계산한 재고를 구매하는 수량을 뺀다.
        Integer minus = itemEntity.getStock() - successByItemNums;
        electronicStoreItemRepository.updateItemStock(itemId, minus);

        // 매장의 매상에 추가
        StoreSalesEntity storeSales = storeSalesRepository.findStoreSalesById(itemEntity.getStoreId());
        storeSalesRepository.updateSalesAmount(itemEntity.getStoreId(), storeSales.getAmount() + totalPrice);

        return successByItemNums;
    }
}
