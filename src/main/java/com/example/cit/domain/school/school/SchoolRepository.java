package com.example.cit.domain.school.school;

import com.example.cit.domain.school.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}