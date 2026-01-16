package com.lexicon.ZombieProject.repository;

import com.lexicon.ZombieProject.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SceneRepository extends JpaRepository<Scene, Long> {
    Optional<Scene> findBySceneName(String sceneName);
    boolean existsBySceneName(String sceneName);
}
