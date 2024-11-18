package com.github.basic.web.controller;

import com.github.basic.service.ElectronicStoreItemService;
import com.github.basic.web.dto.BuyOrder;
import com.github.basic.web.dto.Item;
import com.github.basic.web.dto.ItemBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ElectronicStoreController {

    private final ElectronicStoreItemService electronicStoreItemService;

    private static int serialItemId = 1;

    @GetMapping("/items")
    public List<Item> findAllItem() {
        return electronicStoreItemService.findAllItems();
    }

    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {
        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID : " + itemId;
    }

    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id) {
        return electronicStoreItemService.findById(id);
    }

    @GetMapping("/items-query")
    public Item findItemByQueryId(@RequestParam("id") String id) {
        return electronicStoreItemService.findById(id);
    }

    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(@RequestParam("id") List<String> ids) {
        return electronicStoreItemService.findItemsByIds(ids);
    }

    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@PathVariable String id) {
      electronicStoreItemService.deleteItems(id);
      return "성공적인 삭제";
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody) {

        return electronicStoreItemService.updateItemEntity(id,itemBody);
    }

    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder) {
        Integer orderItemNums = electronicStoreItemService.buyItems(buyOrder);
        return "요청 하신 아이템 중 " + orderItemNums + "개를 구매하였습니다.";
    }

    @GetMapping("/items-types")
    public List<Item> findItemByTypes(
            @RequestParam("type") List<String> types
    ) {
        List<Item> items = electronicStoreItemService.findItemByTypes(types);
        return items;
    }

}
