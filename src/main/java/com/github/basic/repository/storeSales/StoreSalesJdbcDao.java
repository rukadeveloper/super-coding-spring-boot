package com.github.basic.repository.storeSales;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StoreSalesJdbcDao implements StoreSalesRepository {

    private JdbcTemplate jdbcTemplate;

    public StoreSalesJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static RowMapper<StoreSalesEntity> storeSalesEntityRowMapper = ((rs, rowNum) ->
            new StoreSalesEntity.StoreSalesEntityBuilder().id(rs.getInt("id"))
                    .storeName(rs.getNString("store_name")).amount(rs.getInt("amount"))
                    .build()
    );

    @Override
    public StoreSalesEntity findStoreSalesById(Integer storeId) {
        return jdbcTemplate.queryForObject("SELECT * FROM store_sales WHERE id = ?", storeSalesEntityRowMapper, storeId);
    }

    @Override
    public void updateSalesAmount(Integer storeId, Integer i) {
        jdbcTemplate.update("UPDATE store_sales SET amount = ? WHERE id = ?", i, storeId);
    }
}
