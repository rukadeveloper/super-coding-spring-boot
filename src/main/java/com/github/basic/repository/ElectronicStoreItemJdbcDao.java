package com.github.basic.repository;

import com.github.basic.web.dto.ItemBody;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ElectronicStoreItemJdbcDao implements ElectronicStoreItemRepository{

    private JdbcTemplate jdbcTemplate;

    static RowMapper<ItemEntity> itemEntityRowMapper = ((rs, rowNum)
            -> new ItemEntity(rs.getInt("id"),rs.getNString("name"),
               rs.getNString("type"), rs.getInt("price"),
               rs.getNString("cpu"),rs.getNString("capacity"))) ;

    public ElectronicStoreItemJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ItemEntity> findAllItems() {
        return jdbcTemplate.query("SELECT * FROM item",itemEntityRowMapper);
    }

    @Override
    public Integer saveItem(ItemEntity itemEntity) {
        jdbcTemplate.update("INSERT INTO item(name, type, price, cpu, capacity) VALUES(?,?,?,?,?)",
                            itemEntity.getName(),itemEntity.getType(),itemEntity.getPrice()
                            ,itemEntity.getCpu(),itemEntity.getCapacity());

        ItemEntity itemEntityFound = jdbcTemplate.queryForObject("SELECT * FROM item WHERE name = ?",
                                itemEntityRowMapper,itemEntity.getName());

        return itemEntityFound.getId();
    }

    @Override
    public ItemEntity findById(String id) {
        ItemEntity itemEntity = jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?"
                                ,itemEntityRowMapper,id);
        return itemEntity;

    }

    @Override
    public ItemEntity findByQuery(String id) {
        ItemEntity itemEntity = jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?",
                itemEntityRowMapper,id);
        return itemEntity;
    }

    @Override
    public void deleteItems(int id) {
        jdbcTemplate.update("DELETE FROM item WHERE id = ?",id);
    }

    @Override
    public ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity) {
        jdbcTemplate.update("UPDATE item " +
                            "SET name = ?, type = ?, price = ?, cpu = ?, capacity = ? " +
                            "WHERE id = ?",
                            itemEntity.getName(),itemEntity.getType(),itemEntity.getPrice(),
                            itemEntity.getCpu(),itemEntity.getCapacity(),idInt);

        return jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?",itemEntityRowMapper,idInt);
    }

}
