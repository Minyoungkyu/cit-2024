package com.example.cit.domain.stage.stageRecord.controller;

import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.stage.stageRecord.service.StageRecordService;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/stageRecords", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1StageRecordController", description = "플레이어 스테이지 기록 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1StageRecordController {

    private final StageRecordService stageRecordService;
    private final Rq rq;

//    @GetMapping(value = "/last", consumes = ALL_VALUE)
//    @Operation(summary = "플레이어의 최고 스테이지 번호")
//    public RsData<Long> last() {
//        return stageRecordService.last(rq.getMember());
//    }



}
