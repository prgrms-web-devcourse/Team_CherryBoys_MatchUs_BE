DROP TABLE IF EXISTS teams CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS sports CASCADE;
DROP TABLE IF EXISTS team_users CASCADE;
DROP TABLE IF EXISTS tags CASCADE;
DROP TABLE IF EXISTS user_tags CASCADE;
DROP TABLE IF EXISTS matches CASCADE;
DROP TABLE IF EXISTS hire_posts CASCADE;
DROP TABLE IF EXISTS user_match_histories CASCADE;
DROP TABLE IF EXISTS hire_applications CASCADE;
DROP TABLE IF EXISTS team_waitings CASCADE;
DROP TABLE IF EXISTS team_tags CASCADE;
DROP TABLE IF EXISTS team_invitations CASCADE;
DROP TABLE IF EXISTS member_waitings CASCADE;

CREATE TABLE teams
(
    id                 BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sport_id           BIGINT        NOT NULL,
    name               VARCHAR(20)   NOT NULL UNIQUE,
    bio                TEXT NULL,
    logo               TEXT NULL,
    age_group          VARCHAR(10) NULL,
    match_count        INT           NOT NULL DEFAULT 0,
    member_count       INT           NOT NULL DEFAULT 1,
    manner_temperature DECIMAL(4, 1) NOT NULL DEFAULT 36.5,
    is_deleted         TINYINT       NOT NULL DEFAULT 0,
    created_date       DATETIME      NOT NULL,
    modified_date      DATETIME      NOT NULL
);

CREATE TABLE users
(
    id                 BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sport_id           BIGINT        NOT NULL,
    email              VARCHAR(320)  NOT NULL UNIQUE,
    name               VARCHAR(255)  NOT NULL,
    password           VARCHAR(255)  NOT NULL,
    nickname           VARCHAR(20)   NOT NULL UNIQUE,
    gender             VARCHAR(10)   NOT NULL,
    bio                TEXT NULL,
    age_group          VARCHAR(10)   NOT NULL,
    manner_temperature DECIMAL(4, 1) NOT NULL DEFAULT 36.5,
    is_disaffiliated   TINYINT       NOT NULL DEFAULT 0,
    created_date       DATETIME      NOT NULL,
    modified_date      DATETIME      NOT NULL
);

CREATE TABLE sports
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL
);

CREATE TABLE team_users
(
    id               BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    team_id          BIGINT      NOT NULL,
    user_id          BIGINT      NOT NULL,
    grade            VARCHAR(10) NOT NULL,
    is_disaffiliated TINYINT     NOT NULL
);

CREATE TABLE tags
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    type VARCHAR(10) NOT NULL
);

CREATE TABLE user_tags
(
    id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_id    BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    tag_count INT    NOT NULL DEFAULT 0
);

CREATE TABLE matches
(
    id               BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    register_team_id BIGINT       NOT NULL,
    apply_team_id    BIGINT NULL,
    sport_id         BIGINT       NOT NULL,
    city             VARCHAR(255) NOT NULL,
    region           VARCHAR(255) NOT NULL,
    ground_name      VARCHAR(255) NOT NULL,
    date             DATE         NOT NULL,
    start_time       TIME         NOT NULL,
    end_time         TIME         NOT NULL,
    age_group        VARCHAR(10) NULL,
    cost             INT          NOT NULL,
    detail           VARCHAR(255) NULL,
    is_cancelled     TINYINT      NOT NULL DEFAULT 0,
    status           VARCHAR(20)  NOT NULL,
    created_date     DATETIME     NOT NULL,
    modified_date    DATETIME     NOT NULL
);

CREATE TABLE hire_posts
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    team_id            BIGINT       NOT NULL,
    title              VARCHAR(100) NOT NULL,
    position           VARCHAR(50) NULL,
    city               VARCHAR(255) NOT NULL,
    region             VARCHAR(255) NOT NULL,
    ground_name        VARCHAR(255) NOT NULL,
    date               DATE         NOT NULL,
    start_time         TIME         NOT NULL,
    end_time           TIME         NOT NULL,
    age_group          VARCHAR(10) NULL,
    detail             VARCHAR(255) NULL,
    hire_player_number INT          NOT NULL DEFAULT 1,
    created_date       DATETIME     NOT NULL,
    modified_date      DATETIME     NOT NULL,
    is_deleted         TINYINT      NOT NULL
);

CREATE TABLE user_match_histories
(
    id       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    match_id BIGINT NOT NULL
);

CREATE TABLE hire_applications
(
    id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hire_post_id BIGINT NOT NULL,
    user_id      BIGINT NOT NULL
);

CREATE TABLE team_waitings
(
    id           BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    match_id     BIGINT      NOT NULL,
    team_id      BIGINT      NOT NULL,
    waiting_type VARCHAR(10) NOT NULL
);

CREATE TABLE team_tags
(
    id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_id    BIGINT NOT NULL,
    team_id   BIGINT NOT NULL,
    tag_count INT    NOT NULL DEFAULT 0
);

CREATE TABLE team_invitations
(
    id      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE member_waitings
(
    id              BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    team_waiting_id BIGINT NOT NULL
);

ALTER TABLE teams
    ADD CONSTRAINT FK_sports_TO_teams_1 FOREIGN KEY (sport_id)
        REFERENCES sports (id);

ALTER TABLE users
    ADD CONSTRAINT FK_sports_TO_users_1 FOREIGN KEY (sport_id)
        REFERENCES sports (id);

ALTER TABLE team_users
    ADD CONSTRAINT FK_teams_TO_team_users_1 FOREIGN KEY (team_id)
        REFERENCES teams (id);

ALTER TABLE team_users
    ADD CONSTRAINT FK_users_TO_team_users_1 FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE user_tags
    ADD CONSTRAINT FK_tags_TO_user_tags_1 FOREIGN KEY (tag_id)
        REFERENCES tags (id);

ALTER TABLE user_tags
    ADD CONSTRAINT FK_users_TO_user_tags_1 FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE matches
    ADD CONSTRAINT FK_teams_TO_matches_1 FOREIGN KEY (register_team_id)
        REFERENCES teams (id);

ALTER TABLE matches
    ADD CONSTRAINT FK_teams_TO_matches_2 FOREIGN KEY (apply_team_id)
        REFERENCES teams (id);

ALTER TABLE matches
    ADD CONSTRAINT FK_sports_TO_matches_1 FOREIGN KEY (sport_id)
        REFERENCES sports (id);

ALTER TABLE hire_posts
    ADD CONSTRAINT FK_teams_TO_hire_posts_1 FOREIGN KEY (team_id)
        REFERENCES teams (id);

ALTER TABLE user_match_histories
    ADD CONSTRAINT FK_users_TO_user_match_histories_1 FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE user_match_histories
    ADD CONSTRAINT FK_matches_TO_user_match_histories_1 FOREIGN KEY (match_id)
        REFERENCES matches (id);

ALTER TABLE hire_applications
    ADD CONSTRAINT FK_hire_posts_TO_hire_applications_1 FOREIGN KEY (hire_post_id)
        REFERENCES hire_posts (id);

ALTER TABLE hire_applications
    ADD CONSTRAINT FK_users_TO_hire_applications_1 FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE team_waitings
    ADD CONSTRAINT FK_matches_TO_team_waitings_1 FOREIGN KEY (match_id)
        REFERENCES matches (id);

ALTER TABLE team_waitings
    ADD CONSTRAINT FK_teams_TO_team_waitings_1 FOREIGN KEY (team_id)
        REFERENCES teams (id);

ALTER TABLE team_tags
    ADD CONSTRAINT FK_tags_TO_team_tags_1 FOREIGN KEY (tag_id)
        REFERENCES tags (id);

ALTER TABLE team_tags
    ADD CONSTRAINT FK_teams_TO_team_tags_1 FOREIGN KEY (team_id)
        REFERENCES teams (id);

ALTER TABLE team_invitations
    ADD CONSTRAINT FK_teams_TO_team_invitations_1 FOREIGN KEY (team_id)
        REFERENCES teams (id);

ALTER TABLE team_invitations
    ADD CONSTRAINT FK_users_TO_team_invitations_1 FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE member_waitings
    ADD CONSTRAINT FK_users_TO_member_waitings_1 FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE member_waitings
    ADD CONSTRAINT FK_team_waitings_TO_member_waitings_1 FOREIGN KEY (team_waiting_id)
        REFERENCES team_waitings (id);
