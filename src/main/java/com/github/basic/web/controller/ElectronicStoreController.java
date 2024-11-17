package com.github.basic.web.controller;

import com.github.basic.repository.ElectronicStoreItemRepository;
import com.github.basic.repository.ItemEntity;
import com.github.basic.service.ElectronicStoreItemService;
import com.github.basic.web.dto.Item;
import com.github.basic.web.dto.ItemBody;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {

    private ElectronicStoreItemService electronicStoreItemService;

    public ElectronicStoreController(ElectronicStoreItemService service) {
        this.electronicStoreItemService = service;
    }

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

}
