package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PlayerRepositoryImpl implements PlayerRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Player> findAll(String name, String title, Race race, Profession profession, Long after,
                                Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        if (order == null)
            order = PlayerOrder.ID;
        CriteriaQuery<Player> criteriaQuery = result(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, order);

        return entityManager.createQuery(criteriaQuery).setMaxResults(pageSize).setFirstResult(pageNumber * pageSize).getResultList();
    }

    @Override
    public Integer count(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned,
                         Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        CriteriaQuery<Player> criteriaQuery = result(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, null);

        return entityManager.createQuery(criteriaQuery).getResultList().size();
    }

    private CriteriaQuery<Player> result(String name, String title, Race race, Profession profession, Long after, Long before,
                                         Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                         Integer maxLevel, PlayerOrder order) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = criteriaBuilder.createQuery(Player.class);
        Root<Player> root = criteriaQuery.from(Player.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null)
            predicates.add(criteriaBuilder.like(root.get("name".toLowerCase()), "%" + name.toLowerCase() + "%"));
        if (title != null)
            predicates.add(criteriaBuilder.like(root.get("title".toLowerCase()), "%" + title.toLowerCase() + "%"));
        if (race != null)
            predicates.add(criteriaBuilder.equal(root.get("race"), race));
        if (profession != null)
            predicates.add(criteriaBuilder.equal(root.get("profession"), profession));
        if (after != null) {
            Date date = new Date(after);
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday").as(Date.class), date));
        }
        if (before != null) {
            Date date = new Date(before);
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthday").as(Date.class), date));
        }
        if (banned != null)
            predicates.add(criteriaBuilder.equal(root.get("banned"), banned));
        if (minExperience != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience));
        if (maxExperience != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience));
        if (minLevel != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel));
        if (maxLevel != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel));

        if (order != null)
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(order.getFieldName())));

        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
    }
}
