package com.example.cit.domain.program.program.controller;

import com.example.cit.domain.log.log.controller.ApiV1PlayerLogController;
import com.example.cit.domain.member.member.controller.ApiV1MemberController;
import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.dto.ProgramDetailDto;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.service.ProgramService;
import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.global.app.AppConfig;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import com.example.cit.standard.base.KwTypeV1;
import com.example.cit.standard.base.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    public record GetProgramResponseBody(@NonNull ProgramDetailDto item) {}

    @GetMapping(value = "/{id}", consumes = ALL_VALUE)
    @Operation(summary = "사업 단건 조회")
    public RsData<GetProgramResponseBody> getProgram(
            @PathVariable("id") Long id
    ) {
        return RsData.of(
                new GetProgramResponseBody(
                        new ProgramDetailDto(
                                programService.getProgramById(id)
                        )
                )
        );
    }

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

    public record createProgramRequestBody(
            @NonNull String name,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate,
            @NonNull String region,
            @NonNull String ad,
            @NonNull List<SchoolInputListDto> agency,
            @NonNull List<MemberInputListDto> member
    ) {}

    public record createProgramResponseBody(@NonNull ProgramDto program) {}

    @PostMapping(value = "/new", consumes = ALL_VALUE)
    @Operation(summary = "사업 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<createProgramResponseBody> batchPlayLog(
            @RequestBody createProgramRequestBody body
    ) {
        return RsData.of( "사업이 생성되었습니다.",
                new createProgramResponseBody(
                        new ProgramDto(
                                programService.createProgram(
                                        body.name(),
                                        body.startDate(),
                                        body.endDate(),
                                        body.region(),
                                        body.ad(),
                                        body.agency(),
                                        body.member()
                                )
                        )
                )
        );
    }

    public record ModifyProgramRequestBody(
            @NonNull Long id,
            @NonNull String name,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate,
            @NonNull String region,
            @NonNull String ad,
            @NonNull List<SchoolInputListDto> agency,
            @NonNull List<MemberInputListDto> member
    ) {}
    public record ModifyProgramResponseBody(@NonNull ProgramDto programDto) {}

    @PutMapping("/modify")
    @Operation(summary = "사업 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ModifyProgramResponseBody> modify(
            @Valid @RequestBody ModifyProgramRequestBody body
    ) {
        return RsData.of( "사업이 수정되었습니다.",
                new ModifyProgramResponseBody(
                        new ProgramDto(
                                programService.modifyProgram(
                                        body.id,
                                        body.name(),
                                        body.startDate(),
                                        body.endDate(),
                                        body.region(),
                                        body.ad(),
                                        body.agency(),
                                        body.member()
                                )
                        )
                )
        );
    }

    public record ProgramDeleteRequestBody(@NonNull List<Long> programIds) {}

    @PostMapping("/delete")
    @Operation(summary = "사업 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> delete(
            @Valid @RequestBody ProgramDeleteRequestBody body
    ) {
        programService.deletePrograms(body.programIds());

        return RsData.of("사업이 삭제되었습니다.");
    }

}
