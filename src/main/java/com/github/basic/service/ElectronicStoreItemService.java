package com.github.basic.service;

import com.github.basic.repository.item.ElectronicStoreItemJpaRepository;
import com.github.basic.repository.item.ElectronicStoreItemRepository;
import com.github.basic.repository.item.ItemEntity;
import com.github.basic.repository.storeSales.StoreSalesEntity;
import com.github.basic.repository.storeSales.StoreSalesJpaRepository;
import com.github.basic.repository.storeSales.StoreSalesRepository;
import com.github.basic.service.Mapper.ItemMapper;
import com.github.basic.service.exceptions.NotFoundException;
import com.github.basic.web.dto.BuyOrder;
import com.github.basic.web.dto.Item;
import com.github.basic.web.dto.ItemBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectronicStoreItemService {
    private final ElectronicStoreItemJpaRepository electronicStoreItemJpaRepository;
    private final StoreSalesJpaRepository storeSalesJpaRepository;

    public List<Item> findAllItems() {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll();
        List<Item> items = itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
        return items;
    }

    public Integer saveItem(ItemBody itemBody) {
        ItemEntity itemEntity = ItemMapper.INSTANCE.idAndItemBodyToItemEntity(null,itemBody);
        ItemEntity itemEntityCreated;
        itemEntityCreated = electronicStoreItemJpaRepository.save(itemEntity);

        return itemEntityCreated.getId();
    }

    public Item findById(String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(idInt).orElseThrow(() -> new NotFoundException("해당 ID의 제품이 없습니다." + idInt) );
        Item item = new Item(itemEntity);
        return item;
    }

    public List<Item> findItemsByIds(List<String> ids) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll();
        if(itemEntities.isEmpty()) {
            throw new NotFoundException("아무 아이템들을 찾을 수 없습니다.");
        }
        List<Item> items = itemEntities.stream().map(Item::new).filter(item -> ids.contains(item.getId())).collect(Collectors.toList());
        return items;
    }

    public void deleteItems(String id) {
        Integer idInt = Integer.parseInt(id);
        electronicStoreItemJpaRepository.deleteById(idInt);
    }

    @Transactional(transactionManager = "tmJpa1")
    public Item updateItemEntity(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntityUpdated = electronicStoreItemJpaRepository.findById(idInt).orElseThrow(()-> new NotFoundException("찾을 수 없습니다."));
        itemEntityUpdated.setItemBody(itemBody);
        Item itemUpdated = ItemMapper.INSTANCE.itemEntityToItem(itemEntityUpdated);
        return itemUpdated;
    }

    @Transactional(transactionManager = "tmJpa1")
    public Integer buyItems(BuyOrder buyOrder) {

        // buyOrder에서 상품 ID와 수량 얻어내기
        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        // 상품을 조회하여 수량이 얼마나 있는지 확인한다.
        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(itemId).orElseThrow(
                ()-> new NotFoundException("해당 이름의 아이템이 없습니다")
        );

        // 단 재고가 없거나 수량이 아예 없으면 살수 없다.
        if(itemEntity.getStoreSales().isEmpty()) throw new RuntimeException("매장을 찾을 수 없습니다.");
        if(itemEntity.getStock() <= 0) throw new RuntimeException("상품의 재고가 없습니다.");

        Integer successByItemNums;
        if(itemNums >= itemEntity.getStock()) successByItemNums = itemEntity.getStock();
        else successByItemNums = itemNums;

        // 상품의 수량과 가격을 가지고 총 값을 계산한다.
        Integer totalPrice = successByItemNums * itemEntity.getPrice();

        if(successByItemNums == 4) throw new RuntimeException("4개를 구매하는 것은 허락하지 않습니다.");

        // 상품의 재고에 기존 계산한 재고를 구매하는 수량을 뺀다.
        Integer minus = itemEntity.getStock() - successByItemNums;
        itemEntity.setStock(minus);

        // 매장의 매상에 추가
        StoreSalesEntity storeSales = itemEntity.getStoreSales().orElseThrow(
                () -> new NotFoundException("요청하신 Store에 해당하는 스토어 상품이 없습니다.")
        );
        storeSales.setAmount(storeSales.getAmount() + totalPrice);

        return successByItemNums;
    }

    public List<Item> findItemByTypes(List<String> types) {
        List<ItemEntity> entities = electronicStoreItemJpaRepository.findItemEntitiesByTypeIn(types);
        return entities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }
}
