package com.example.cit.domain.achievement.playerAchievement.repository;

import com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 플레이어 업적 매핑테이블 jpa 리포지토리
public interface PlayerAchievementRepository extends JpaRepository<PlayerAchievement, Long> {
    Optional<PlayerAchievement> findByPlayerIdAndAchievementId(Long playerId, Long achievementId);

    List<PlayerAchievement> findByPlayerId(Long playerId);
}
