package com.github.basic.service.Mapper;

import com.github.basic.repository.item.ItemEntity;
import com.github.basic.web.dto.Item;
import com.github.basic.web.dto.ItemBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    // 메소드
    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);

    @Mapping(target = "cpu", source = "itemBody.spec.cpu")
    @Mapping(target = "capacity", source = "itemBody.spec.capacity")
    @Mapping(target = "storeId", ignore = true)
    @Mapping(target = "stock", expression = "java(0)")
    ItemEntity idAndItemBodyToItemEntity(Integer id, ItemBody itemBody);
}
