package com.github.basic.web.controller;

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

    private static int serialItemId = 1;

    private List<Item> items = new ArrayList<Item>(Arrays.asList(
            new Item(String.valueOf(serialItemId++),"Apple IPhone","SmartPhone",149000,"A14 Bionic","150GB"),
            new Item(String.valueOf(serialItemId++),"Apple IPhone","SmartPhone",149000,"A14 Bionic","150GB")
    ));

    @GetMapping("/items")
    public List<Item> findAllItem() {
        return items;
    }

    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {
        Item newItem = new Item(serialItemId++, itemBody);
        items.add(newItem);
        return "ID: " + newItem.getId();
    }

    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id) {
        Item itemFounded = items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException());

        return itemFounded;
    }

    @GetMapping("/items-query")
    public Item findItemByQueryId(@RequestParam("id") String id) {
        Item itemFounded = items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException());

        return itemFounded;
    }

    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(@RequestParam("id") List<String> ids) {

        Set<String> idSet = ids.stream().collect(Collectors.toSet());

        List<Item> itemFounded = items.stream().filter(item -> idSet.contains(item.getId()))
                .collect(Collectors.toList());

        return itemFounded;

    }

    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@PathVariable String id) {
        Item itemFounded = items.stream().filter(item -> item.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException());
        items.remove(itemFounded);
        return "Object with Id = " + itemFounded.getId() + " has been deleted";
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@RequestBody ItemBody itemBody, @PathVariable String id) {
        Item itemFounded = items.stream().filter(item -> item.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException());
        items.remove(itemFounded);

        Item itemUpdated = new Item(Integer.valueOf(id), itemBody);
        items.add(itemUpdated);
        return itemUpdated;
    }
}
