-- CREATE USER IF NOT EXISTS bookmanager@localhost identified BY 'hpZlg)q_t!3x';
-- CREATE DATABASE IF NOT EXISTS schema1488;
-- GRANT ALL PRIVILEGES ON schema1488.* TO bookmanager@localhost;

-- use schema1488;
--
-- DROP TABLE IF EXISTS test;
-- DROP TABLE IF EXISTS authorities;
-- DROP TABLE IF EXISTS log;
-- DROP TABLE IF EXISTS image;
-- DROP TABLE IF EXISTS comment;
-- DROP TABLE IF EXISTS post;
-- DROP TABLE IF EXISTS author;
--
-- --USER TABLE
-- CREATE TABLE IF NOT EXISTS author
-- (
--     id INTEGER PRIMARY KEY IDENTITY NOT NULL,
--     first_name	VARCHAR(20),
--     last_name	VARCHAR(20),
--     nick_name	VARCHAR(20) UNIQUE NOT NULL,
--     birthday	TIMESTAMP,
--     email	VARCHAR(30) UNIQUE NOT NULL,
--     password	VARCHAR(100) NOT NULL,
--     image INT UNIQUE,
--     visible boolean default true,
--     enabled BOOLEAN NOT NULL
-- );

--AUTHORITIES TABLE
CREATE TABLE IF NOT EXISTS authorities
(
      id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
      useremail VARCHAR(30) UNIQUE NOT NULL,
      access_group VARCHAR(255) NOT NULL,
      FOREIGN KEY (useremail) REFERENCES author(email)
);

--LOG TABLE
CREATE TABLE IF NOT EXISTS log (
	  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    log_string VARCHAR(1000) NULL
);

--TEST TABLE
CREATE TABLE IF NOT EXISTS test (
	  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    test_column VARCHAR(1000) NULL
);

--POSTS TABLE
-- CREATE TABLE IF NOT EXISTS post (
--     id INT NOT NULL IDENTITY PRIMARY KEY,
--     author_id INT NOT NULL,
--     published  TIMESTAMP DEFAULT NOW() NOT NULL,
--     title VARCHAR(255) NOT NULL,
--     subtitle VARCHAR(255) NOT NULL,
--     text VARCHAR(10000) NOT NULL,
--     FOREIGN KEY (author_id) REFERENCES author(id)
-- );
--
-- --COMMENTS TABLE
-- CREATE TABLE IF NOT EXISTS comment (
--     id INT NOT NULL IDENTITY PRIMARY KEY,
--     author_id INT NOT NULL,
--     published  TIMESTAMP DEFAULT NOW() NOT NULL,
--     text VARCHAR(3000) NOT NULL,
--     post_id INT NOT NULL,
--     FOREIGN KEY (post_id) REFERENCES post(id),
--     FOREIGN KEY (author_id) REFERENCES author(id)
-- );
--
-- --IMAGES TABLE
-- CREATE TABLE IF NOT EXISTS image (
--     id INT NOT NULL IDENTITY PRIMARY KEY,
--     post_id INT,
--     FOREIGN KEY (post_id) REFERENCES post(id)
-- );