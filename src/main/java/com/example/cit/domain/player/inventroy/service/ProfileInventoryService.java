package com.example.cit.domain.player.inventroy.service;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.item.profileIcon.service.ProfileService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.AuthTokenService;
import com.example.cit.domain.player.inventroy.dto.InventoryDto;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import com.example.cit.domain.player.inventroy.repository.InventoryRepository;
import com.example.cit.domain.player.inventroy.repository.ProfileInventoryRepository;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileInventoryService {

    private final ProfileInventoryRepository profileInventoryRepository;
    private final ProfileService profileService;
    private final Rq rq;
    private final AuthTokenService authTokenService;

    @Transactional
    public ProfileInventory createInventory(Player player, ProfileIcon profileIcon, boolean isEquipped) {

        ProfileInventory profileInventory = ProfileInventory.builder()
                .player(player)
                .profileIcon(profileIcon)
                .isEquipped(isEquipped)
                .build();

        profileInventoryRepository.save(profileInventory);
        return profileInventory;
    }

    public List<ProfileInventory> getMyInventoryList(Member member) {
        return profileInventoryRepository.findByPlayer(member.getPlayer());
    }

    @Transactional
    public ProfileInventory addInventory(long profileId, Member member) {
        ProfileIcon profile = profileService.getProfile(profileId);
        if(profile.getPrice() > 0) {
            if(member.getPlayer().getGems() < profile.getPrice())
                throw new GlobalException("보석이 부족합니다.");
            member.getPlayer().setGems(member.getPlayer().getGems() - profile.getPrice());
            return createInventory(member.getPlayer(), profile, false);
        } else {
            throw new GlobalException("구매할 수 없는 아이템입니다.");
        }
    }
}
