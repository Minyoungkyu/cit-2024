package com.example.cit.domain.player.inventroy.repository;

import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
