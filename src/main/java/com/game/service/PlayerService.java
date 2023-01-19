package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {

    Player createPlayer(Player player);

    List<Player> findAll(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned,
                         Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel,
                         PlayerOrder order, Integer pageNumber, Integer pageSize);

    Integer count(String name, String title, Race race, Profession profession, Long after,
                  Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                  Integer minLevel, Integer maxLevel);

    void deleteById(Long id);

    Player findById(Long id);

    Player updatePlayer(Player findPlayer, Player updatePlayer);
}
