package com.example.cit.domain.program.program.controller;

import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.service.ProgramService;
import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.global.app.AppConfig;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.KwTypeV1;
import com.example.cit.standard.base.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/programs", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ProgramController", description = "사업관리 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1ProgramController {

    private final ProgramService programService;
    private final Rq rq;

    public record GetProgramsResponseBody(@NonNull PageDto<ProgramDto> itemPage) {
    }

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "사업 조회")
    public RsData<GetProgramsResponseBody> getPrograms(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") KwTypeV1 kwType
    ) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<Program> itemPage = programService.findByKw(kwType, kw, pageable);

        Page<ProgramDto> _itemPage = itemPage.map(ProgramDto::new);

        return RsData.of(
                new GetProgramsResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }


}
