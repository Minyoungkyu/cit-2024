package com.example.cit.domain.player.player.dto;

import com.example.cit.domain.player.player.entity.Player;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;

public record PlayerDto(
        @NonNull long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime modifyDate,
        @NonNull String nickname
) {
    public PlayerDto(Player player) {
        this(player.getId(), player.getCreateDate(), player.getModifyDate(), player.getNickname());
    }
}
