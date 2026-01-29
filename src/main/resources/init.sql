CREATE DATABASE IF NOT EXISTS game_db;

USE game_db;

-- Skapa tabeller i rätt ordning (parent före child)
CREATE TABLE IF NOT EXISTS scenes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT
);

CREATE TABLE IF NOT EXISTS transitions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    origin_scene_id BIGINT,
    target_scene_id BIGINT,
    scene_description TEXT,
    choice_description TEXT,
    consumes_required_items BOOLEAN DEFAULT TRUE,
    is_enabled BOOLEAN,
    FOREIGN KEY (origin_scene_id) REFERENCES scenes(id),
    FOREIGN KEY (target_scene_id) REFERENCES scenes(id)
);

CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    scene_id BIGINT,
    transition_id BIGINT,
    FOREIGN KEY (scene_id) REFERENCES scenes(id),
    FOREIGN KEY (transition_id) REFERENCES transitions(id)
);

CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id BIGINT,
    quantity INT,
    FOREIGN KEY (item_id) REFERENCES items(id)
);

-- Many-to-many join tables
CREATE TABLE IF NOT EXISTS transition_items (
    transition_id BIGINT,
    item_id BIGINT,
    PRIMARY KEY (transition_id, item_id),
    FOREIGN KEY (transition_id) REFERENCES transitions(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE TABLE IF NOT EXISTS transition_enables_transitions (
    owner_transition_id BIGINT,
    affected_transition_id BIGINT,
    PRIMARY KEY (owner_transition_id, affected_transition_id),
    FOREIGN KEY (owner_transition_id) REFERENCES transitions(id),
    FOREIGN KEY (affected_transition_id) REFERENCES transitions(id)
);

CREATE TABLE IF NOT EXISTS transition_disables_transitions (
    owner_transition_id BIGINT,
    affected_transition_id BIGINT,
    PRIMARY KEY (owner_transition_id, affected_transition_id),
    FOREIGN KEY (owner_transition_id) REFERENCES transitions(id),
    FOREIGN KEY (affected_transition_id) REFERENCES transitions(id)
);