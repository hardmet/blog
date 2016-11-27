--USERS
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Admin','admin@gmail.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka1','grechka@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka2','test1@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Guest','test2@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka4','test3@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka5','test4@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka6','test5@outlook.com','12345', TRUE, false);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka7','test6@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka8','test7@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka9','test8@outlook.com','12345', TRUE, false);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka10','roleuser@outlook.com','12345', TRUE, true);
INSERT INTO author (nick_name, email, password, enabled, visible) VALUES ('Grechka11','superuser@outlook.com','12345', TRUE, false);

--authorITIES
INSERT INTO authorITIES (useremail,access_group) VALUES ('admin@gmail.com','ROLE_ADMIN');
INSERT INTO authorITIES (useremail,access_group) VALUES ('test1@outlook.com','ROLE_USER');
INSERT INTO authorITIES (useremail,access_group) VALUES ('test2@outlook.com','ROLE_SUPER_USER');

--log
INSERT INTO log(log_string) VALUES('TEST log 1');
INSERT INTO log(log_string) VALUES('TEST log 2');
INSERT INTO log(log_string) VALUES('TEST log 3');
INSERT INTO log(log_string) VALUES('TEST log 4');
INSERT INTO log(log_string) VALUES('TEST log 5');
INSERT INTO log(log_string) VALUES('TEST log 6');
INSERT INTO log(log_string) VALUES('TEST log 7');
INSERT INTO log(log_string) VALUES('TEST log 8');
INSERT INTO log(log_string) VALUES('TEST log 9');
INSERT INTO log(log_string) VALUES('TEST log 10');
INSERT INTO log(log_string) VALUES('TEST log 11');
INSERT INTO log(log_string) VALUES('TEST log 12');
INSERT INTO log(log_string) VALUES('TEST log 13');
INSERT INTO log(log_string) VALUES('TEST log 14');
INSERT INTO log(log_string) VALUES('TEST log 15');
INSERT INTO log(log_string) VALUES('TEST log 16');
INSERT INTO log(log_string) VALUES('TEST log 17');
INSERT INTO log(log_string) VALUES('TEST log 18');
INSERT INTO log(log_string) VALUES('TEST log 19');
INSERT INTO log(log_string) VALUES('TEST log 20');
INSERT INTO log(log_string) VALUES('TEST log 21');
INSERT INTO log(log_string) VALUES('TEST log 22');
INSERT INTO log(log_string) VALUES('TEST log 23');
INSERT INTO log(log_string) VALUES('TEST log 24');
INSERT INTO log(log_string) VALUES('TEST log 25');
INSERT INTO log(log_string) VALUES('TEST log 26');

--TEST
INSERT INTO test(test_column) VALUES('test COLUMN 1');
INSERT INTO test(test_column) VALUES('test COLUMN 2');
INSERT INTO test(test_column) VALUES('test COLUMN 3');
INSERT INTO test(test_column) VALUES('test COLUMN 4');

--post
INSERT INTO post(author, title, subtitle, text) VALUES('Admin','FIRSTTITLE','SUBTITLE','My first post!');
INSERT INTO post(author, title, subtitle, text) VALUES('Admin','My second post!','second-sub','Hello world!');

--image
INSERT INTO image(post_id) VALUES (1);
INSERT INTO image(post_id) VALUES (1);
INSERT INTO image(post_id) VALUES (1);
INSERT INTO image(post_id) VALUES (1);
INSERT INTO image(post_id) VALUES (2);
INSERT INTO image(post_id) VALUES (2);
INSERT INTO image(post_id) VALUES (2);

--COMMENT
INSERT INTO COMMENT(author, text, post_id) VALUES('Admin','Funny comments', '1');

