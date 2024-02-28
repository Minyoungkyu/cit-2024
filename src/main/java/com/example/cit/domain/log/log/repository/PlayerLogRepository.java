package com.example.cit.domain.log.log.repository;

import com.example.cit.domain.log.log.entity.PlayerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerLogRepository extends JpaRepository<PlayerLog, Long> {
}
