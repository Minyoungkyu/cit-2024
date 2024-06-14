package com.example.cit.domain.areaCode.administrativeDistrict.repository;

import com.example.cit.domain.areaCode.administrativeDistrict.entity.AdministrativeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 행정구역 jpa 리포지토리
public interface AdministrativeDistrictRepository extends JpaRepository<AdministrativeDistrict, Long> {
    List<AdministrativeDistrict> findAllByRegionCode(long regionCode);
}
