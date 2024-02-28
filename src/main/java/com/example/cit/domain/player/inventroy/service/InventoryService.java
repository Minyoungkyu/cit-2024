package com.example.cit.domain.player.inventroy.service;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.repository.PlayerLogRepository;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.repository.InventoryRepository;
import com.example.cit.domain.player.player.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public void createInventory(Player player, Item item, boolean isEquipped) {

        Inventory inventory = Inventory.builder()
                .player(player)
                .item(item)
                .isEquipped(isEquipped)
                .build();

        inventoryRepository.save(inventory);
    }


}
