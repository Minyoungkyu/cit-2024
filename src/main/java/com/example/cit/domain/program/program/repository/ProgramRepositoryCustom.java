package com.example.cit.domain.program.program.repository;

import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.standard.base.KwTypeV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProgramRepositoryCustom {
    Page<Program> findByKw(KwTypeV1 kwType, String kw, Pageable pageable);
}
