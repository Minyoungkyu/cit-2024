package com.example.cit.domain.item.item.service;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.item.repository.ItemRepository;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import com.example.cit.domain.item.itemParts.repository.ItemPartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item createItem(ItemParts itemParts, String name, String description, String availableCommands, String sourcePath, int price) {

        Item item = Item.builder()
                .itemParts(itemParts)
                .name(name)
                .description(description)
                .availableCommands(availableCommands)
                .sourcePath(sourcePath)
                .price(price)
                .build();

        itemRepository.save(item);

        return item;
    }

}
