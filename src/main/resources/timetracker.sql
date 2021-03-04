DROP DATABASE IF EXISTS mydb;
CREATE DATABASE mydb;
use mydb;

CREATE TABLE user_type (
    user_type_id INT PRIMARY KEY AUTO_INCREMENT,
    user_type_name VARCHAR(20)
);
INSERT INTO user_type (user_type_name) VALUES ('admin');
INSERT INTO user_type (user_type_name) VALUES ('client');

CREATE TABLE activity (
    activity_id INT PRIMARY KEY AUTO_INCREMENT,
    activity_name VARCHAR(20)
);
INSERT INTO activity (activity_name) VALUES ('game');

CREATE TABLE status (
    status_id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(20)
);
INSERT INTO status (status_name) VALUES ('new_activity');
INSERT INTO status (status_name) VALUES ('in_progress');
INSERT INTO status (status_name) VALUES ('pause');
INSERT INTO status (status_name) VALUES ('finished');
INSERT INTO status (status_name) VALUES ('stop');

CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20),
    sur_name VARCHAR(20),
    login VARCHAR(20),
    password VARCHAR(20),
    user_type_id INT,
    request boolean,
    FOREIGN KEY (user_type_id) REFERENCES user_type (user_type_id)
    ON DELETE SET NULL
);
INSERT INTO user (first_name,sur_name,login,password,user_type_id)
    VALUES ('Ray','Parker','admin','admin','1');
INSERT INTO user (first_name,sur_name,login,password,user_type_id)
    VALUES ('Tom','Crooz','user','user','2');

CREATE TABLE user_request (
    user_request_id INT PRIMARY KEY AUTO_INCREMENT,
    user_request_name VARCHAR(20)
);
INSERT INTO user_request (user_request_name) VALUES ('add');
INSERT INTO user_request (user_request_name) VALUES ('remove');

CREATE TABLE tracking (
    tracking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    activity_id INT,
    status_id INT,
    user_request_id INT,
    time VARCHAR(20),
    time_start BIGINT(64),
	  time_stop BIGINT(64),
    difference_time BIGINT(64),
    time_switch boolean,
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (activity_id) REFERENCES activity (activity_id),
	  FOREIGN KEY (status_id) REFERENCES status (status_id),
	  FOREIGN KEY (user_request_id) REFERENCES user_request (user_request_id)
);
INSERT INTO tracking (user_id,activity_id,status_id,user_request_id,
    time, time_start, time_stop, difference_time)
    VALUES ('2','1','1',NULL,'00:00:00','0','0','0');
