--USERS
DELETE from comment;
DELETE from image;
DELETE from post;
DELETE from authorities;
DELETE from author;
INSERT INTO author (nickName, email, password, enabled) VALUES ('Admin', 'admin@gmail.com', '$2a$10$PZnnT1R3T8eElY9fPzg9vOcb9zj5C9PtyBfxE4t8tlF0JGH/QInzG', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka1', 'grechka@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka2', 'test1@outlook.com', '$2a$10$PZnnT1R3T8eElY9fPzg9vOcb9zj5C9PtyBfxE4t8tlF0JGH/QInzG', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Guest', 'test2@outlook.com', '$2a$10$PZnnT1R3T8eElY9fPzg9vOcb9zj5C9PtyBfxE4t8tlF0JGH/QInzG', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka4', 'test3@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka5', 'test4@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka6', 'test5@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka7', 'test6@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka8', 'test7@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka9', 'test8@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka10', 'roleuser@outlook.com', '12345678', true);
INSERT INTO author (nickName, email, password, enabled) VALUES ('Grechka11', 'superuser@outlook.com', '12345678', true);

--authorities
INSERT INTO authorities (useremail, access_group) VALUES ('admin@gmail.com', 'ROLE_ADMIN');
INSERT INTO authorities (useremail, access_group) VALUES ('test1@outlook.com', 'ROLE_USER');
INSERT INTO authorities (useremail, access_group) VALUES ('test2@outlook.com', 'ROLE_SUPER_USER');

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
INSERT INTO post(author_id, title, subtitle, text, isvisible) VALUES((SELECT id from author where email = 'admin@gmail.com'),
'FIRSTTITLE','SUBTITLE','My first post!', false);
INSERT INTO post(author_id, title, subtitle, text, isvisible) VALUES((SELECT id from author where email = 'admin@gmail.com'),
'My second post!','second-sub','Hello world!', true);

--image
INSERT INTO image(post_id) VALUES ((SELECT id from post where title = 'FIRSTTITLE'));
INSERT INTO image(post_id) VALUES ((SELECT id from post where title = 'FIRSTTITLE'));
INSERT INTO image(post_id) VALUES ((SELECT id from post where title = 'FIRSTTITLE'));
INSERT INTO image(post_id) VALUES ((SELECT id from post where title = 'FIRSTTITLE'));
INSERT INTO image(post_id) VALUES ((SELECT id from post where title = 'FIRSTTITLE'));
INSERT INTO image(post_id) VALUES ((SELECT id from post where title = 'FIRSTTITLE'));
INSERT INTO image(post_id) VALUES ((SELECT id from post where title = 'FIRSTTITLE'));

--COMMENT
INSERT INTO comment(author_id, text, post_id) VALUES((SELECT id from author where email = 'admin@gmail.com'),
'Funny comments', (SELECT id from post where title = 'FIRSTTITLE'));

