package com.example.cit.global.initData;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class DevInit2 {

    private final ProgramService programService;
    private final MemberService memberService;


    @Bean
    @Order(9)
    ApplicationRunner initProgram() {
        return args -> {
            if (programService.findByName("테스트사업").isEmpty()) {
                Member memberProgramAdmin = memberService.findByUsername("program").get();
                Member memberProgramAdmin2 = memberService.join("program2", "1234", "사업관리자2", "010-1234-1234", 3, "부서2", "직급2", "123-456-7890").getData();
                memberProgramAdmin.setRefreshToken("program2");

                Program program = programService.createProgram("테스트사업", LocalDate.now(), LocalDate.now(), "서울시", "성북구");
                programService.addResponsibility(memberProgramAdmin, program);
                Program program2 = programService.createProgram("테테스트사업", LocalDate.now(), LocalDate.now(), "서울시", "강북구");
                programService.addResponsibility(memberProgramAdmin2, program);

                IntStream.range(0, 100).forEach(i -> {
                    Program opP = programService.createProgram("테스트사업" + i, LocalDate.now(), LocalDate.now(), "서울시", "강남구");
                    programService.addResponsibility(memberProgramAdmin, opP);
                });

            }


        };
    }




}
