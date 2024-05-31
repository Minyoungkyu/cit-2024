package com.example.cit.domain.school.school.service;

import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.repository.SchoolRepository;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.standard.base.KwTypeV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Writer;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Transactional
    public School createSchool(String region, String administrativeDistrict, String schoolLevel, String HighSchoolType, String schoolName,
                               String establishmentType, String coeducationType, String areaType, String address, String phoneNumber) {

        School school = School.builder()
                .region(region==null?"":region)
                .administrativeDistrict(administrativeDistrict==null?"":administrativeDistrict)
                .schoolLevel(schoolLevel==null?"":schoolLevel)
                .highSchoolType(HighSchoolType==null?"":HighSchoolType)
                .schoolName(schoolName==null?"":schoolName)
                .establishmentType(establishmentType==null?"":establishmentType)
                .coeducationType(coeducationType==null?"":coeducationType)
                .areaType(areaType==null?"":areaType)
                .address(address==null?"":address)
                .phoneNumber(phoneNumber==null?"":phoneNumber)
                .instituteType("학교")
                .build();

        schoolRepository.save(school);

        return school;
    }

    @Transactional
    public School modifySchool(Long id, String region, String administrativeDistrict, String schoolLevel, String HighSchoolType, String schoolName,
                               String establishmentType, String coeducationType, String areaType, String address, String phoneNumber) {
        School school = schoolRepository.findById(id).orElseThrow();

        school.setRegion(region==null?"":region);
        school.setAdministrativeDistrict(administrativeDistrict==null?"":administrativeDistrict);
        school.setSchoolLevel(schoolLevel==null?"":schoolLevel);
        school.setHighSchoolType(HighSchoolType==null?"":HighSchoolType);
        school.setSchoolName(schoolName==null?"":schoolName);
        school.setEstablishmentType(establishmentType==null?"":establishmentType);
        school.setCoeducationType(coeducationType==null?"":coeducationType);
        school.setAreaType(areaType==null?"":areaType);
        school.setAddress(address==null?"":address);
        school.setPhoneNumber(phoneNumber==null?"":phoneNumber);

        schoolRepository.save(school);

        return school;
    }

    @Transactional
    public School createInstitute(String region, String administrativeDistrict, String schoolName,
                               String areaType, String address, String phoneNumber) {

        School school = School.builder()
                .region(region==null?"":region)
                .administrativeDistrict(administrativeDistrict==null?"":administrativeDistrict)
                .schoolLevel("")
                .highSchoolType("")
                .schoolName(schoolName==null?"":schoolName)
                .establishmentType("")
                .coeducationType("")
                .areaType(areaType==null?"":areaType)
                .address(address==null?"":address)
                .phoneNumber(phoneNumber==null?"":phoneNumber)
                .instituteType("기관")
                .build();

        schoolRepository.save(school);

        return school;
    }

    @Transactional
    public School modifyInstitute(Long id, String region, String administrativeDistrict, String schoolName,
                               String areaType, String address, String phoneNumber) {
        School school = schoolRepository.findById(id).orElseThrow();

        school.setRegion(region==null?"":region);
        school.setAdministrativeDistrict(administrativeDistrict==null?"":administrativeDistrict);
        school.setSchoolLevel("");
        school.setHighSchoolType("");
        school.setSchoolName(schoolName==null?"":schoolName);
        school.setEstablishmentType("");
        school.setCoeducationType("");
        school.setAreaType(areaType==null?"":areaType);
        school.setAddress(address==null?"":address);
        school.setPhoneNumber(phoneNumber==null?"":phoneNumber);

        schoolRepository.save(school);

        return school;
    }

    @Transactional
    public void deleteSchools(List<Long> schoolIds) {
        schoolIds.forEach(schoolId -> this.deleteSchool(schoolRepository.findById(schoolId).orElseThrow()));
    }

    @Transactional
    protected void deleteSchool(School school) {
        schoolRepository.delete(school);
    }

    public List<SchoolInputListDto> getSchools() {
        return schoolRepository.findAllProjectedBy();
    }

    public List<School> getSchoolsDetail(String instituteType) {
        // instituteType=="학교" 인 기관만 조회
        return schoolRepository.findByInstituteType(instituteType);

//        return schoolRepository.findAll();
    }

    public School getSchoolById(Long id) {
        return schoolRepository.findById(id).orElseThrow();
    }

    public Optional<School> findSchoolById(Long id) {
        return schoolRepository.findById(id);
    }

    public Page<School> findByKw(KwTypeV1 kwType, String kw, Pageable pageable, String instituteType) {
        return schoolRepository.findByKw(kwType, kw, pageable, instituteType);
    }


    public SchoolClass getSchoolClassById(long schoolClassId) {
        return schoolRepository.findSchoolClassById(schoolClassId);
    }

    public SchoolClass getSchoolClassByCode(String schoolClassCode) {
        return schoolRepository.findSchoolClassByCode(schoolClassCode);
    }
}
