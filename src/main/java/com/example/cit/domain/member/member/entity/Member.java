package com.example.cit.domain.member.member.entity;

import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Member extends BaseTime {
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String refreshToken;
    private String name;
    private String cellphoneNo;

    private int roleLevel; // 권한 레벨

    @OneToOne(fetch = LAZY, cascade = ALL)
    private Player player;

    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Program> programs = new ArrayList<>();


    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<School> schools = new ArrayList<>();

    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    // 캐시 데이터
    // admin ( class < system < super )
    @Transient
    private Boolean _isAdmin; //roleLevel >= 2

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStringList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Transient
    public List<String> getAuthoritiesAsStringList() {
        List<String> authorities = new ArrayList<>();

        if (roleLevel >= 4) {
            authorities.add("ROLE_SUPERADMIN");
        }
        if (roleLevel >= 3) {
            authorities.add("ROLE_SYSTEMADMIN");
        }
        if (roleLevel >= 2) {
            authorities.add("ROLE_CLASSADMIN");
        }
        authorities.add("ROLE_MEMBER"); // 모든 사용자에게 기본적으로 부여

        return authorities;
    }

    public boolean isAdmin() {
        if (this._isAdmin != null)
            return this._isAdmin;

        this._isAdmin = getRoleLevel() >= 2;

        return this._isAdmin;
    }

    // List.of("system", "admin").contains(getUsername()); 이걸할 때 findById 가 실행될 수 도 있는데
    // 이 함수를 통해서 _isAdmin 필드의 값을 강제로 정하면서, 적어도 isAdmin() 함수 때문에 findById 가 실행되지 않도록 한다.
    public void setAdmin(boolean admin) {
        this._isAdmin = admin;
    }

}
