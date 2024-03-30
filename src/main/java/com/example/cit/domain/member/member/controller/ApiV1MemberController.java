package com.example.cit.domain.member.member.controller;

import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/members", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1MemberController", description = "회원 CRUD 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;


    public record LoginRequestBody(@NotNull int roleLevel, @NotBlank String username, @NotBlank String password) {
    }

    public record LoginResponseBody(@NonNull MemberDto item) {
    }

    @PostMapping(value = "/login")
    @Operation(summary = "로그인, accessToken, refreshToken 쿠키 생성됨")
    public RsData<LoginResponseBody> login(@Valid @RequestBody LoginRequestBody body) {

        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs;

        if (body.roleLevel == 1) {
            authAndMakeTokensRs = memberService.memberLogin(
                    body.username,
                    body.password
            );

        } else {
            authAndMakeTokensRs = memberService.adminLogin(
                    body.username,
                    body.password
            );
        }

        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().refreshToken());
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().accessToken());

        return authAndMakeTokensRs.newDataOf(
                new LoginResponseBody(
                        new MemberDto(authAndMakeTokensRs.getData().member())
                )
        );
    }

    public record AdminLoginRequestBody(@NotBlank String username, @NotBlank String password) {}

    @PostMapping(value = "/admin/login")
    @Operation(summary = "관리자 로그인, accessToken, refreshToken 쿠키 생성됨")
    public RsData<LoginResponseBody> adminLogin(@Valid @RequestBody AdminLoginRequestBody body) {
        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.adminLogin(
                body.username,
                body.password
        );

        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().refreshToken());
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().accessToken());

        return authAndMakeTokensRs.newDataOf(
                new LoginResponseBody(
                        new MemberDto(authAndMakeTokensRs.getData().member())
                )
        );
    }

    public record MeResponseBody(@NonNull MemberDto item) {
    }

    @GetMapping(value = "/me", consumes = ALL_VALUE)
    @Operation(summary = "내 정보")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<MeResponseBody> getMe() {
        return RsData.of(
                new MeResponseBody(
                        new MemberDto(rq.getMember())
                )
        );
    }

    public record AdminCheckPasswordRequestBody(@NotBlank String password) {}

    @PostMapping(value = "/admin/checkPassword")
    @Operation(summary = "관리자 비밀번호 확인")
    public RsData<LoginResponseBody> adminCheckPassword(@Valid @RequestBody AdminCheckPasswordRequestBody body) {
        return RsData.of(
                new LoginResponseBody(
                        new MemberDto(memberService.checkPassword(rq.getMember().getId(), body.password))
                )
        );
    }

    @PostMapping(value = "/logout", consumes = ALL_VALUE)
    @Operation(summary = "로그아웃")
    public RsData<Empty> logout() {
        rq.setLogout();

        return RsData.of("로그아웃 성공");
    }

    public record ModifyRequestBody(@NotBlank String oldPassword, @NotBlank String newPassword, @NotBlank String nickname, @NotBlank String cellphoneNo) {}
    public record ModifyResponseBody(@NonNull MemberDto item) {}

    @PutMapping("/{id}/modify")
    @Operation(summary = "관리자정보 수정")
    @PreAuthorize("hasRole('CLASSADMIN')")
    @Transactional
    public RsData<ModifyResponseBody> modify(
            @PathVariable("id") long id,
            @Valid @RequestBody ModifyRequestBody body
    ) {
        RsData<Member> modifyRs = memberService.modify(
                id,
                body.oldPassword,
                body.newPassword,
                body.nickname,
                body.cellphoneNo
        );

        return modifyRs.newDataOf(
                new ModifyResponseBody(
                        new MemberDto(modifyRs.getData())
                )
        );
    }


}