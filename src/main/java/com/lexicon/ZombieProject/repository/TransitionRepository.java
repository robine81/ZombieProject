package com.lexicon.ZombieProject.repository;

import com.lexicon.ZombieProject.entity.Transition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransitionRepository extends JpaRepository<Transition, Long> {
    public Transition findByName(String name);
    public Boolean existsByName(String name);
}
