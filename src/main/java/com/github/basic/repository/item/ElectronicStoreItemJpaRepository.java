package com.github.basic.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectronicStoreItemJpaRepository extends JpaRepository<ItemEntity, Integer> {

    List<ItemEntity> findItemEntitiesByTypeIn(List<String> types);
}
