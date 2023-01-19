package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(value = "/rest/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<Player> findAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false) PlayerOrder order,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize
    ) {
        return playerService.findAll(name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel,
                order,
                pageNumber,
                pageSize);
    }

    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Integer count(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "title", required = false) String title,
                         @RequestParam(value = "race", required = false) Race race,
                         @RequestParam(value = "profession", required = false) Profession profession,
                         @RequestParam(value = "after", required = false) Long after,
                         @RequestParam(value = "before", required = false) Long before,
                         @RequestParam(value = "banned", required = false) Boolean banned,
                         @RequestParam(value = "minExperience", required = false) Integer minExperience,
                         @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                         @RequestParam(value = "minLevel", required = false) Integer minLevel,
                         @RequestParam(value = "maxLevel", required = false) Integer maxLevel
    ) {
        return playerService.count(name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        player = playerService.createPlayer(player);
        if (player != null)
            return ok(player);
        else
            return badRequest().build();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Player> findById(@PathVariable("id") Long id) {
        if (id <= 0) return badRequest().build();
        Player player = playerService.findById(id);
        if (player != null)
            return ok(player);
        else
            return notFound().build();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Player> updateById(@PathVariable("id") Long id, @RequestBody Player updatePlayer) {
        if (id <= 0) return badRequest().build();
        Player findPlayer = playerService.findById(id);
        if (findPlayer == null)
            return notFound().build();
        updatePlayer = playerService.updatePlayer(findPlayer, updatePlayer);

        if (updatePlayer != null)
            return ok(updatePlayer);
        else
            return badRequest().build();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if (id <= 0) return badRequest().build();
        try {
            playerService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return notFound().build();
        }
        return ok().build();
    }
}
