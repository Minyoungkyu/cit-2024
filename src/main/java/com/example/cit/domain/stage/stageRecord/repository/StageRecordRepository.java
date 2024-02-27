package com.example.cit.domain.stage.stageRecord.repository;

import com.example.cit.domain.stage.stageRecord.entity.StageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRecordRepository extends JpaRepository<StageRecord, Long>{

    StageRecord findTop1ByPlayerIdAndIsClearFalseOrderByIdDesc(Long playerId);
}
