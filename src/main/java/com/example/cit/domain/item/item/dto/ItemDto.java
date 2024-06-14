package com.example.cit.domain.item.item.dto;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

// 게임 플레이어 장비 DTO
public record ItemDto(
        @NonNull Long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime updateDate,
        @NonNull Long itemPartsId,
        @NonNull String name,
        @NonNull int price,
        @NonNull String description,
        @NonNull String availableCommands,
        @NonNull String sourcePath
) {
    public ItemDto(Item item) {
        this(
                item.getId(),
                item.getCreateDate(),
                item.getModifyDate(),
                item.getItemParts().getId(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getAvailableCommands(),
                item.getSourcePath()
        );
    }
}
