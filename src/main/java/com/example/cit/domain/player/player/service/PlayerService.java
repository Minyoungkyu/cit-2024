package com.example.cit.domain.player.player.service;

import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.player.player.repository.PlayerRepository;
import com.example.cit.global.exceptions.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional
    public Player setNickName(long id, String nickname) {

        return findPlayerByMemberId(id)
            .map(player -> {
                player.setNickname(nickname);
                return player;
            })
            .orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 플레이어입니다."));
    }

    public Optional<Player> findPlayerByMemberId(long id) {
        return playerRepository.findByMemberId(id);
    }
}
