package com.lexicon.ZombieProject.repository;

import com.lexicon.ZombieProject.entity.Transition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransitionRepository extends JpaRepository<Transition, Long> {
}
