package com.example.cit.domain.achievement.achievement.repository;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.achievement.achievement.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 업적 jpa 리포지토리
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Optional<Achievement> findByConditionTypeAndConditionDetail(String conditionType, long conditionDetail);

    Optional<Achievement> findFirstByConditionTypeAndConditionDetailLessThanEqualOrderByConditionDetailDesc(String conditionType, long conditionDetail);
}
