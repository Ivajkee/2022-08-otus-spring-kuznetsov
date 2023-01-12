CREATE TABLE AUTHORS (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    FULL_NAME VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE GENRES (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE BOOKS (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    AUTHOR_ID BIGINT NOT NULL,
    GENRE_ID BIGINT NOT NULL,
    TITLE VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT FK_AUTHORS FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS (ID),
    CONSTRAINT FK_GENRES FOREIGN KEY (GENRE_ID) REFERENCES GENRES (ID)
);

CREATE TABLE COMMENTS (
    ID BIGINT NOT NULL AUTO_INCREMENT,
    BOOK_ID BIGINT NOT NULL,
    TEXT VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT FK_BOOKS FOREIGN KEY (BOOK_ID) REFERENCES BOOKS (ID)
);