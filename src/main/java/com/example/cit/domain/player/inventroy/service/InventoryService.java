package com.example.cit.domain.player.inventroy.service;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.AuthTokenService;
import com.example.cit.domain.player.inventroy.dto.InventoryDto;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.repository.InventoryRepository;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final Rq rq;
    private final AuthTokenService authTokenService;

    @Transactional
    public void createInventory(Player player, Item item, boolean isEquipped) {

        Inventory inventory = Inventory.builder()
                .player(player)
                .item(item)
                .isEquipped(isEquipped)
                .build();

        inventoryRepository.save(inventory);
    }

    public List<Inventory> getMyInventoryList(Member member) {
        return inventoryRepository.findByPlayer(member.getPlayer());
    }


    @Transactional
    public void updateInventory(List<InventoryDto> inventoryDtoList, Member member) {
        List<Inventory> myCurrentInventoryList = member.getPlayer().getInventories();

        Map<Long, InventoryDto> dtoMap = inventoryDtoList.stream()
                .collect(Collectors.toMap(dto -> dto.item().id(), dto -> dto));

        myCurrentInventoryList.stream()
                .filter(inv -> dtoMap.containsKey(inv.getItem().getId()) &&
                        dtoMap.get(inv.getItem().getId()).isEquipped() != inv.isEquipped())
                .forEach(inv -> inv.setEquipped(dtoMap.get(inv.getItem().getId()).isEquipped()));
    }
}
