package com.example.cit.global.initData;

import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.achievement.achievement.service.AchievementService;
import com.example.cit.domain.areaCode.administrativeDistrict.service.AdministrativeDistrictService;
import com.example.cit.domain.areaCode.region.service.RegionService;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.item.profileIcon.service.ProfileService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.service.ProgramService;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
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
    private final SchoolService schoolService;
    private final RegionService regionService;
    private final ProfileService profileService;
    private final AchievementService achievementService;
    private final AdministrativeDistrictService administrativeDistrictService;


    @Bean
    @Order(9)
    ApplicationRunner initProgram() {
        return args -> {
            if (programService.findByName("테스트사업").isEmpty()) {
                Member memberProgramAdmin = memberService.findByUsername("program").get();
                Member memberProgramAdmin2 = memberService.join("program2", "1234", "사업관리자2", "010-1234-1234", 3, "부서2", "직급2", "123-456-7890").getData();
                memberProgramAdmin.setRefreshToken("program2");

                Program program = programService.createProgram("테스트사업", LocalDate.now(), LocalDate.now(), "서울시", "성북구");
                Program program2 = programService.createProgram("테테스트사업", LocalDate.now(), LocalDate.now(), "서울시", "강북구");
                programService.addResponsibility(memberProgramAdmin2, program2);

                IntStream.range(0, 100).forEach(i -> {
                    Program opP = programService.createProgram("테스트사업" + i, LocalDate.now(), LocalDate.now(), "서울시", "강남구");
                    programService.addResponsibility(memberProgramAdmin, opP);
                });



            }
        };
    }
    @Bean
    @Order(10)
    ApplicationRunner initAreaCode() {
        return args -> {
            if (regionService.findByName("서울특별시").isEmpty()) {
                regionService.createRegion(11, "서울특별시");
                regionService.createRegion(21, "부산광역시");
                regionService.createRegion(22, "대구광역시");
                regionService.createRegion(23, "인천광역시");
                regionService.createRegion(24, "광주광역시");
                regionService.createRegion(25, "대전광역시");
                regionService.createRegion(26, "울산광역시");
                regionService.createRegion(29, "세종특별자치도");
                regionService.createRegion(31, "경기도");
                regionService.createRegion(32, "강원도");
                regionService.createRegion(33, "충청북도");
                regionService.createRegion(34, "충청남도");
                regionService.createRegion(35, "전라북도");
                regionService.createRegion(36, "전라남도");
                regionService.createRegion(37, "경상북도");
                regionService.createRegion(38, "경상남도");
                regionService.createRegion(39, "제주특별자치도");

                administrativeDistrictService.createAdministrativeDistrict(11010, "종로구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11020, "중구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11030, "용산구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11040, "성동구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11050, "광진구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11060, "동대문구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11070, "중랑구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11080, "성북구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11090, "강북구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11100, "도봉구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11110, "노원구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11120, "은평구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11130, "서대문구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11140, "마포구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11150, "양천구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11160, "강서구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11170, "구로구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11180, "금천구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11190, "영등포구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11200, "동작구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11210, "관악구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11220, "서초구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11230, "강남구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11240, "송파구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11250, "강동구", 11);
                administrativeDistrictService.createAdministrativeDistrict(21010, "중구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21020, "서구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21030, "동구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21040, "영도구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21050, "부산진구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21060, "동래구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21070, "남구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21080, "북구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21090, "해운대구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21100, "사하구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21110, "금정구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21120, "강서구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21130, "연제구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21140, "수영구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21150, "사상구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21310, "기장군", 21);
                administrativeDistrictService.createAdministrativeDistrict(22010, "중구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22020, "동구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22030, "서구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22040, "남구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22050, "북구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22060, "수성구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22070, "달서구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22310, "달성군", 22);
                administrativeDistrictService.createAdministrativeDistrict(23010, "중구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23020, "동구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23040, "연수구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23050, "남동구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23060, "부평구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23070, "계양구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23080, "서구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23090, "미추홀구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23310, "강화군", 23);
                administrativeDistrictService.createAdministrativeDistrict(23320, "옹진군", 23);
                administrativeDistrictService.createAdministrativeDistrict(24010, "동구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24020, "서구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24030, "남구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24040, "북구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24050, "광산구", 24);
                administrativeDistrictService.createAdministrativeDistrict(25010, "동구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25020, "중구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25030, "서구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25040, "유성구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25050, "대덕구", 25);
                administrativeDistrictService.createAdministrativeDistrict(26010, "중구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26020, "남구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26030, "동구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26040, "북구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26310, "울주군", 26);
                administrativeDistrictService.createAdministrativeDistrict(29010, "세종특별자치시", 29);
                administrativeDistrictService.createAdministrativeDistrict(31010, "수원시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31011, "장안구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31012, "권선구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31013, "팔달구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31014, "영통구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31020, "성남시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31021, "수정구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31022, "중원구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31023, "분당구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31030, "의정부시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31040, "안양시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31041, "만안구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31042, "동안구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31050, "부천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31060, "광명시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31070, "평택시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31080, "동두천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31090, "안산시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31091, "상록구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31092, "단원구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31100, "고양시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31101, "덕양구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31103, "일산동구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31104, "일산서구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31110, "과천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31120, "구리시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31130, "남양주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31140, "오산시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31150, "시흥시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31160, "군포시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31170, "의왕시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31180, "하남시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31190, "용인시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31191, "처인구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31192, "기흥구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31193, "수지구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31200, "파주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31210, "이천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31220, "안성시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31230, "김포시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31240, "화성시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31250, "광주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31260, "양주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31270, "포천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31280, "여주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31350, "연천군", 31);
                administrativeDistrictService.createAdministrativeDistrict(31370, "가평군", 31);
                administrativeDistrictService.createAdministrativeDistrict(31380, "양평군", 31);
                administrativeDistrictService.createAdministrativeDistrict(32010, "춘천시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32020, "원주시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32030, "강릉시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32040, "동해시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32050, "태백시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32060, "속초시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32070, "삼척시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32310, "홍천군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32320, "횡성군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32330, "영월군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32340, "평창군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32350, "정선군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32360, "철원군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32370, "화천군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32380, "양구군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32390, "인제군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32400, "고성군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32410, "양양군", 32);
                administrativeDistrictService.createAdministrativeDistrict(33020, "충주시", 33);
                administrativeDistrictService.createAdministrativeDistrict(33030, "제천시", 33);
                administrativeDistrictService.createAdministrativeDistrict(33040, "청주시", 33);
                administrativeDistrictService.createAdministrativeDistrict(33041, "상당구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33042, "서원구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33043, "흥덕구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33044, "청원구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33320, "보은군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33330, "옥천군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33340, "영동군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33350, "진천군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33360, "괴산군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33370, "음성군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33380, "단양군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33390, "증평군", 33);
                administrativeDistrictService.createAdministrativeDistrict(34010, "천안시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34011, "동남구", 34);
                administrativeDistrictService.createAdministrativeDistrict(34012, "서북구", 34);
                administrativeDistrictService.createAdministrativeDistrict(34020, "공주시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34030, "보령시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34040, "아산시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34050, "서산시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34060, "논산시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34070, "계룡시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34080, "당진시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34310, "금산군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34330, "부여군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34340, "서천군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34350, "청양군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34360, "홍성군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34370, "예산군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34380, "태안군", 34);
                administrativeDistrictService.createAdministrativeDistrict(35010, "전주시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35011, "완산구", 35);
                administrativeDistrictService.createAdministrativeDistrict(35012, "덕진구", 35);
                administrativeDistrictService.createAdministrativeDistrict(35020, "군산시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35030, "익산시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35040, "정읍시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35050, "남원시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35060, "김제시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35310, "완주군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35320, "진안군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35330, "무주군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35340, "장수군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35350, "임실군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35360, "순창군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35370, "고창군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35380, "부안군", 35);
                administrativeDistrictService.createAdministrativeDistrict(36010, "목포시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36020, "여수시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36030, "순천시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36040, "나주시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36060, "광양시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36310, "담양군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36320, "곡성군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36330, "구례군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36350, "고흥군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36360, "보성군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36370, "화순군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36380, "장흥군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36390, "강진군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36400, "해남군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36410, "영암군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36420, "무안군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36430, "함평군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36440, "영광군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36450, "장성군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36460, "완도군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36470, "진도군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36480, "신안군", 36);
                administrativeDistrictService.createAdministrativeDistrict(37010, "포항시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37011, "남구", 37);
                administrativeDistrictService.createAdministrativeDistrict(37012, "북구", 37);
                administrativeDistrictService.createAdministrativeDistrict(37020, "경주시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37030, "김천시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37040, "안동시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37050, "구미시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37060, "영주시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37070, "영천시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37080, "상주시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37090, "문경시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37100, "경산시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37310, "군위군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37320, "의성군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37330, "청송군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37340, "영양군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37350, "영덕군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37360, "청도군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37370, "고령군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37380, "성주군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37390, "칠곡군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37400, "예천군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37410, "봉화군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37420, "울진군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37430, "울릉군", 37);
                administrativeDistrictService.createAdministrativeDistrict(38030, "진주시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38050, "통영시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38060, "사천시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38070, "김해시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38080, "밀양시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38090, "거제시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38100, "양산시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38110, "창원시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38111, "의창구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38112, "성산구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38113, "마산합포구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38114, "마산회원구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38115, "진해구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38310, "의령군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38320, "함안군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38330, "창녕군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38340, "고성군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38350, "남해군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38360, "하동군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38370, "산청군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38380, "함양군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38390, "거창군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38400, "합천군", 38);
                administrativeDistrictService.createAdministrativeDistrict(39010, "제주시", 39);
                administrativeDistrictService.createAdministrativeDistrict(39020, "서귀포시", 39);

            }
        };
    }

    @Bean
    @Order(11)
    ApplicationRunner initAchievementAndProfileIcon() {

        return args -> {

            if (achievementService.getAchievementById(1L).isEmpty()) {
                achievementService.createAchievement("테스트업적1", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 2, 5, 25, null);
                achievementService.createAchievement("테스트업적2", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 5, 5, 25, null);
                achievementService.createAchievement("테스트업적3", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 14, 5, 25, null);
                achievementService.createAchievement("테스트업적4", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 23, 5, 25, null);
                achievementService.createAchievement("테스트업적5", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 33, 10, 50, null);
                achievementService.createAchievement("테스트업적6", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 42, 10, 50, null);
                achievementService.createAchievement("테스트업적7", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 51, 10, 50, null);
                achievementService.createAchievement("테스트업적8", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 61, 15, 75, null);
                achievementService.createAchievement("테스트업적9", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 70, 15, 75, null);
                achievementService.createAchievement("테스트업적10", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 79, 15, 75, null);
                achievementService.createAchievement("테스트업적11", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 88, 15, 75, null);
                achievementService.createAchievement("테스트업적12", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 8, 8, 40, null);
                achievementService.createAchievement("테스트업적13", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 17, 8, 40, null);
                achievementService.createAchievement("테스트업적14", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 26, 8, 40, null);
                achievementService.createAchievement("테스트업적15", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 36, 16, 80, null);
                achievementService.createAchievement("테스트업적16", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 45, 16, 80, null);
                achievementService.createAchievement("테스트업적17", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 54, 16, 80, null);
                achievementService.createAchievement("테스트업적18", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 64, 24, 120, null);
                achievementService.createAchievement("테스트업적19", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 73, 24, 120, null);
                achievementService.createAchievement("테스트업적20", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 82, 24, 120, null);
                achievementService.createAchievement("테스트업적21", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 91, 24, 120, null);
                achievementService.createAchievement("테스트업적22", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 11, 10, 50, null);
                achievementService.createAchievement("테스트업적23", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 20, 10, 50, null);
                achievementService.createAchievement("테스트업적24", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 29, 10, 50, null);
                achievementService.createAchievement("테스트업적25", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 39, 20, 100, null);
                achievementService.createAchievement("테스트업적26", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 48, 20, 100, null);
                achievementService.createAchievement("테스트업적27", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 57, 20, 100, null);
                achievementService.createAchievement("테스트업적28", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 67, 30, 150, null);
                achievementService.createAchievement("테스트업적29", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 76, 30, 150, null);
                achievementService.createAchievement("테스트업적30", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 85, 30, 150, null);
                achievementService.createAchievement("테스트업적31", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 94, 30, 150, null);
                achievementService.createAchievement("테스트업적32", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 5, 20, 100, null);
                achievementService.createAchievement("테스트업적33", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 10, 30, 150, null);
                achievementService.createAchievement("테스트업적34", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 15, 40, 200, null);
                achievementService.createAchievement("테스트업적35", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 20, 50, 250, null);
                achievementService.createAchievement("테스트업적36", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 25, 50, 250, null);
                achievementService.createAchievement("테스트업적37", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 30, 100, 500, null);
                achievementService.createAchievement("테스트업적38", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 35, 150, 750, null);
                achievementService.createAchievement("테스트업적39", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 40, 200, 1000, null);
                achievementService.createAchievement("테스트업적40", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 45, 250, 1250, null);
                achievementService.createAchievement("테스트업적41", "테스트 테스트, 테스트 테스트!", "PLAYER LEVEL", 50, 500, 2500, null);
                achievementService.createAchievement("테스트업적42", "테스트 테스트, 테스트 테스트!", "PURCHASE EQUIPMENT", 1, 5, 25, null);
                achievementService.createAchievement("테스트업적43", "테스트 테스트, 테스트 테스트!", "PURCHASE ICON", 1, 5, 25, null);
                achievementService.createAchievement("테스트업적44", "테스트 테스트, 테스트 테스트!", "CHECK ENCY", 1, 5, 25, null);
                achievementService.createAchievement("테스트업적45", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 30, 10, 50, null);
                achievementService.createAchievement("테스트업적46", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 58, 10, 50, null);
                achievementService.createAchievement("테스트업적47", "테스트 테스트, 테스트 테스트!", "PURCHASE EQUIPMENT", 5, 10, 50, null);
                achievementService.createAchievement("테스트업적48", "테스트 테스트, 테스트 테스트!", "PURCHASE EQUIPMENT", 10, 10, 50, null);
                achievementService.createAchievement("테스트업적49", "테스트 테스트, 테스트 테스트!", "COUNT NORMAL", 5, 100, 500, null);
                achievementService.createAchievement("테스트업적50", "테스트 테스트, 테스트 테스트!", "COUNT NORMAL", 30, 300, 1500, null);
                Achievement ac1 = achievementService.createAchievement("테스트업적51", "테스트 테스트, 테스트 테스트!", "COUNT NORMAL", 100, 500, 2500, null);
                Achievement ac2 = achievementService.createAchievement("테스트업적52", "테스트 테스트, 테스트 테스트!", "COUNT NORMAL", 300, 1000, 5000, null);
                achievementService.createAchievement("테스트업적53", "테스트 테스트, 테스트 테스트!", "COUNT BOSS", 1, 50, 250, null);
                achievementService.createAchievement("테스트업적54", "테스트 테스트, 테스트 테스트!", "COUNT BOSS", 3, 300, 1500, null);
                Achievement ac3 = achievementService.createAchievement("테스트업적55", "테스트 테스트, 테스트 테스트!", "COUNT BOSS", 10, 1500, 7500, null);
                achievementService.createAchievement("테스트업적56", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 30, 100, 500, null);
                achievementService.createAchievement("테스트업적57", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 58, 250, 1250, null);
                achievementService.createAchievement("테스트업적58", "테스트 테스트, 테스트 테스트!", "STAGE CLEAR", 94, 500, 2500, null);
                Achievement ac4 = achievementService.createAchievement("테스트업적59", "테스트 테스트, 테스트 테스트!", "PURCHASE EQUIPMENT SET", 0, 500, 2500, null);
                Achievement ac5 = achievementService.createAchievement("테스트업적59", "테스트 테스트, 테스트 테스트!", "PURCHASE EQUIPMENT SET", 1, 500, 2500, null);

                profileService.createProfile("테스트1", "테스트 테스트, 테스트 테스트!", "img_icon1", 500, null);
                profileService.createProfile("테스트2", "테스트 테스트, 테스트 테스트!", "img_icon2", 500, null);
                profileService.createProfile("테스트3", "테스트 테스트, 테스트 테스트!", "img_icon3", 500, null);
                profileService.createProfile("테스트4", "테스트 테스트, 테스트 테스트!", "img_icon4", 500, null);
                profileService.createProfile("테스트5", "테스트 테스트, 테스트 테스트!", "img_icon7", 500, null);
                profileService.createProfile("테스트6", "테스트 테스트, 테스트 테스트!", "img_icon8", 500, null);
                profileService.createProfile("테스트7", "테스트 테스트, 테스트 테스트!", "img_icon9", 500, null);
                profileService.createProfile("테스트8", "테스트 테스트, 테스트 테스트!", "img_icon10", 500, null);

                ProfileIcon icon1 = profileService.createProfile("테스트10", "테스트 테스트, 테스트 테스트!", "img_icon11", 0, null);
                ProfileIcon icon2 = profileService.createProfile("테스트11", "테스트 테스트, 테스트 테스트!", "img_icon12", 0, null);
                ProfileIcon icon3 = profileService.createProfile("테스트12", "테스트 테스트, 테스트 테스트!", "img_icon13", 0, null);
                ProfileIcon icon4 = profileService.createProfile("테스트13", "테스트 테스트, 테스트 테스트!", "img_icon6", 0, null);
                ProfileIcon icon5 = profileService.createProfile("테스트14", "테스트 테스트, 테스트 테스트!", "img_icon5", 0, null);

                achievementService.setRewardIcon(ac1, icon1);
                achievementService.setRewardIcon(ac2, icon2);
                achievementService.setRewardIcon(ac3, icon3);
                achievementService.setRewardIcon(ac4, icon4);
                achievementService.setRewardIcon(ac5, icon5);

                profileService.setAchievement(icon1, ac1);
                profileService.setAchievement(icon2, ac2);
                profileService.setAchievement(icon3, ac3);
                profileService.setAchievement(icon4, ac4);
                profileService.setAchievement(icon5, ac5);
            }
        };

    }

}
