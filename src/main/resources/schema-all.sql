DROP TABLE IF EXISTS user_notification;

CREATE TABLE user_notification  (
    notification_id BIGINT NOT NULL,
    user_id VARCHAR(20),
    card_id VARCHAR(20),
    notification_date DATETIME,
    PRIMARY KEY (notification_id)
);

INSERT INTO user_notification values ('1', '1', '1', '2017-03-11 10:00:00.000');
INSERT INTO user_notification values ('2', '2', '2', '2017-03-12 10:00:00.000');
INSERT INTO user_notification values ('3', '3', '3', '2017-03-13 10:00:00.000');
INSERT INTO user_notification values ('4', '4', '4', '2017-03-11 10:00:00.000');
INSERT INTO user_notification values ('5', '5', '5', '2017-03-12 10:00:00.000');


