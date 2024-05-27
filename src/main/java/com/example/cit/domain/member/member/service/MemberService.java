package com.example.cit.domain.member.member.service;

import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.member.member.controller.ApiV1MemberController;
import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.dto.MemberProgramAdmDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.repository.MemberRepository;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import com.example.cit.domain.player.inventroy.service.ProfileInventoryService;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.dto.ProgramInputDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.repository.ProgramRepository;
import com.example.cit.domain.program.program.service.ProgramService;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rsData.RsData;
import com.example.cit.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sqids.Sqids;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;
    private final PlayerLogService playerLogService;
    private final ProgramService programService;
    private final ProgramRepository programRepository;
    private final SchoolService schoolService;

    @Transactional
    public RsData<Member> join(String username, String password, int roleLevel) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(encodePasswordForAdmin(roleLevel, password))
                .refreshToken(authTokenService.genRefreshToken())
                .roleLevel(roleLevel)
                .build();

        memberRepository.save(member);

        Player player = Player.builder()
                .member(member)
                .nickname("")
                .exp(0)
                .gems(0)
                .build();

        member.setPlayer(
                Player
                        .builder()
                        .member(member)
                        .nickname("")
                        .exp(0)
                        .gems(0)
                        .build()
        );


        playerLogService.setFirstGame(member);

        return RsData.of("회원가입이 완료되었습니다.".formatted(member.getUsername()), member);
    }
    @Transactional
    public RsData<Member> join(String username, String password, String name, String cellphoneNo, int roleLevel, String department, String position, String extensionNo) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(encodePasswordForAdmin(roleLevel, password))
                .refreshToken(authTokenService.genRefreshToken())
                .name(name)
                .cellphoneNo(cellphoneNo)
                .roleLevel(roleLevel)
                .department(department)
                .position(position)
                .extensionNo(extensionNo)
                .build();

        memberRepository.save(member);

        member.setPlayer(
                Player
                        .builder()
                        .member(member)
                        .nickname("")
                        .exp(0)
                        .gems(0)
                        .build()
        );

        playerLogService.setFirstGame(member);

        return RsData.of("회원가입이 완료되었습니다.".formatted(member.getUsername()), member);
    }

    @Transactional
    public RsData<Member> join(String username, String password, String name, String cellphoneNo, String department, String position, String extensionNo, List<ProgramInputDto> programs, int roleLevel) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(encodePasswordForAdmin(roleLevel, password))
                .refreshToken(authTokenService.genRefreshToken())
                .name(name)
                .cellphoneNo(cellphoneNo)
                .roleLevel(roleLevel)
                .department(department)
                .position(position)
                .extensionNo(extensionNo)
//                .programs(
//                        programs.stream()
//                                .map(program_ -> programService.getProgramById(program_.getId()))
//                                .collect(Collectors.toList())
//                )
                .build();

        programs.stream()
                .map(program_ -> programService.getProgramById(program_.getId()))
                .forEach(program -> program.getMembers().add(member));

        memberRepository.save(member);

        member.setPlayer(
                Player
                        .builder()
                        .member(member)
                        .nickname("")
                        .exp(0)
                        .gems(0)
                        .build()
        );

        playerLogService.setFirstGame(member);

        return RsData.of("회원가입이 완료되었습니다.".formatted(member.getUsername()), member);
    }

    @Transactional
    public RsData<Member> joinClassAdmin(String username, String password, String name, String cellphoneNo, List<SchoolInputListDto> schools, int roleLevel) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(encodePasswordForAdmin(roleLevel, password))
                .refreshToken(authTokenService.genRefreshToken())
                .name(name)
                .cellphoneNo(cellphoneNo)
                .roleLevel(roleLevel)

                .schools(
                        schools.stream()
                                .map(school_ -> schoolService.getSchoolById(school_.id()))
                                .collect(Collectors.toList())
                )
                .build();

//        schools.stream()
//                .map(school_ -> schoolService.getSchoolById(school_.id()))
//                .forEach(school -> school.getMembers().add(member));

        memberRepository.save(member);

        member.setPlayer(
                Player
                        .builder()
                        .member(member)
                        .nickname("")
                        .exp(0)
                        .gems(0)
                        .build()
        );

        playerLogService.setFirstGame(member);

        return RsData.of("회원가입이 완료되었습니다.".formatted(member.getUsername()), member);
    }

    public RsData<Member> joinStudent(long schoolClassId, int studentYear, int studentNumber, String password) {
        SchoolClass schoolClass = schoolService.getSchoolClassById(schoolClassId);
        String yearLastTwo = String.valueOf(studentYear).substring(2);
        String formattedNumber = String.format("%03d", studentNumber);
        String username = "%s%s%s".formatted(schoolClass.getCode(), yearLastTwo, formattedNumber);

        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(password)
                .refreshToken(authTokenService.genRefreshToken())
                .roleLevel(1)
                .studentClass(schoolClass)
                .build();

//        schools.stream()
//                .map(school_ -> schoolService.getSchoolById(school_.id()))
//                .forEach(school -> school.getMembers().add(member));

        memberRepository.save(member);

        member.setPlayer(
                Player
                        .builder()
                        .member(member)
                        .nickname("")
                        .exp(0)
                        .gems(0)
                        .build()
        );

        playerLogService.setFirstGame(member);

        return RsData.of("회원가입이 완료되었습니다.".formatted(member.getUsername()), member);
    }

    private String encodePasswordForAdmin(int roleLevel, String password) {
        if (roleLevel == 1) {
            return password;
        }
        return passwordEncoder.encode(password);
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

//    @Transactional
//    public RsData<Member> modifyOrJoin(String username, String providerTypeCode, String nickname) {
//        Member member = findByUsername(username).orElse(null);
//
//        if (member == null) {
//            return join(
//                    username, "", nickname, "", 1
//            );
//        }
//
//        return modify(member, nickname);
//    }

    @Transactional
    public RsData<Member> modify(long id, String password, String name, String cellphoneNo, String department, String position, String extensionNo) {
        return memberRepository.findById(id)
                .map(member -> {
                    if (password != null && !password.isEmpty()) {
                        member.setPassword(passwordEncoder.encode(password));
                    }
                    member.setName(name);
                    member.setCellphoneNo(cellphoneNo);
                    member.setDepartment(department);
                    member.setPosition(position);
                    member.setExtensionNo(extensionNo);
                    return member;
                })
                .map(member -> RsData.of("회원정보가 저장되었습니다.", member))
                .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));
    }

    @Transactional
    public RsData<Member> modify(long id, String password, String name, String cellphoneNo, String department, String position, String extensionNo, List<ProgramInputDto> programs) {
        return memberRepository.findById(id)
                .map(member -> {
                    if (password != null && !password.isEmpty()) {
                        member.setPassword(passwordEncoder.encode(password));
                    }
                    member.setName(name);
                    member.setCellphoneNo(cellphoneNo);
                    member.setDepartment(department);
                    member.setPosition(position);
                    member.setExtensionNo(extensionNo);

                    programs.stream()
                            .map(program_ -> programService.getProgramById(program_.getId()))
                            .forEach(program -> program.getMembers().add(member));

                    return member;
                })
                .map(member -> RsData.of("회원정보가 저장되었습니다.", member))
                .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));
    }

    public Optional<Member> findById(long id) {
        return memberRepository.findById(id);
    }

    public Member checkPassword(long memberId, String password) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않습니다."));
        if (!passwordMatches(member, password)) {
            throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    public MemberProgramAdmDto makeProgramAdmDto(Member member) {
        List<ProgramDto> programDtoList = programService.getPrograms(member).stream()
                .map(ProgramDto::new)
                .toList();

        return new MemberProgramAdmDto(member, programDtoList);
    }

    public List<MemberInputListDto> getProgramMembers() {
        return memberRepository.findByRoleLevelGreaterThanEqual(3);
    }

    public boolean duplicate(String username) {
        return memberRepository.existsByUsername(username);
    }

    public record AuthAndMakeTokensResponseBody(
            @NonNull Member member,
            @NonNull String accessToken,
            @NonNull String refreshToken
    ) {}

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

        if(member.getRoleLevel() == 1) {
            if(!member.getPassword().equals(password))
                throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");
        } else {
            if (!passwordMatches(member, password))
                throw new GlobalException("400-2", "비밀번호가 일치하지 않습니다.");
        }

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

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }


    public Page<Member> findByKw(String kwType, String kw, Pageable pageable, int roleLevel) {
        return memberRepository.findByKw(kwType, kw, pageable, roleLevel);
    }

    @Transactional
    public Member createMember(ApiV1MemberController.createSystemAdminRequestBody body, int roleLevel) {

        //if member with same agencyId, same grade, same classNo and member that isSpecial is false already exists, throw exception
//        if (memberRepository.existsBySchoolIdAndGradeAndClassNoAndIsSpecial(agencyId, grade, classNo, isSpecial, specialName )) {
////            throw new IllegalStateException("Member with same agencyId, same grade, same classNo and isSpecial is false already exists");
//            return null;
//        }


        Member member = Member.builder()
                .username(body.username())
                .password(encodePasswordForAdmin(roleLevel, body.password()))
                .name(body.name())
                .cellphoneNo(body.cellphoneNo())
                .roleLevel(roleLevel)
                .department(body.department())
                .position(body.position())
                .extensionNo(body.extensionNo())
                .build();

        body.programs().forEach(program_ -> {
            Program program = programService.getProgramById(program_.getId());
            member.getPrograms().add(program);
        });

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public Member modifySystemAdminMember(ApiV1MemberController.ModifySystemAdminRequestBody body) {
        Member member = memberRepository.findById(body.id()).orElseThrow();

//        if (memberRepository.existsBySchoolIdAndGradeAndClassNoAndIsSpecial(member.getSchool().getId(), grade, classNo, isSpecial, specialName )) {
////            throw new IllegalStateException("Member with same agencyId, same grade, same classNo and isSpecial is false already exists");
//            return null;
//        }

        if(body.password() != null && !body.password().isEmpty())
            member.setPassword(encodePasswordForAdmin(member.getRoleLevel(), body.password()));
        member.setName(body.name());
        member.setCellphoneNo(body.cellphoneNo());
        member.setDepartment(body.department());
        member.setPosition(body.position());
        member.setExtensionNo(body.extensionNo());

        body.programs().stream()
                .map(program_ -> programService.getProgramById(program_.getId()))
                .forEach(program -> program.getMembers().add(member));

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public Member modifyClassAdminMember(ApiV1MemberController.ModifyClassAdminRequestBody body) {
        Member member = memberRepository.findById(body.id()).orElseThrow();

//        if (memberRepository.existsBySchoolIdAndGradeAndClassNoAndIsSpecial(member.getSchool().getId(), grade, classNo, isSpecial, specialName )) {
////            throw new IllegalStateException("Member with same agencyId, same grade, same classNo and isSpecial is false already exists");
//            return null;
//        }

        if(body.password() != null && !body.password().isEmpty())
            member.setPassword(encodePasswordForAdmin(member.getRoleLevel(), body.password()));
        member.setName(body.name());
        member.setCellphoneNo(body.cellphoneNo());

        body.schools().stream()
                .map(school_ -> schoolService.getSchoolById(school_.id()))
                .forEach(school_ -> member.getSchools().add(school_));

        memberRepository.save(member);

        return member;
    }

    public Member modifyStudentMember(ApiV1MemberController.ModifyStudentRequestBody body) {
        Member member = memberRepository.findById(body.id()).orElseThrow();

//        if (memberRepository.existsBySchoolIdAndGradeAndClassNoAndIsSpecial(member.getSchool().getId(), grade, classNo, isSpecial, specialName )) {
////            throw new IllegalStateException("Member with same agencyId, same grade, same classNo and isSpecial is false already exists");
//            return null;
//        }

        if(body.password() != null && !body.password().isEmpty())
            member.setPassword(body.password());

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public void deleteMembers(List<Long> memberIds) {
        memberIds.forEach(memberId -> this.deleteMember(memberRepository.findById(memberId).orElseThrow()));
    }

    @Transactional
    private void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public List<Member> getMemberDetail(int roleLevel) {
        return memberRepository.findByRoleLevel(roleLevel);
    }
}
