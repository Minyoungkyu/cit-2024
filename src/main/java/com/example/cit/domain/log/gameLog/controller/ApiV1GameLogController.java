package com.example.cit.domain.log.gameLog.controller;

import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.log.dto.GameLogDto;
import com.example.cit.domain.log.gameLog.entity.GameLog;
import com.example.cit.domain.log.gameLog.service.GameLogService;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.program.program.controller.ApiV1ProgramController;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.global.app.AppConfig;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.KwTypeV1;
import com.example.cit.standard.base.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/gameLogs", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1GameLogController", description = "게임 로그 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1GameLogController {

    private final GameLogService gameLogService;
    private final Rq rq;
    public record BatchGameLogRequestBody(
            @NonNull GameMapDto gameMapDto,
            @NonNull int result,
            @NonNull int editorAutoComplete,
            @NonNull int editorAutoClose,
            @NonNull int killCount
    ) {}

    @PostMapping(value = "/batchGameLog", consumes = ALL_VALUE)
    @Operation(summary = "게임 로그 일괄 처리")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public void batchPlayLog(
            @RequestBody BatchGameLogRequestBody body
    ) {
        gameLogService.batchGameLog(rq.getMember(), body.gameMapDto, body.result, body.editorAutoComplete, body.editorAutoClose, body.killCount);
    }

    public record GetStatLogResponseBody(@NonNull PageDto<GameLogDto> itemPage) {
    }

    @GetMapping(value = "/stat", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "통계 조회")
    public RsData<GetStatLogResponseBody> getStatLog(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(name = "programId") long programId,
            @RequestParam(name = "schoolId") long schoolId,
            @RequestParam(name = "grade") int grade,
            @RequestParam(name = "startDateTime") LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime") LocalDateTime endDateTime
    ) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<GameLog> itemPage = gameLogService.getStatLogs(programId, schoolId, grade, startDateTime, endDateTime, pageable);

        Page<GameLogDto> _itemPage = itemPage.map(GameLogDto::new);

        return RsData.of(
                new GetStatLogResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }
}
