package com.example.cit.domain.stage.stageRecord.service;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.stage.stageRecord.repository.StageRecordRepository;
import com.example.cit.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StageRecordService {

    private final StageRecordRepository stageRecordRepository;


//    public RsData<Long> last(Member member) {
//        return RsData.of(
//                "조회 성공",
//                stageRecordRepository.findTop1ByPlayerIdAndIsClearFalseOrderByIdDesc(member.getId()).getStage().getId()
//        );
//    }
}
