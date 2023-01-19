package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerRepositoryCustom {
    List<Player> findAll(String name, String title, Race race, Profession profession, Long after,
                         Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                         Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize);

    Integer count(String name, String title, Race race, Profession profession, Long after,
                  Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                  Integer minLevel, Integer maxLevel);
}
