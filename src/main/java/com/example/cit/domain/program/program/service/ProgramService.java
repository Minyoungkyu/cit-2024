package com.example.cit.domain.program.program.service;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.repository.ProgramRepository;
import com.example.cit.domain.school.school.repository.SchoolRepository;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.standard.base.KwTypeV1;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProgramService {

    private final ProgramRepository programRepository;

    @Transactional
    public Program createProgram(String name, LocalDate startDate, LocalDate endDate, String city, String administrativeDistrict) {

        Program program = Program.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .city(city)
                .administrativeDistrict(administrativeDistrict)
                .build();

        programRepository.save(program);

        return program;
    }

    @Transactional
    public Program addResponsibility(Member member, Program program) {
        program.getMembers().add(member);
        programRepository.save(program);

        return program;
    }

    @Transactional
    public List<Program> getPrograms(Member member) {
        return programRepository.findAllByMembers_Id(member.getId());
    }

    public Page<Program> findByKw(KwTypeV1 kwType, String kw, Pageable pageable) {
        return programRepository.findByKw(kwType, kw, pageable);
    }

    public Optional<Program> findByName(String name) {
        return programRepository.findByName(name);
    }
}
