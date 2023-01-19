package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository repository;

    @Override
    public List<Player> findAll(String name, String title, Race race, Profession profession, Long after,
                                Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        return repository.findAll(name, title, race, profession, after, before, banned, minExperience, maxExperience,
                minLevel, maxLevel, order, pageNumber, pageSize);
    }

    @Override
    public Player findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Player createPlayer(Player player) {
        if (player.getName() == null || player.getTitle() == null || player.getExperience() == null || player.getBirthday() == null ||
                player.getRace() == null || player.getProfession() == null)
            return null;
        if (check(player)) return null;
        if (player.getBanned() == null) player.setBanned(false);

        player.recalculateExp();
        return repository.save(player);
    }

    @Override
    public Player updatePlayer(Player findPlayer, Player updatePlayer) {
        if ((updatePlayer.getName() == null || updatePlayer.getName().isEmpty()) && updatePlayer.getTitle() == null &&
                updatePlayer.getRace() == null && updatePlayer.getProfession() == null && updatePlayer.getBirthday() == null &&
                updatePlayer.getBanned() == null && updatePlayer.getExperience() == null)
            return findPlayer;

        if (check(updatePlayer)) return null;

        if (updatePlayer.getName() != null)
            findPlayer.setName(updatePlayer.getName());
        if (updatePlayer.getTitle() != null)
            findPlayer.setTitle(updatePlayer.getTitle());
        if (updatePlayer.getRace() != null)
            findPlayer.setRace(updatePlayer.getRace());
        if (updatePlayer.getProfession() != null)
            findPlayer.setProfession(updatePlayer.getProfession());
        if (updatePlayer.getBirthday() != null)
            findPlayer.setBirthday(updatePlayer.getBirthday());
        if (updatePlayer.getBanned() != null)
            findPlayer.setBanned(updatePlayer.getBanned());
        if (updatePlayer.getExperience() != null)
            findPlayer.setExperience(updatePlayer.getExperience());

        return repository.save(findPlayer);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Integer count(String name, String title, Race race, Profession profession, Long after,
                         Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                         Integer minLevel, Integer maxLevel) {
        return repository.count(name, title, race, profession, after, before, banned, minExperience, maxExperience,
                minLevel, maxLevel);
    }

    private boolean check(Player player) {
        if (player.getName() != null && (player.getName().length() > 12 || "".equals(player.getName()))) return true;
        if (player.getTitle() != null && player.getTitle().length() > 30) return true;
        if (player.getExperience() != null && (player.getExperience() < 0 || player.getExperience() > 10000000)) return true;
        return player.getBirthday() != null && (player.getBirthday().getTime() < 0 ||
                !(player.getBirthday().after(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime()) &&
                        player.getBirthday().before(new GregorianCalendar(3000, Calendar.DECEMBER, 31).getTime())));
    }
}
