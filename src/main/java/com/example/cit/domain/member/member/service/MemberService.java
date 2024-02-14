package com.example.cit.domain.member.member.service;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.repository.MemberRepository;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rsData.RsData;
import com.example.cit.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    @Transactional
    public RsData<Member> join(String username, String password) {
        return join(username, password, username, 1 );
    }

    @Transactional
    public RsData<Member> join(String username, String password, String nickname, int roleLevel) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .refreshToken(authTokenService.genRefreshToken())
                .nickname(nickname)
                .roleLevel(roleLevel)
                .build();
        memberRepository.save(member);

        return RsData.of("회원가입이 완료되었습니다.".formatted(member.getUsername()), member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findByUsernameByMember(String username) {
        return memberRepository.findByUsernameAndRoleLevel(username, 1);
    }

    public Optional<Member> findByUsernameByAdmin(String username) {
        return memberRepository.findByUsernameAndRoleLevelGreaterThanEqual(username, 2);
    }

    public long count() {
        return memberRepository.count();
    }

    @Transactional
    public RsData<Member> modifyOrJoin(String username, String providerTypeCode, String nickname) {
        Member member = findByUsername(username).orElse(null);

        if (member == null) {
            return join(
                    username, "", nickname, 1
            );
        }

        return modify(member, nickname);
    }

    @Transactional
    public RsData<Member> modify(Member member, String nickname) {
        member.setNickname(nickname);

        return RsData.of("회원정보가 수정되었습니다.".formatted(member.getUsername()), member);
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public void setName(Member member, String nickname) {
        member.setNickname(nickname);
    }

    public record AuthAndMakeTokensResponseBody(
            @NonNull Member member,
            @NonNull String accessToken,
            @NonNull String refreshToken
    ) {
    }

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> memberLogin(String username, String password) {
        return authAndMakeTokens(
                findByUsernameByMember(username)
                        .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다.")),
                username,
                password
        );
    }

    @Transactional
    public RsData<AuthAndMakeTokensResponseBody> adminLogin(String username, String password) {
        return authAndMakeTokens(
                findByUsernameByAdmin(username)
                        .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다.")),
                username,
                password
        );
    }

    public RsData<AuthAndMakeTokensResponseBody> authAndMakeTokens(Member member, String username, String password) {

        if (!passwordMatches(member, password))
            throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");

        String refreshToken = member.getRefreshToken();
        String accessToken = authTokenService.genAccessToken(member);

        return RsData.of(
                "%s님 안녕하세요.".formatted(member.getUsername()),
                new AuthAndMakeTokensResponseBody(member, accessToken, refreshToken)
        );
    }

    @Transactional
    public String genAccessToken(Member member) {
        return authTokenService.genAccessToken(member);
    }

    public boolean passwordMatches(Member member, String password) {
        return passwordEncoder.matches(password, member.getPassword());
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = authTokenService.getDataFrom(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<String> authorities = (List<String>) payloadBody.get("authorities");

        return new SecurityUser(
                id,
                username,
                "",
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }

    public boolean validateToken(String token) {
        return authTokenService.validateToken(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 리프레시 토큰입니다."));

        String accessToken = authTokenService.genAccessToken(member);

        return RsData.of("200-1", "토큰 갱신 성공", accessToken);
    }
}
