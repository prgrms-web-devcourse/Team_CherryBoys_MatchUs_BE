INSERT INTO sports (id, name)
VALUES (1, '축구');
INSERT INTO sports (id, name)
VALUES (2, '풋살');

INSERT INTO users (id, sport_id, email, name, password, nickname, gender, bio, age_group,
                   manner_temperature, is_disaffiliated, created_date, modified_date)
VALUES (1, 1, 'email@naver.com', '머쓱', '1234', '머쓱머쓱', 'MAN', '', 'TWENTIES', 36.5, false,
        '2021-12-06T12:32:22', '2021-12-06T12:32:22');
