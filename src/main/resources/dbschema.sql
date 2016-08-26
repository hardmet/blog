DROP TABLE IF EXISTS TEST;
DROP TABLE IF EXISTS AUTHORITIES;
DROP TABLE IF EXISTS LOG;
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS POST;
DROP TABLE IF EXISTS USER;

use bookmanager;

--USER TABLE
CREATE TABLE IF NOT EXISTS USER
(
    ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    FIRST_NAME	VARCHAR(20),
    LAST_NAME	VARCHAR(20),
    NICK_NAME	VARCHAR(20) UNIQUE NOT NULL,
    BIRTHDAY	TIMESTAMP,
    EMAIL	VARCHAR(30) UNIQUE NOT NULL,
    PASSWORD	VARCHAR(100) NOT NULL,
    ENABLED BOOLEAN NOT NULL
);

--AUTHORITIES TABLE
CREATE TABLE IF NOT EXISTS AUTHORITIES
(
      USERNAME VARCHAR(30) NOT NULL PRIMARY KEY,
      ACCESS_GROUP VARCHAR(255) NOT NULL,
      FOREIGN KEY (USERNAME) REFERENCES USER(EMAIL)
);

--LOG TABLE
CREATE TABLE IF NOT EXISTS LOG (
	IDLOG INT AUTO_INCREMENT NOT NULL,
    LOGSTRING VARCHAR(1000) NULL,
    PRIMARY KEY (IDLOG)
);

--TEST TABLE
CREATE TABLE IF NOT EXISTS TEST (
	IDTEST INT NOT NULL AUTO_INCREMENT,
    TESTCOLUMN VARCHAR(1000) NULL,
    PRIMARY KEY (IDTEST)
);

--POSTS TABLE
CREATE TABLE IF NOT EXISTS POST (
    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    AUTHOR VARCHAR(20) NOT NULL,
    POST_DATE  TIMESTAMP DEFAULT NOW() NOT NULL,
    TITLE VARCHAR(255) NOT NULL,
    SUBTITLE VARCHAR(255) NOT NULL,
    TEXT VARCHAR(10000) NOT NULL,
    FOREIGN KEY (AUTHOR) REFERENCES USER(NICK_NAME)
);

--COMMENTS TABLE
CREATE TABLE IF NOT EXISTS COMMENT (
    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    AUTHOR VARCHAR(20) NOT NULL,
    COMMENT_DATE  TIMESTAMP DEFAULT NOW() NOT NULL,
    TEXT VARCHAR(3000) NOT NULL,
    POST_ID INT NOT NULL,
    FOREIGN KEY (POST_ID) REFERENCES POST(ID),
    FOREIGN KEY (AUTHOR) REFERENCES USER(NICK_NAME)
);